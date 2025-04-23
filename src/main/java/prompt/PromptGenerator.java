package prompt;

import Schema.Meta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

public class PromptGenerator {

    public PromptGenerator() {
    }

    public static String promptGen(String folderPath, String URL, Meta meta, String createTableSQL,
            AtomicLong promptFileNum) {
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
        String filePath = folderPath + "prompt" + promptFileNum + ".txt";
        promptFileNum.incrementAndGet();
        PrintWriter writer = null;
        try {
            // 创建PrintWriter对象，使用FileWriter作为其构造参数
            writer = new PrintWriter(new FileWriter(filePath));

            writer.println("现在你是一个精通数据库连接器测试和 Java 语言的专家。");
            writer.println();
            writer.println("数据库连接器连接的URL为：");
            writer.println(URL);
            writer.println();
            writer.println("已知当前我的数据库中表结构为：");
            // Assuming meta.getTable().toString() provides the desired table format
            // including 'Table Name:', 'Key Column:', 'Value Columns:' etc.
            writer.println(meta.getTable().toString());
            writer.println();
            writer.println("你的任务是为我生成一段 Java 代码，该代码需要满足以下要求：");
            writer.println();
            writer.println("1. 代码格式要求：");
            writer.println("   - 不包含 try-catch 块");
            writer.println("   - 不包含注释");
            writer.println("   - 每个方法调用独占一行");
            writer.println("   - 不使用while、if等流程语句");
            writer.println("   - 保持简洁的代码风格");
            writer.println("   - 代码在最开始初始化对应的各个类的对象为null");
            writer.println("   - 示例格式：");
            writer.println("     Connection con = null;");
            writer.println("     Statement stmt = null;");
            writer.println("     ResultSet rs = null;");
            writer.println("     ... ");
            writer.println("     stmt = con.createStatement(1003, 1007, 1);");
            writer.println("     stmt.getQueryTimeout();");
            writer.println("...");
            writer.println("     rs.close();");
            writer.println("...");
            writer.println("     stmt.close();");
            writer.println("     con.close();");
            // Note: Corrected table name placeholder in example
            writer.println("     stmt.executeUpdate(\"DROP TABLE IF EXISTS table_name;\");");
            writer.println();
            writer.println("2. 代码量要求：");
            writer.println("   - 生成的代码应该包含至少 150 行有效代码");
            writer.println("   - 每个重要的 JDBC 方法应该被调用多次，使用不同的参数");
            writer.println("   - 应该包含多个完整的测试流程");
            writer.println();
            writer.println("3. 代码结构要求：");
            writer.println("   - 代码应该按照自然的执行顺序编写");
            writer.println("   - 不封装，不分模块，结构混乱而完整，模拟真实 JDBC 混乱操作场景");
            writer.println("   - 结果集对象(rs)一旦创建且不为null，可以在代码中任何位置随时调用提示词中rs的所有方法，无需考虑逻辑顺序");
            writer.println("   - 单个rs相关的操作可以频繁穿插在其他操作之间，呈现随机分布状态");
            writer.println("   - 事务控制应该自然地融入代码流程中");
            writer.println("   - 批处理操作应该与其他操作自然地混合");
            writer.println("   - 性能设置应该在需要时自然地添加");
            writer.println("   - 异常测试应该自然地融入正常流程中");
            writer.println("   - 不同类的操作应该高度耦合，而不是分模块");
            writer.println("   - 同一个对象的方法调用可以分散在不同位置");
            writer.println("   - 代码应该反映真实的数据库操作场景");
            writer.println();
            writer.println("4. 允许使用的JDBC类和方法：");
            writer.println("   4.1 DriverManager类：");
            writer.println("       - getConnection(URL)");
            writer.println();
            writer.println("   4.2 Connection类：");
            writer.println("       - 事务控制：commit(), rollback(), rollback(Savepoint), setAutoCommit(boolean)");
            writer.println("       - 保存点：setSavepoint(), setSavepoint(String)");
            writer.println("       - 连接属性：isReadOnly(), getHoldability()");
            writer.println("       - 语句创建：createStatement(int, int, int)");
            writer.println(
                    "       - 预处理语句：prepareStatement(String), prepareStatement(String, int), prepareStatement(String, int[]), prepareStatement(String, String[]) //");
            writer.println("       - 资源管理：close()");
            writer.println();
            writer.println("   4.3 Statement类：");
            writer.println(
                    "       - 查询执行：executeQuery(String sql), executeUpdate(String sql), executeLargeUpdate(String sql)");
            writer.println("       - 批处理：executeBatch(), executeLargeBatch(), addBatch(String sql), clearBatch()");
            writer.println("       - 结果集控制：getResultSet(), getUpdateCount(), getMoreResults(), getLargeUpdateCount()");
            writer.println("       - 自动生成键：getGeneratedKeys()");
            writer.println("       - 字段控制：getMaxFieldSize(), setMaxFieldSize(int)");
            writer.println("       - 获取方向：setFetchDirection(int), getFetchDirection()");
            writer.println("       - 获取大小：setFetchSize(int), getFetchSize()");
            writer.println("       - 结果集属性：getResultSetConcurrency(), getResultSetType(), getResultSetHoldability()");
            writer.println("       - 行数控制：setMaxRows(int), getMaxRows()");
            writer.println("       - 转义处理：setEscapeProcessing(boolean)");
            writer.println("       - 资源管理：close(), closeOnCompletion()");
            writer.println();
            writer.println("   4.4 PreparedStatement类：");
            writer.println("       - 参数设置：setObject()");
            writer.println("       - 批处理：addBatch(), executeBatch()");
            writer.println("       - 执行操作：execute(), executeQuery(), executeUpdate(), executeLargeUpdate()");
            writer.println("       - 参数清理：clearParameters()");
            writer.println();
            writer.println("   4.5 ResultSet类：");
            writer.println("       - 游标移动：next(), previous(), beforeFirst(), afterLast()");
            writer.println("       - 数据获取：getObject(int), getObject(String)");
            writer.println(
                    "       - 行更新：cancelRowUpdates(), updateObject(int, Object), updateObject(String, Object), updateRow()");
            writer.println("       - 行状态：rowDeleted(), rowInserted(), rowUpdated()");
            writer.println("       - 游标位置：isFirst(), isLast(), isAfterLast(), isBeforeFirst()");
            writer.println("       - 结果集属性：getHoldability(), getType()");
            writer.println("       - 获取控制：setFetchSize(int), setFetchDirection(int) ");
            writer.println("       - 资源管理：close()");
            writer.println();
            writer.println("5. 测试场景要求：");
            writer.println("   5.1 基本操作：");
            writer.println("       - 建立连接");
            writer.println("       - 创建表");
            writer.println("       - 插入数据");
            writer.println("       - 查询数据");
            writer.println("       - 更新数据");
            writer.println("       - 删除数据");
            writer.println();
            writer.println("   5.2 高级操作：");
            writer.println("       - 事务控制");
            writer.println("       - 批处理");
            writer.println("       - 预处理语句");
            writer.println("       - 结果集操作");
            writer.println("       - 性能设置");
            writer.println();
            writer.println("   5.3 异常测试和极限参数设置：");
            writer.println("       - 使用极端参数值：最大/最小整数值、特殊字符、超长字符串等");
            writer.println("       - 对数值字段测试边界值（Integer.MAX_VALUE, Long.MIN_VALUE等）");
            writer.println("       - 对字符字段测试各种特殊字符、Unicode字符、极长字符串");
            writer.println("       - 使用极端的fetch size和max rows设置（非常大或非常小的值）");
            writer.println("       - 使用极端的批处理大小");
            writer.println("       - 重复主键测试");
            writer.println("       - 违反约束条件测试");
            writer.println("       - 无效SQL语句测试");
            writer.println("       - 连接超时测试");
            writer.println("       - 资源耗尽测试");
            writer.println();
            writer.println("6. 代码生成要求：");
            writer.println("   - 生成的代码应该能够测试不同数据库连接器的行为差异");
            writer.println("   - 代码应该包含所有重要的JDBC方法调用");
            writer.println("   - 代码应该按照自然的执行顺序编写");
            writer.println("   - 代码应该易于理解和维护");
            writer.println("   - 代码应该遵循Java编码规范");
            writer.println("   - 代码应该包含完整的测试流程");
            writer.println("   - 代码应该包含必要的变量声明");
            writer.println("   - 代码应该包含资源清理");
            writer.println("   - 代码应该保持简洁和清晰");
            writer.println("   - 代码应该反映真实的数据库操作场景");
            writer.println("   - 代码应该避免模块化的结构");
            writer.println("   - 代码应该让不同类的操作高度耦合");
            writer.println();
            writer.println("7. 输出格式：");
            writer.println("   - 生成的代码应该直接可执行");
            writer.println("   - 代码应该包含必要的变量声明");
            writer.println("   - 代码应该包含资源清理");
            writer.println("   - 代码应该保持简洁和清晰");
            writer.println("   - 代码应该按照自然的执行顺序编写");
            writer.println();
            // Instead of just printing the SQL, maybe add a section for it?
            writer.println("// Create Table SQL: " + createTableSQL);

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
