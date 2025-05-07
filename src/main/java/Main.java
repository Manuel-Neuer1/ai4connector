import Schema.Meta;
import db.ConnectMysqlDB;
import mcp.DashScopeMCPClient;
import sql.Impl.MysqlSQLGenerator;
import url.Impl.MysqlURLconfigGenerator;
import compare.OutputFileComparator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static prompt.PromptGenerator.promptGen;

public class Main {
    static String baseFolderPath = "prompt/testdb";
    static String baseURL = "jdbc:mysql://localhost:3306/?user=root&password=1234";
    static String baseOceanbaseURL = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234";
    static int maxColumnCount = 2;
    static int maxTableCount = 10; // 每个数据库db中能够被允许创建的表的数量
    static int roundCount = 1;     // 总共生成 5 个 testdb，分别编号 0 ~ 4
    static int MAX_ROUNDS = 50;

    public static AtomicLong promptFileNum = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        Random r = new Random();
        ConnectMysqlDB condb;

        for(int cycle = 1; cycle <= roundCount; cycle++) {
            String folderPath = baseFolderPath;
            String URL = baseURL;
            String oceanbaseURL = baseOceanbaseURL;
            /* 数据库连接测试 */
            try {
                // /* 这里测试 ConnectDB 的逻辑 */
                // 1 先调用connectDB在mysql规范的数据库系统中创建db
                // 1.1 创建一个ConnectDB对象，连接上对应的数据库系统
                condb = new ConnectMysqlDB(URL);
                // 1.2 连接上mysql后调用创建数据库的方法,创建数据库testdb-cnt
                condb.createDatabase(condb.getDbId());
                // 1.3 成功创建好数据库testdb-cnt后关闭连接
                if (URL.contains("mysql")) {
                    System.out.println("mysql数据库成功连接！");
                } else if (URL.contains("oceanbase")) {
                    System.out.println("oceanbase数据库成功连接！");
                } else {
                    System.out.println("连接成功！");
                }
                condb.closeConnection();
            } catch (Exception e) {
                System.out.println("数据库连接异常，请检测数据库连接设置！");
                System.out.println(e.getMessage());
                return;
            }

            /* 这里测试生成复杂URL的逻辑 */
            MysqlURLconfigGenerator mysqlURLgen = new MysqlURLconfigGenerator(r);
            String urlLast = (String) mysqlURLgen.generateRandomUrlConfig();
            // TODO 这里的URL生成的参数是mysql专用的，不是ob和mysql通用的参数，需要修改
            URL = InsertDB2URL(URL, "testdb" + condb.getDbId()) + urlLast;
            System.out.println(URL);

            folderPath = folderPath + condb.getDbId() + "/";
            /* 测试列、表的逻辑 */
            // 每个数据库db里最多生成maxTableCount个表用于测试（因此每个数据库db只生成maxTableCount个prompt）
            for (int i = 0; i < maxTableCount; i++) {
                Meta meta = new Meta(condb, i);
                meta.initTable(maxColumnCount);
                MysqlSQLGenerator mysqlSQLGenerator = new MysqlSQLGenerator(meta);
                String createTableSQL = mysqlSQLGenerator.generateCreateSql();
                promptGen(folderPath, URL, meta, createTableSQL, promptFileNum); // 这里生成prompt文件
            }
            System.out.println("Prompt generation complete.");

            String inputDir = "";
            String genTestsDir = Paths.get("testRewriteFile", "generated_tests", "testdb" + condb.getDbId()).toString();

            try {
                // 使用CursorMCPClient生成测试
                DashScopeMCPClient client = new DashScopeMCPClient(genTestsDir);

                // 为每个prompt生成测试用例
                client.generateTestCases(folderPath);

                // 更新输入目录路径指向生成的测试
                inputDir = genTestsDir;
            } catch (Exception e) {
                System.err.println("Error in API test generation: " + e.getMessage());
                e.printStackTrace();

            }

             //5. 重写测试文件
        //String outputDir = Paths.get("testRewriteFile", "Output", "testdb" + condb.getDbId()).toString();

        String outputDir = Paths.get("testRewriteFile", "Output", "testdb0").toString();
        try {
            Files.createDirectories(Paths.get(outputDir));

            // 获取输入目录中的所有测试文件
            //File inputDirectory = new File(inputDir);
            File inputDirectory = new File("testRewriteFile/generated_tests/testdb0");
            File[] testFiles = inputDirectory
                    .listFiles((dir, name) -> name.startsWith("test_") && name.endsWith(".java"));

            if (testFiles != null) {
                for (File testFile : testFiles) {
                    String inputFilePath = testFile.getAbsolutePath();
                    String outputFilePath = Paths.get(outputDir, "test" + testFile.getName().substring(5)).toString();

                    try {
                        rewriteFile rf = new rewriteFile(inputFilePath, outputFilePath);
                        System.out.println("文件改写完成，结果已保存到：" + outputFilePath);
                    } catch (IOException e) {
                        System.err.println("Error rewriting file " + testFile.getName() + ": " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }

        // 6. 执行测试文件
        File outputDirectory = new File(outputDir);
        File[] rewrittenTestFiles = outputDirectory
                .listFiles((dir, name) -> name.startsWith("test") && name.endsWith(".java"));

        if (rewrittenTestFiles != null) {
            for (File testFile : rewrittenTestFiles) {
                try {
                    ExecuteJavaCodeFromFile ejf = new ExecuteJavaCodeFromFile();
                    ejf.executeFile(testFile.getAbsolutePath());
                } catch (Exception e) {
                    System.err.println("Error executing test file " + testFile.getName() + ": " + e.getMessage());
                }
            }
        }

            // 7. 对比结果 这里对比OceanBase和Mysql的结果，但是现在只需要写Mysql的就好了，OB的可以把代码重新运行一下，改成OB的URL就好了
            // OutputFileComparator.compare();

            //condb.addDbId();
        }
    }

    public static String InsertDB2URL(String URL, String dbName) {
        // 如果URL中不包含数据库名称，则在URL中添加数据库名称
        int pos = URL.indexOf("?");
        String URLFirst = URL.substring(0, pos);
        String URLLast = URL.substring(pos);
        return URLFirst + dbName + URLLast;
    }
}