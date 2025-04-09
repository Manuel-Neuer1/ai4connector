import Schema.Meta;
import db.ConnectMysqlDB;
import sql.Impl.MysqlSQLGenerator;
import url.Impl.MysqlURLconfigGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static prompt.PromptGenerator.promptGen;

public class Main {
    static String folderPath = "prompt/testdb";
    static String URL = "jdbc:mysql://localhost:3306/?user=root&password=1234";
    static String oceanbaseURL = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234";;
    static int maxColumnCount = 4;
    static int maxTableCount = 10; // 每个数据库db中能够被允许创建的表的数量

    public static AtomicLong promptFileNum = new AtomicLong(0);

    public static void main(String[] args) {

        Random r = new Random();
        ConnectMysqlDB condb = null;

        /* 数据库连接测试 */
        try {
            /* 这里测试 ConnectDB 的逻辑 */
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
        // 每个数据库db里最多生成10个表用于测试（因此每个数据库db只生成10个prompt）
        // for (int i = 0; i < maxTableCount; i++) {
        // Meta meta = new Meta(condb, i);
        // meta.initTable(maxColumnCount);
        // MysqlSQLGenerator mysqlSQLGenerator = new MysqlSQLGenerator(meta);
        // // System.out.println(mysqlSQLGenerator.generateCreateSql());
        // String createTableSQL = mysqlSQLGenerator.generateCreateSql();
        // promptGen(folderPath, URL, meta, createTableSQL, promptFileNum);
        // /* 这里开始为每个数据库db创建表 */

        // }

        String currentDir = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDir);
        // String inputFilePath = "testRewriteFile/Input/input.txt";
        String outputFilePath = "testRewriteFile/Output/TestPrompt1.txt";

        // try{
        // rewriteFile rf = new rewriteFile(inputFilePath, outputFilePath);
        // System.out.println("文件改写完成，结果已保存到：" + outputFilePath);}
        // catch (IOException e){
        // e.printStackTrace();
        // }
        // try {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        // } catch (ClassNotFoundException e) {
        // System.err.println("未找到 MySQL JDBC 驱动程序: " + e.getMessage());
        // }

        ExecuteJavaCodeFromFile ejf = new ExecuteJavaCodeFromFile();
        ejf.executeFile(outputFilePath);

        // 对比逻辑
        // OutputFileComparator.compare();
    }

    public static String InsertDB2URL(String URL, String dbName) {
        // 如果URL中不包含数据库名称，则在URL中添加数据库名称
        int pos = URL.indexOf("?");
        String URLFirst = URL.substring(0, pos);
        String URLLast = URL.substring(pos);
        return URLFirst + dbName + URLLast;
    }

}