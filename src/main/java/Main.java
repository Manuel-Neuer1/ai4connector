import Schema.Meta;
import db.ConnectDB;
import sql.Impl.MysqlSQLGenerator;
import url.Impl.MysqlURLconfigGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    static String folderPath = "prompt";
    static String URL = "jdbc:mysql://localhost:3306/?user=root&password=1234";
    static int maxColumnCount = 4;
    static int maxTableCount = 10; // 每个数据库db中能够被允许创建的表的数量

    public static AtomicLong promptFileNum = new AtomicLong(0);

    public static void main(String[] args) {

        Random r = new Random();

        /* 这里测试 ConnectDB 的逻辑 */
        // 1 先调用connectDB在mysql中创建db
        // 1.1 创建一个ConnectDB对象，连接上mysql
        ConnectDB condb = new ConnectDB(URL);
        // 1.2 连接上mysql后调用创建数据库的方法,创建数据库testdb-cnt
        condb.createDatabase(condb.getDbId());
        // 1.3 成功创建好数据库testdb-cnt后关闭连接
        condb.closeConnection();



        /* 这里测试生成复杂URL的逻辑 */
        MysqlURLconfigGenerator mysqlURLgen = new MysqlURLconfigGenerator(r);
        String urlLast = (String) mysqlURLgen.generateRandomUrlConfig();
        URL = URL + urlLast;
        System.out.println(URL);

        /* 测试列、表的逻辑 */
        //每个数据库db里最多生成100个表用于测试
        for(int i = 0; i < maxTableCount; i++) {
            Meta meta = new Meta(condb, i);
            meta.initTable(maxColumnCount);
            MysqlSQLGenerator mysqlSQLGenerator = new MysqlSQLGenerator(meta);
            //System.out.println(mysqlSQLGenerator.generateCreateSql());
            promptGenerator(URL, meta, mysqlSQLGenerator.generateCreateSql());
        }

        System.getProperty("user.dir");
        //String inputFilePath = "testRewriteFile/Input/input.txt";
        String outputFilePath = "testRewriteFile/Output/output.txt";

//        try{
//            rewriteFile rf = new rewriteFile(inputFilePath, outputFilePath);
//            System.out.println("文件改写完成，结果已保存到：" + outputFilePath);}
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.err.println("未找到 MySQL JDBC 驱动程序: " + e.getMessage());
//        }

        ExecuteJavaCodeFromFile ejf = new ExecuteJavaCodeFromFile();
        ejf.executeFile(outputFilePath);
    }

    public static String promptGenerator(String URL, Meta meta, String createTableSQL) {
        // 确保文件夹路径以斜杠结尾，如果不是则添加
        if (!folderPath.endsWith(File.separator)) {
            folderPath += File.separator;
        }
        // 创建文件夹（包括必要的父目录）
        File dir = new File(folderPath);
        if (!dir.exists()) {
            boolean mkdirsResult = dir.mkdirs(); // 尝试创建目录
            if (!mkdirsResult) {
                System.err.println("无法创建文件夹: " + folderPath);
                return null;
            }
        }
        // 构造文件路径
        String filePath = folderPath + "prompt" + promptFileNum;
        promptFileNum.incrementAndGet();
        PrintWriter writer = null;
        try {
            // 创建PrintWriter对象，使用FileWriter作为其构造参数
            writer = new PrintWriter(new FileWriter(filePath));

            writer.print("现在你是一个精通数据库连接器测试和 Java 语言的专家，\n");
            writer.println("数据库连接器连接的URL为：" + URL);
            writer.print("已知当前我的数据库中表结构为：\n");
            writer.println(meta.getTable().toString());
            writer.println("请为我生成一个可能出现潜在问题的代码，该代码包含多种JDBC方法，在不同数据库连接器上测试时可能会产生不一样的结果");

            // 返回文件路径表示成功
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            // 如果出现异常，返回null或相应的错误消息
            return null;
        } finally {
            // 确保在完成后关闭writer，避免资源泄露
            if (writer != null) {
                writer.close();
            }
        }
    }
}