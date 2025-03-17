import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import javax.tools.*;
import java.util.*;
import java.lang.reflect.*;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class ExecuteJavaCodeFromFile {
    private String codeFilePath;

    public void executeFile(String codeFilePath) {
        // 读取文件中的代码并执行
        String javaCode = readJavaCodeFromFile(codeFilePath);
        if (javaCode != null) {
            executeJavaCode(javaCode);
        }
    }

    public String outputFilePath(){
        String directoryPath = "ResultComparison/connector1";
        String filePath = directoryPath + "/output.txt";

        try {
            // 确保目录存在
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            System.err.println("无法创建目录: " + e.getMessage());
        }

        return filePath;
    }

    /**
     * 从文件中读取Java代码
     */
    public String readJavaCodeFromFile(String filePath) {
        StringBuilder javaCode = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                javaCode.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("读取文件时出错: " + e.getMessage());
            return null;
        }
        return javaCode.toString();
    }

    /**
     * 动态编译并执行Java代码
     */
    public void executeJavaCode(String javaCode) {
        // 创建一个Java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("无法获取Java编译器。请确保你使用的是完整的JDK环境。");
            return;
        }

        // 准备编译的Java代码
        String fullJavaCode = ""
                + "import java.sql.*;\n"
                + "import java.util.*;\n"
                + "import java.io.*;\n"
                + "public class DynamicJDBCClass {\n"
                + "    public static void main(String[] args) throws SQLException, FileNotFoundException {\n"
                + "        // 重定向标准输出到文件\n"
                + "        FileOutputStream fos = new FileOutputStream(\"" + outputFilePath() + "\");\n"
                + "        PrintStream ps = new PrintStream(fos);\n"
                + "        System.setOut(ps);\n"
                + " \n"
                + javaCode + "\n"
                + "    }\n"
                + "}";


        // 创建一个内存中的Java文件对象
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // 创建一个临时目录来保存编译后的类文件
        File outputDir = new File("temp");
        outputDir.mkdirs();

        // 将编译后的类文件保存到临时目录
        List<JavaFileObject> fileObjects = new ArrayList<>();
        fileObjects.add(new MemoryJavaFileObject("DynamicJDBCClass", fullJavaCode));

        // 设置编译选项，包括类路径和输出目录
        List<String> options = new ArrayList<>();
        options.add("-classpath");
        options.add(System.getProperty("java.class.path"));
        options.add("-d");
        options.add(outputDir.getAbsolutePath());

        System.out.println(options);
        /*[-classpath, D:\JavaDevelop\Project\ai4connector\target\classes;C:\Users\25302\.m2\repository\com\mysql\mysql-connector-j\8.0.33\mysql-connector-j-8.0.33.jar;C:\Users\25302\.m2\repository\com\google\protobuf\protobuf-java\3.21.9\protobuf-java-3.21.9.jar, -d, D:\JavaDevelop\Project\ai4connector\temp]
         */
        // 编译
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, fileObjects);
        boolean success = task.call();

        if (success) {
            System.out.println("编译成功！");
            // 执行编译后的类
            try {
                // 使用自定义类加载器加载编译后的类
                ClassLoader classLoader = new DynamicClassLoader(getClass().getClassLoader(), outputDir);
                Class<?> clazz = classLoader.loadClass("DynamicJDBCClass");
                Method mainMethod = clazz.getMethod("main", String[].class);
                mainMethod.invoke(null, (Object) new String[0]);
            } catch (Exception e) {
                System.err.println("执行编译后的类时出错: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("编译失败！");
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                System.err.println("行 " + diagnostic.getLineNumber() + ": " + diagnostic.getMessage(null));
            }
        }

        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 内存中的Java文件对象
     */
    private class MemoryJavaFileObject extends SimpleJavaFileObject {
        private final String code;

        public MemoryJavaFileObject(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

    /**
     * 自定义类加载器，用于加载编译后的类
     */
    private class DynamicClassLoader extends ClassLoader {
        private File outputDir;

        public DynamicClassLoader(ClassLoader parent, File outputDir) {
            super(parent);
            this.outputDir = outputDir;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            File classFile = new File(outputDir, name.replace('.', '/') + ".class");
            if (!classFile.exists()) {
                throw new ClassNotFoundException(name);
            }

            try (FileInputStream fis = new FileInputStream(classFile);
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
                byte[] classBytes = baos.toByteArray();
                return defineClass(name, classBytes, 0, classBytes.length);
            } catch (IOException e) {
                throw new ClassNotFoundException(name);
            }
        }
    }
}