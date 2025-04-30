import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.sql.*;
import javax.tools.*;
import java.util.*;
import java.lang.reflect.*;

public class ExecuteJavaCodeFromFile {
    public void executeFile(String codeFilePath) {
        File file = new File(codeFilePath);
        String fileNameWithoutExt = file.getName().replaceFirst("[.][^.]+$", ""); // 去掉扩展名
        String javaCode = readJavaCodeFromFile(codeFilePath);
        if (javaCode != null) {
            executeJavaCode(javaCode, fileNameWithoutExt); // 使用固定 ID
        }
    }

    public String outputFilePath(String uniqueId) {
        String directoryPath = "ResultComparison/connector1";
        String filePath = directoryPath + "/output_" + uniqueId + ".txt";
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (IOException e) {
            System.err.println("无法创建目录: " + e.getMessage());
        }
        return filePath;
    }

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

    public void executeJavaCode(String javaCode, String uniqueId) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("无法获取Java编译器。请使用 JDK 运行而不是 JRE。");
            return;
        }

        String className = "DynamicJDBCClass_" + uniqueId;
        String outputPath = outputFilePath(uniqueId);

        // 拼接完整 Java 源码
        String fullJavaCode = ""
                + "import java.sql.*;\n"
                + "import java.util.*;\n"
                + "import java.io.*;\n"
                + "import java.math.BigDecimal;\n"
                + "public class " + className + " {\n"
                + "    public static void main(String[] args) {\n"
                + "        try (PrintStream ps = new PrintStream(new FileOutputStream(\"" + outputPath + "\"))) {\n"
                + "            System.setOut(ps);\n"
                + "            // 用户代码开始\n"
                +                  javaCode + "\n"
                + "            // 用户代码结束\n"
                + "        } catch(Exception e) {\n"
                + "            System.out.println(e);\n"
                + "        }\n"
                + "    }\n"
                + "}";

        // 清理并创建临时目录
        File outputDir = new File("temp");
        if (outputDir.exists()) {
            for (File file : Objects.requireNonNull(outputDir.listFiles())) {
                file.delete();
            }
        }
        outputDir.mkdirs();

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

        List<JavaFileObject> fileObjects = new ArrayList<>();
        fileObjects.add(new MemoryJavaFileObject(className, fullJavaCode));

        List<String> options = new ArrayList<>();
        options.add("-classpath");

        String mavenRepo = "D:\\JavaDevelop\\apache-maven-3.9.9\\mvn_repo";
        String classPath = System.getProperty("java.class.path") + File.pathSeparator +
                "target/classes" + File.pathSeparator +
                mavenRepo + "/com/mysql/mysql-connector-j/9.2.0/mysql-connector-j-9.2.0.jar";

        options.add(classPath);
        options.add("-encoding");
        options.add("UTF-8");
        options.add("-d");
        options.add(outputDir.getAbsolutePath());

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, fileObjects);
        boolean success = task.call();

        if (success) {
            System.out.println("编译成功：" + className);
            try {
                ClassLoader classLoader = new DynamicClassLoader(getClass().getClassLoader(), outputDir);
                Class<?> clazz = classLoader.loadClass(className);
                Method mainMethod = clazz.getMethod("main", String[].class);
                mainMethod.invoke(null, (Object) new String[0]);
            } catch (Exception e) {
                System.err.println("执行编译后的类时出错: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("\n编译失败！详细信息如下：");
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                System.err.println("错误位置：第 " + diagnostic.getLineNumber() + " 行");
                System.err.println("错误信息：" + diagnostic.getMessage(null));
            }
        }

        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MemoryJavaFileObject extends SimpleJavaFileObject {
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

    private static class DynamicClassLoader extends ClassLoader {
        private final File outputDir;

        public DynamicClassLoader(ClassLoader parent, File outputDir) {
            super(parent);
            this.outputDir = outputDir;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            File classFile = new File(outputDir, name.replace('.', '/') + ".class");
            if (!classFile.exists()) throw new ClassNotFoundException(name);

            try (FileInputStream fis = new FileInputStream(classFile);
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return defineClass(name, baos.toByteArray(), 0, baos.size());
            } catch (IOException e) {
                throw new ClassNotFoundException(name);
            }
        }
    }
}
