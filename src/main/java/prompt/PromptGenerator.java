package prompt;

import Schema.Meta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

public class PromptGenerator {

    public PromptGenerator() {}

    public static void promptGen(String folderPath, String URL, Meta meta, String createTableSQL, AtomicLong promptFileNum) {
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
        String filePath = folderPath + "prompt" + promptFileNum + ".txt";
        promptFileNum.incrementAndGet();
        PrintWriter writer = null;
        try {
            // 创建PrintWriter对象，使用FileWriter作为其构造参数
            writer = new PrintWriter(new FileWriter(filePath));


            writer.print("现在你是一个精通数据库连接器测试和 Java 语言的专家。\n");
            writer.println("数据库连接器连接的URL为：" + URL);
            writer.print("已知当前我的数据库中表结构为：\n");
            writer.println(meta.getTable().toString());
            writer.println("你的任务是为我生成一段 Java 代码，该代码需要满足以下要求：");
            writer.println("    1. 代码需要包含多种 JDBC 方法的调用，以测试不同数据库连接器的行为差异。\n" +
                           "    2. 代码需符合以下格式，不包含 try-catch 块和其他不必要的代码:\n" +
                           "        “stmt = con.createStatement(1005, 1007, 2);\n" +
                           "        stmt.getQueryTimeout();\n" +
                           "        stmt.executeUpdate(\"DROP TABLE IF EXISTS table18_0;\");\n" +
                           "        stmt.executeLargeUpdate(\"CREATE TABLE table18_0(id SMALLINT PRIMARY KEY,value TINYINT);\", 1);\n" +
                           "        stmt.executeLargeUpdate(\"UPDATE table18_0 SET value = 446748952 WHERE (table18_0.value > -628126433/-1075983817);\", 2);\n" +
                           "        stmt.getResultSetType();\n" +
                           "        stmt.getGeneratedKeys();\n" +
                           "        stmt.getLargeUpdateCount();\n" +
                           "        rs = stmt.executeQuery(\"SELECT id, value FROM table18_0 WHERE (table18_0.value > -273963601);\");\n" +
                           "        con.commit();\n" +
                           "        rs.setFetchDirection(1001);\n" +
                           "        rs.next();\n" +
                           "        con.getAutoCommit();\n" +
                           "        rs = stmt.executeQuery(\"SELECT id, value FROM table18_0 WHERE (table18_0.value > -431675329-1315731580-257152969) OR (table18_0.id != 936751358);\");\n" +
                           "        con.commit();\n" +
                           "        rs.next();\n" +
                           "        con.getHoldability();”\n");

            writer.print("  3.对于JDBC方法，你只能调用以下提到的五个类中的方法：DriverManager类、Connection类、Statement类、PreparedStatment类、ResultSet类。\n" +
                    "       3.1 DriverManager类中可使用的方法只有：\n" +
                    "              getConnection(URL);\n" +
                    "       3.2 Connection类中可使用的方法有：\n" +
                    "              Connection 类中可使用的方法有：\n" +
                    "              commit()：提交当前事务。\n" +
                    "              rollback()：回滚当前事务。\n" +
                    "              rollback(Savepoint sp)：回滚到指定的保存点。\n" +
                    "              isReadOnly()：检查此连接是否处于只读模式。\n" +
                    "              setAutoCommit(boolean autoCommit)：设置自动提交模式。\n" +
                    "              setSavepoint()：创建一个保存点。\n" +
                    "              setSavepoint(String name)：创建一个具有指定名称的保存点。\n" +
                    "              getHoldability()：获取此连接创建的结果集的默认保持能力。\n" +
                    "              prepareStatement(String sql)：创建一个 PreparedStatement 对象。\n" +
                    "              prepareStatement(String sql, int autoGeneratedKeys)：创建一个 PreparedStatement 对象，并指定自动生成的键。\n" +
                    "              prepareStatement(String sql, int[] columnIndexes)：创建一个 PreparedStatement 对象，并指定自动生成的列索引。\n" +
                    "              prepareStatement(String sql, String[] columnNames)：创建一个 PreparedStatement 对象，并指定自动生成的列名。\n" +
                    "              createStatement()：创建一个 Statement 对象。\n" +
                    "              createStatement(int resultSetType, int resultSetConcurrency)：创建一个具有指定结果集类型和并发性的 Statement 对象。\n" +
                    "              createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)：创建一个具有指定结果集类型、并发性和保持能力的 Statement 对象。\n" +
                    "              close()：关闭此连接。\n" +
                    "       3.3 Statement 类中可使用的方法有：\n" +
                    "              executeQuery(String sql)：执行 SQL 查询，并返回一个 ResultSet 对象。\n" +
                    "              executeUpdate(String sql)：执行 SQL 更新，并返回受影响的行数。\n" +
                    "              executeLargeUpdate(String sql)：执行 SQL 更新，并返回受影响的行数（支持大数据集）。\n" +
                    "              executeBatch()：执行一批 SQL 语句。\n" +
                    "              executeLargeBatch()：执行一批 SQL 语句（支持大数据集）。\n" +
                    "              addBatch(String sql)：将 SQL 语句添加到批处理中。\n" +
                    "              clearBatch()：清除批处理中的所有 SQL 语句。\n" +
                    "              close()：关闭此 Statement 对象。\n" +
                    "              closeOnCompletion()：关闭此 Statement 对象，当所有结果集关闭时。\n" +
                    "              getResultSet()：获取此 Statement 执行查询返回的当前 ResultSet 对象。\n" +
                    "              getUpdateCount()：获取上一个执行的更新语句影响的行数。\n" +
                    "              getMoreResults()：移动到下一个结果。\n" +
                    "              getLargeUpdateCount()：获取上一个执行的更新语句影响的行数（支持大数据集）。\n" +
                    "              getGeneratedKeys()：获取自动生成的键。\n" +
                    "              getMaxFieldSize()：获取此 Statement 对象的最大字段大小。\n" +
                    "              setMaxFieldSize(int max)：设置此 Statement 对象的最大字段大小。\n" +
                    "              setFetchDirection(int direction)：设置结果集的获取方向。\n" +
                    "              getFetchDirection()：获取结果集的获取方向。\n" +
                    "              setFetchSize(int rows)：设置结果集的获取大小。\n" +
                    "              getFetchSize()：获取结果集的获取大小。\n" +
                    "              getResultSetConcurrency()：获取结果集的并发性。\n" +
                    "              getResultSetType()：获取结果集的类型。\n" +
                    "              getResultSetHoldability()：获取结果集的保持能力。\n" +
                    "              setMaxRows(int max)：设置结果集的最大行数。\n" +
                    "              setEscapeProcessing(boolean enable)：启用或禁用转义处理。\n" +
                    "              getMaxRows()：获取结果集的最大行数。\n" +
                    "       3.4 PreparedStatement类中可使用的方法有：\n" +
                    "              setObject();addBatch();executeBatch();execute();executeQuery();executeUpdate();clearParameters();executeLargeUpdate();\n" +
                    "       3.5 ResultSet类中可使用的方法有：\n" +
                    "              next()：将光标移动到下一行。\n" +
                    "              previous()：将光标移动到上一行。\n" +
                    "              getObject(int columnIndex)：获取指定列的值。\n" +
                    "              getObject(String columnLabel)：获取指定列的值。\n" +
                    "              cancelRowUpdates()：取消对当前行的更新。\n" +
                    "              updateObject(int columnIndex, Object value)：更新指定列的值。\n" +
                    "              updateObject(String columnLabel, Object value)：更新指定列的值。\n" +
                    "              updateRow()：更新当前行。\n" +
                    "              beforeFirst()：将光标移动到结果集的开头之前。\n" +
                    "              afterLast()：将光标移动到结果集的末尾之后。\n" +
                    "              getHoldability()：获取结果集的保持能力。\n" +
                    "              getType()：获取结果集的类型。\n" +
                    "              setFetchSize(int rows)：设置结果集的获取大小。\n" +
                    "              setFetchDirection(int direction)：设置结果集的获取方向。\n" +
                    "              deleteRow()：删除当前行。\n" +
                    "              rowDeleted()：检查当前行是否已被删除。\n" +
                    "              rowInserted()：检查当前行是否已被插入。\n" +
                    "              rowUpdated()：检查当前行是否已被更新。\n" +
                    "              isFirst()：检查光标是否在第一行。\n" +
                    "              isLast()：检查光标是否在最后一行。\n" +
                    "              isAfterLast()：检查光标是否在结果集的末尾之后。\n" +
                    "              isBeforeFirst()：检查光标是否在结果集的开头之前。\n" +
                    "              close()：关闭此 ResultSet 对象。\n");

            writer.println("    4. 测试模块以及给出了如下内容：\n" +
                    "       Connection conn = null;\n" +
                    "       Statement stmt = null;\n" +
                    "       PreparedStatement pstmt = null;\n" +
                    "       ResultSet rs = null;\n" +
                    "       Savepoint savepoint = null;\n" +
                    "       con = DriverManager.getConnection(" + URL + ");");

            writer.println("    5. 建表语句为：" + createTableSQL);

//            writer.println("代码应多次调用相同的 JDBC 方法，特别是：executeUpdate(), executeLargeUpdate(), getGeneratedKeys(), commit(), rollback(), rollback(sp), addBatch(), executeBatch(), setFetchSize(), 以及上述提供的ResultSet类中的方法等，确保能够反复测试这些方法在不同连接器上的差异。" +
//                    "执行多次事务回滚与提交操作，并多次尝试关闭/重启连接以验证事务管理机制的正确性。\n" +
//                    "测试时需要模拟数据库异常的场景，例如对重复主键的插入、违反外键约束的插入、批量插入等，观察不同数据库连接器的反应。\n " +
//                    "代码需要在不同数据库连接器上测试时可能会产生不一样的结果，确保对可能的差异进行全面测试并报告结果");
            writer.println("    6.测试场景设计：你的代码需要涵盖以下场景，确保不同数据库连接器的行为得到全面测试：\n" +
                    "       批处理操作：通过多次执行批处理操作 (addBatch, executeBatch, executeLargeBatch) 测试数据库在大批量数据处理时的表现。\n" +
                    "       事务控制：测试 `commit()`、`rollback()`、`Savepoint` 的嵌套使用。测试 commit() 和 rollback() 方法，通过不同的事务操作验证数据库在事务控制方面的行为差异。设计多个事务场景，如多个提交、回滚、事务失败的情况。\n" +
                    "       SQL 查询：多次执行不同类型的查询（SELECT、UPDATE、INSERT以及条件 WHERE），以测试数据库的查询优化和响应。\n" +
                    "       结果集操作：多轮遍历结果集，测试大数据量时的游标性能。多次使用 next(), previous(), beforeFirst(), afterLast() 等方法测试 ResultSet 的游标控制。\n" +
                    "       性能测试：使用 setFetchSize(), getQueryTimeout() 等方法多次验证数据库的查询性能，特别是在高并发和大数据量场景下。\n" +
                    "测试时需要模拟数据库异常的场景，例如对重复主键的插入、违反外键约束的插入、批量插入等，观察不同数据库连接器的反应。\n" +
                    "代码需要在不同数据库连接器上测试时可能会产生不一样的结果，确保对可能的差异进行全面测试并报告结果。\n"
            );


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
