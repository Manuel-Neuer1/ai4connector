import Schema.Meta;
import db.ConnectMysqlDB;
import mcp.DashScopeMCPClient;
import sql.Impl.MysqlSQLGenerator;
import url.Impl.MysqlURLconfigGenerator;
import compare.OutputFileComparator;
import prompt.*;
import util.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static prompt.PromptGenerator.promptGen;

public class Main {
    static String baseFolderPath = "prompt/testdb";
    static String baseURL = "jdbc:mysql://localhost:3306/?user=root&password=1234";
    static String baseOceanbaseURL = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234";
    static int maxColumnCount = 2;
    static int maxTableCount = 1;// 每个数据库db中能够被允许创建的表的数量
    static int roundCount = 1;     // 总共生成 5 个 testdb，分别编号 0 ~ 4
    static int MAX_ROUNDS = 4;

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

            // === 自适应 Prompt 选择模块开始 ===
            // 1. 把所有 prompt 文件加载成 PromptArm 列表
            List<PromptArm> arms = null;
            try {
                // folderPath = prompt/testdb+DbId/
                arms = Files.list(Paths.get(folderPath))
                        .filter(p -> p.toString().endsWith(".txt"))
                        .map(PromptArm::new)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 2. 初始化 DashScope 客户端 genTestDir = testRewriteFile/generated_tests/testdb+DbId
            DashScopeMCPClient client = new DashScopeMCPClient(genTestsDir);

            // 3. 多轮自适应调用
            for (int round = 1; round <= MAX_ROUNDS; round++) {
                // 3.1 用 UCB1 算法选出一个 prompt
                PromptArm arm = selectArmUCB1(arms);
                // arm.getPromptPath() 当前选取的这个prompt文件的路径，比如说 prompt/testdb0/prompt0.txt
                System.out.println("Round " + round + " use prompt: " + arm.getPromptPath());
                String extractPromptNumber = Util.extractPromptNumber(arm.getPromptPath().toString());

                // 3.2 调用 LLM 生成测试用例
                client.signalGenerateTestCases(arm.getPromptPath().toString());

//                //3.3 改写测试样例
//                File inputDirFile = new File(genTestsDir);
//                File[] newTests = inputDirFile.listFiles((dir, name) -> name.startsWith("test_") && name.endsWith(".java"));
//
//                if (newTests != null) {
//                    for (File testFile : newTests) {
//                        String inputPath = testFile.getAbsolutePath();
//                        // 构建输出路径
//                        Path outputDirPath = Paths.get("testRewriteFile", "Output", "testdb" + condb.getDbId(), "round" + round);
//
//                        // 确保输出目录存在
//                        try {
//                            Files.createDirectories(outputDirPath); // 创建所有必要的目录
//                        } catch (IOException e) {
//                            throw new RuntimeException("Error creating directories: " + e.getMessage(), e);
//                        }
//
//                        // 输出文件路径
//                        Path outputPath = outputDirPath.resolve(testFile.getName());
//                        try {
//                            rewriteFile rf = new rewriteFile(inputPath, outputPath.toString());
//                            System.out.println("Rewrite completed: " + outputPath);
//                        } catch (IOException e) {
//                            System.err.println("Error rewriting file: " + e.getMessage());
//                        }
//                    }
//                }
                // 3.3 改写测试样例，只处理 test_{number}.java 文件
                String targetTestFileName = "test_" + extractPromptNumber + ".java";
                File targetTestFile = new File(genTestsDir, targetTestFileName);

                if (targetTestFile.exists()) {
                    String inputPath = targetTestFile.getAbsolutePath();

                    // 构建输出路径
                    Path outputDirPath = Paths.get("testRewriteFile", "Output", "testdb" + condb.getDbId(), "round" + round);

                    // 确保输出目录存在
                    try {
                        Files.createDirectories(outputDirPath);
                    } catch (IOException e) {
                        throw new RuntimeException("Error creating directories: " + e.getMessage(), e);
                    }

                    // 输出文件路径
                    Path outputPath = outputDirPath.resolve(targetTestFileName);
                    try {
                        rewriteFile rf = new rewriteFile(inputPath, outputPath.toString());
                        System.out.println("Rewrite completed: " + outputPath);
                    } catch (IOException e) {
                        System.err.println("Error rewriting file: " + e.getMessage());
                    }
                } else {
                    System.err.println("Test file not found: " + targetTestFile.getAbsolutePath());
                }

                // 3.4 执行刚生成的测试用例，统计“发现的 bug 数”（需自行实现）
                int bugCount = 0;
                File roundOutputDir = new File(Paths.get("testRewriteFile","Output", "testdb" + condb.getDbId(), "round" + round).toString());
                File[] rewrittenTests = roundOutputDir.listFiles((dir, name) -> name.startsWith("test") && name.endsWith(".java"));
                if (rewrittenTests != null) {
                    for (File testFile : rewrittenTests) {
                        try {
                            ExecuteJavaCodeFromFile ejf = new ExecuteJavaCodeFromFile();
                            // 第一步：在两个数据库 connector 下分别执行
                            ejf.executeFileWithConnector(testFile.getAbsolutePath(), "connector1","jdbc:mysql://localhost:3306"); // e.g., MySQL
                            ejf.executeFileWithConnector(testFile.getAbsolutePath(), "connector2","jdbc:mariadb://localhost:3306"); // e.g., MariaDB

                            // 第二步：读取两个输出，比较差异
                            String output1 = Files.readString(Paths.get("ResultComparison/connector1/output_" + testFile.getName().replace(".java", "") + ".txt"));

                            String output2 = Files.readString(Paths.get("ResultComparison/connector2/output_" + testFile.getName().replace(".java", "") + ".txt"));
                            int bugFound = output1.equals(output2) ? 0 : 1;
                            bugCount += bugFound;
                        } catch (Exception e) {
                            System.err.println("Error executing file: " + e.getMessage());
                        }
                    }
                }

                // 3.4 更新该 prompt 的表现
                arm.recordReward(bugCount);
                System.out.println("Round " + round + " found potential bugs: " + bugCount);

            }
            // === 自适应 Prompt 选择模块结束 ===

            condb.addDbId();
        }
    }

    public static String InsertDB2URL(String URL, String dbName) {
        // 如果URL中不包含数据库名称，则在URL中添加数据库名称
        int pos = URL.indexOf("?");
        String URLFirst = URL.substring(0, pos);
        String URLLast = URL.substring(pos);
        return URLFirst + dbName + URLLast;
    }

    /** UCB1 选择函数 */
    private static PromptArm selectArmUCB1(List<PromptArm> arms) {
        int totalPlays = arms.stream()
                .mapToInt(PromptArm::getTimesTried)
                .sum();
        return arms.stream()
                .max(Comparator.comparingDouble(a -> a.ucbScore(totalPlays)))
                .orElseThrow();
    }
    /**
     * 执行 testsDir 下所有 test_*.java，返回“失败的测试数”或“触发的异常数”
     * —— 你可以在此调用 mvn test 或 ExecuteJavaCodeFromFile 并统计结果
     */
    private static int runAndCountBugs(String testsDir) {
        // TODO: 根据你的执行方式，跑一遍 test_*.java，然后统计失败或异常的个数
        return 0;
    }




}