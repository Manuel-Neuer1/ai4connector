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
            if(URL.contains("mysql")) {
                System.out.println("mysql数据库成功连接！");
            }else if(URL.contains("oceanbase")) {
                System.out.println("oceanbase数据库成功连接！");
            }else{
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
        //URL = URL + urlLast;
        URL = InsertDB2URL(URL, "testdb" + condb.getDbId()) + urlLast;
        System.out.println(URL);

        folderPath = folderPath + condb.getDbId() + "/";
        /* 测试列、表的逻辑 */
        //每个数据库db里最多生成10个表用于测试（因此每个数据库db只生成10个prompt）
        for(int i = 0; i < maxTableCount; i++) {
            Meta meta = new Meta(condb, i);
            meta.initTable(maxColumnCount);
            MysqlSQLGenerator mysqlSQLGenerator = new MysqlSQLGenerator(meta);
            //System.out.println(mysqlSQLGenerator.generateCreateSql());
            String createTableSQL = mysqlSQLGenerator.generateCreateSql();
            promptGenerator(URL, meta, createTableSQL);
            /* 这里开始为每个数据库db创建表 */

        }

        String currentDir = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDir);
        //String inputFilePath = "testRewriteFile/Input/input.txt";
        String outputFilePath = "testRewriteFile/Output/kimi2.txt";

//        try{
//            rewriteFile rf = new rewriteFile(inputFilePath, outputFilePath);
//            System.out.println("文件改写完成，结果已保存到：" + outputFilePath);}
//        catch (IOException e){
//            e.printStackTrace();
//        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("未找到 MySQL JDBC 驱动程序: " + e.getMessage());
        }

        ExecuteJavaCodeFromFile ejf = new ExecuteJavaCodeFromFile();
        ejf.executeFile(outputFilePath);

        //对比逻辑
        //OutputFileComparator.compare();
    }

    public static String InsertDB2URL(String URL,String dbName) {
            // 如果URL中不包含数据库名称，则在URL中添加数据库名称
            int pos = URL.indexOf("?");
            String URLFirst = URL.substring(0,pos);
            String URLLast = URL.substring(pos);
            return URLFirst + dbName + URLLast;
    }

    public static void promptGenerator(String URL, Meta meta, String createTableSQL) {
        // 确保文件夹路径以斜杠结尾，如果不是则添加
//        if (!folderPath.endsWith(File.separator)) {
//            folderPath += File.separator;
//        }
        // 创建文件夹（包括必要的父目录）
        File dir = new File(folderPath);
        if (!dir.exists()) {
            boolean mkdirsResult = dir.mkdirs(); // 尝试创建目录
            if (!mkdirsResult) {
                System.err.println("无法创建文件夹: " + folderPath);
                return;
            }
        }
        // 构造文件路径
        String filePath = folderPath + "prompt" + promptFileNum;
        promptFileNum.incrementAndGet();
        PrintWriter writer = null;
        try {
            // 创建PrintWriter对象，使用FileWriter作为其构造参数
            writer = new PrintWriter(new FileWriter(filePath));


            writer.print("现在你是一个精通数据库连接器测试和 Java 语言的专家。\n");
            writer.println("数据库连接器连接的URL为：" + URL);
            writer.print("已知当前我的数据库中表结构为：\n");
            writer.println(meta.getTable().toString());
            writer.println("你的任务是为我生成一段 Java 代码，该代码需要满足以下要求:");
            writer.println("    1. 代码需要包含多种 JDBC 方法的调用，以测试不同数据库连接器的行为差异。");
            writer.println("    2. 你只能调用以下五个类中的方法：DriverManager 类、Connection 类、Statement 类、PreparedStatement 类、ResultSet 类。");
            writer.println("    3. DriverManager 类中仅可使用 getConnection(URL) 方法。\n");
            writer.print("对于JDBC方法，你只能调用以下提到的五个类中的方法：DriverManager类、Connection类、Statement类、PreparedStatment类、ResultSet类。\n" +
                    "   4. DriverManager类中可使用的方法只有：\n" +
                    "       getConnection(URL);\n" +
                    "   5. Connection类中可使用的方法有：\n" +
                    "       Connection 类中可使用的方法有：\n" +
                    "       commit()：提交当前事务。\n" +
                    "       rollback()：回滚当前事务。\n" +
                    "       rollback(Savepoint sp)：回滚到指定的保存点。\n" +
                    "       isReadOnly()：检查此连接是否处于只读模式。\n" +
                    "       setAutoCommit(boolean autoCommit)：设置自动提交模式。\n" +
                    "       setSavepoint()：创建一个保存点。\n" +
                    "       setSavepoint(String name)：创建一个具有指定名称的保存点。\n" +
                    "       getHoldability()：获取此连接创建的结果集的默认保持能力。\n" +
                    "       prepareStatement(String sql)：创建一个 PreparedStatement 对象。\n" +
                    "       prepareStatement(String sql, int autoGeneratedKeys)：创建一个 PreparedStatement 对象，并指定自动生成的键。\n" +
                    "       prepareStatement(String sql, int[] columnIndexes)：创建一个 PreparedStatement 对象，并指定自动生成的列索引。\n" +
                    "       prepareStatement(String sql, String[] columnNames)：创建一个 PreparedStatement 对象，并指定自动生成的列名。\n" +
                    "       createStatement()：创建一个 Statement 对象。\n" +
                    "       createStatement(int resultSetType, int resultSetConcurrency)：创建一个具有指定结果集类型和并发性的 Statement 对象。\n" +
                    "       createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)：创建一个具有指定结果集类型、并发性和保持能力的 Statement 对象。\n" +
                    "       close()：关闭此连接。\n" +
                    "   6. Statement 类中可使用的方法有：" +
                    "       executeQuery(String sql)：执行 SQL 查询，并返回一个 ResultSet 对象。\n" +
                    "       executeUpdate(String sql)：执行 SQL 更新，并返回受影响的行数。\n" +
                    "       executeLargeUpdate(String sql)：执行 SQL 更新，并返回受影响的行数（支持大数据集）。\n" +
                    "       executeBatch()：执行一批 SQL 语句。\n" +
                    "       executeLargeBatch()：执行一批 SQL 语句（支持大数据集）。\n" +
                    "       addBatch(String sql)：将 SQL 语句添加到批处理中。\n" +
                    "       clearBatch()：清除批处理中的所有 SQL 语句。\n" +
                    "       close()：关闭此 Statement 对象。\n" +
                    "       closeOnCompletion()：关闭此 Statement 对象，当所有结果集关闭时。\n" +
                    "       getResultSet()：获取此 Statement 执行查询返回的当前 ResultSet 对象。\n" +
                    "       getUpdateCount()：获取上一个执行的更新语句影响的行数。\n" +
                    "       getMoreResults()：移动到下一个结果。\n" +
                    "       getLargeUpdateCount()：获取上一个执行的更新语句影响的行数（支持大数据集）。\n" +
                    "       getGeneratedKeys()：获取自动生成的键。\n" +
                    "       getMaxFieldSize()：获取此 Statement 对象的最大字段大小。\n" +
                    "       setMaxFieldSize(int max)：设置此 Statement 对象的最大字段大小。\n" +
                    "       setFetchDirection(int direction)：设置结果集的获取方向。\n" +
                    "       getFetchDirection()：获取结果集的获取方向。\n" +
                    "       setFetchSize(int rows)：设置结果集的获取大小。\n" +
                    "       getFetchSize()：获取结果集的获取大小。\n" +
                    "       getResultSetConcurrency()：获取结果集的并发性。\n" +
                    "       getResultSetType()：获取结果集的类型。\n" +
                    "       getResultSetHoldability()：获取结果集的保持能力。\n" +
                    "       setMaxRows(int max)：设置结果集的最大行数。\n" +
                    "       setEscapeProcessing(boolean enable)：启用或禁用转义处理。\n" +
                    "       getMaxRows()：获取结果集的最大行数。\n" +
                    "   7. PreparedStatement类中可使用的方法有：\n" +
                    "setObject();addBatch();executeBatch();execute();executeQuery();executeUpdate();clearParameters();executeLargeUpdate();\n" +
                    "\n" +
                    "   8. ResultSet类中可使用的方法有：\n" +
                    "       ResultSet 类中可使用的方法有：\n" +
                    "       next()：将光标移动到下一行。\n" +
                    "       previous()：将光标移动到上一行。\n" +
                    "       getObject(int columnIndex)：获取指定列的值。\n" +
                    "       getObject(String columnLabel)：获取指定列的值。\n" +
                    "       cancelRowUpdates()：取消对当前行的更新。\n" +
                    "       updateObject(int columnIndex, Object value)：更新指定列的值。\n" +
                    "       updateObject(String columnLabel, Object value)：更新指定列的值。\n" +
                    "       updateRow()：更新当前行。\n" +
                    "       beforeFirst()：将光标移动到结果集的开头之前。\n" +
                    "       afterLast()：将光标移动到结果集的末尾之后。\n" +
                    "       getHoldability()：获取结果集的保持能力。\n" +
                    "       getType()：获取结果集的类型。\n" +
                    "       setFetchSize(int rows)：设置结果集的获取大小。\n" +
                    "       setFetchDirection(int direction)：设置结果集的获取方向。\n" +
                    "       deleteRow()：删除当前行。\n" +
                    "       rowDeleted()：检查当前行是否已被删除。\n" +
                    "       rowInserted()：检查当前行是否已被插入。\n" +
                    "       rowUpdated()：检查当前行是否已被更新。\n" +
                    "       isFirst()：检查光标是否在第一行。\n" +
                    "       isLast()：检查光标是否在最后一行。\n" +
                    "       isAfterLast()：检查光标是否在结果集的末尾之后。\n" +
                    "       isBeforeFirst()：检查光标是否在结果集的开头之前。\n" +
                    "       close()：关闭此 ResultSet 对象。");

            writer.println("Connection conn = null;\n" +
                    "Statement stmt = null;\n" +
                    "PreparedStatement pstmt = null;\n" +
                    "ResultSet rs = null;\n" +
                    "Savepoint savepoint = null;\n" +
                    "con = DriverManager.getConnection(" + URL + ");");


            writer.println("建表语句为：" + createTableSQL);

            writer.println("代码应多次调用相同的 JDBC 方法，特别是：executeUpdate(), executeLargeUpdate(), getGeneratedKeys(), commit(), rollback(), rollback(sp), addBatch(), executeBatch(), setFetchSize(), 以及上述提供的ResultSet类中的方法等，确保能够反复测试这些方法在不同连接器上的差异。" +
                    "执行多次事务回滚与提交操作，并多次尝试关闭/重启连接以验证事务管理机制的正确性。\n" +
                    "测试时需要模拟数据库异常的场景，例如对重复主键的插入、违反外键约束的插入、批量插入等，观察不同数据库连接器的反应。\n " +
                    "代码需要在不同数据库连接器上测试时可能会产生不一样的结果，确保对可能的差异进行全面测试并报告结果");


            // 返回文件路径表示成功
        } catch (IOException e) {
            e.printStackTrace();
            // 如果出现异常，返回null或相应的错误消息
        } finally {
            // 确保在完成后关闭writer，避免资源泄露
            if (writer != null) {
                writer.close();
            }
        }
    }






}