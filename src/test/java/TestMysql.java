import java.sql.*;


public class TestMysql {
    public static void main(String[] args) throws SQLException {
//        Connection conn = null;
//        Statement stmt = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Savepoint savepoint = null;
//
//
//        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test18?user=root&password=1234");
//
//
//        // 2. 设置自动提交模式
//        conn.setAutoCommit(false);
//
//        // 3. 设置事务隔离级别
//        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//
//        // 4. 创建 Statement 对象
//        stmt = conn.createStatement(1004, 1008, 1);
//        System.out.println(stmt.executeUpdate("DROP TABLE table18_1;"));
//        System.out.println(stmt.executeLargeUpdate("CREATE TABLE table18_1(Id VARCHAR(100) PRIMARY KEY, Value0 VARCHAR(100), Value1 BIGINT, Value2 CHAR);", 1));
//        // 5. 执行 SQL 查询
//        rs = stmt.executeQuery("SELECT * FROM table18_1");
//
//        // 6. 遍历结果集
//        rs.next();
//
//        // 8. 关闭结果集
//        rs.close();
//
//        // 9. 执行更新操作
//        int updateCount = stmt.executeUpdate("UPDATE table18_1 SET Value0 = 'updated' WHERE Id = 1.0");
//
//        // 10. 获取更新计数
//        System.out.println("Update count: " + updateCount);
//
//        // 11. 设置保存点
//        savepoint = conn.setSavepoint("savepoint1");
//
//        // 12. 执行批量更新
//        stmt.addBatch("INSERT INTO table18_1 (Id, Value0, Value1, Value2) VALUES (4.0, 'batch1', 400, 'D')");
//        stmt.addBatch("INSERT INTO table18_1 (Id, Value0, Value1, Value2) VALUES (5.0, 'batch2', 500, 'E')");
//        stmt.executeBatch();
//
//
//
//        // 14. 回滚到保存点
//        conn.rollback(savepoint);
//
//        // 15. 创建 PreparedStatement 对象
//        pstmt = conn.prepareStatement("INSERT INTO table18_1 (Id, Value0, Value1, Value2) VALUES (?, ?, ?, ?)");
//
//        // 16. 设置 PreparedStatement 参数
//        pstmt.setFloat(1, 6.0f);
//        pstmt.setString(2, "prep1");
//        pstmt.setLong(3, 600L);
//        pstmt.setString(4, "F");
//
//        // 17. 执行 PreparedStatement 插入
//        int prepUpdateCount = pstmt.executeUpdate();
//
//        // 18. 获取生成的键值
////        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
////            if (generatedKeys.next()) {
////                System.out.println("Generated key: " + generatedKeys.getInt(1));
////            }
////        }
//
//        // 19. 设置只读模式
//        boolean isReadOnly = conn.isReadOnly();
//        System.out.println("Is read-only: " + isReadOnly);
//
//        // 20. 获取结果集保持性
//        int holdability = conn.getHoldability();
//        System.out.println("Holdability: " + holdability);
//
//        // 24. 获取查询超时
//        int queryTimeout = stmt.getQueryTimeout();
//        System.out.println("Query timeout: " + queryTimeout);
//
//        // 25. 设置查询超时
//        stmt.setQueryTimeout(30);
//
//        // 26. 获取结果集并发性
//        int resultSetConcurrency = stmt.getResultSetConcurrency();
//        System.out.println("Result set concurrency: " + resultSetConcurrency);
//
//        // 27. 获取结果集类型
//        int resultSetType = stmt.getResultSetType();
//        System.out.println("Result set type: " + resultSetType);
//
//        // 28. 获取结果集保持性
//        int resultSetHoldability = stmt.getResultSetHoldability();
//        System.out.println("Result set holdability: " + resultSetHoldability);
//
//        // 29. 设置获取方向
//        stmt.setFetchDirection(ResultSet.FETCH_FORWARD);
//
//        // 30. 获取获取方向
//        int fetchDirection = stmt.getFetchDirection();
//        System.out.println("Fetch direction: " + fetchDirection);
//
//        // 31. 设置获取大小
//        stmt.setFetchSize(10);
//
//        // 32. 获取获取大小
//        int fetchSize = stmt.getFetchSize();
//        System.out.println("Fetch size: " + fetchSize);
//
//        // 33. 设置最大行数
//        stmt.setMaxRows(100);
//
//        // 34. 获取最大行数
//        int maxRows = stmt.getMaxRows();
//        System.out.println("Max rows: " + maxRows);
//
//        // 35. 设置转义处理
//        stmt.setEscapeProcessing(true);
//
//        // 36. 执行大型更新
//        long largeUpdateCount = stmt.executeLargeUpdate("UPDATE table18_1 SET Value0 = 'largeUpdate' WHERE Id = 1.0");
//        System.out.println("Large update count: " + largeUpdateCount);
//
//        // 37. 执行大型批量更新
//        stmt.addBatch("INSERT INTO table18_1 (Id, Value0, Value1, Value2) VALUES (7.0, 'largeBatch1', 700, 'G')");
//        stmt.addBatch("INSERT INTO table18_1 (Id, Value0, Value1, Value2) VALUES (8.0, 'largeBatch2', 800, 'H')");
//        long[] largeBatchUpdateCounts = stmt.executeLargeBatch();
//
//        // 38. 获取大型批量更新计数
//        for (long count : largeBatchUpdateCounts) {
//            System.out.println("Large batch update count: " + count);
//        }
//
//        // 39. 清除批量操作
//        stmt.clearBatch();
//
//        // 40. 执行 SQL 语句
//        boolean executed = stmt.execute("SELECT * FROM table18_1");
//        System.out.println("Executed: " + executed);
//
//        // 41. 获取结果集
//        rs = stmt.getResultSet();
//
//        // 42. 获取更新计数
//        int updateCountAfterExecute = stmt.getUpdateCount();
//        System.out.println("Update count after execute: " + updateCountAfterExecute);
//
//        // 43. 获取更多结果
//        boolean moreResults = stmt.getMoreResults();
//        System.out.println("More results: " + moreResults);
//
//        // 44. 获取大型更新计数
//        long largeUpdateCountAfterExecute = stmt.getLargeUpdateCount();
//        System.out.println("Large update count after execute: " + largeUpdateCountAfterExecute);
//
//        // 45. 关闭语句
//        stmt.close();
//
//        // 46. 提交事务
//        conn.commit();
//
//        // 47. 关闭连接
//        conn.close();
//
//        // 48. 回滚事务n
//        // 49. 关闭资源
//        try {
//            if (rs != null) rs.close();
//            if (stmt != null) stmt.close();
//            if (pstmt != null) pstmt.close();
//            if (conn != null) conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Savepoint sp = null;
// con = DriverManager.getConnection("jdbc:mariadb://localhost:3366/test269?user=root&password=123456&allowMultiQueries=true&rewriteBatchedStatements=true&tinyInt1isBit=false&createDatabaseIfNotExist=true&cacheCallableStmts=false");
        //con = DriverManager.getConnection("jdbc:mysql://localhost:3366/test269?user=root&password=123456&allowMultiQueries=true&rewriteBatchedStatements=true&tinyInt1isBit=false&createDatabaseIfNotExist=true&cacheCallableStmts=false");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test18?user=root&password=1234");
        stmt = con.createStatement(1003, 1007, 1);
        System.out.println(con.isReadOnly());
        sp = con.setSavepoint();
        stmt.executeUpdate("DROP TABLE IF EXISTS table269_0;");
        System.out.println(stmt.executeUpdate("CREATE TABLE table269_0(id BIGINT PRIMARY KEY,value VARCHAR(5));", 1));
        System.out.println(stmt.getResultSetConcurrency());
        System.out.println(stmt.getFetchSize());
        rs = stmt.executeQuery("SELECT id, value FROM table269_0;");
        System.out.println(stmt.getResultSetHoldability());
        try {
            con.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.next());

        System.out.println("ERROR:");
        try {
            rs.afterLast();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.beforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.setFetchDirection(1002);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.next());
        System.out.println(stmt.getResultSetHoldability());
        try {
            rs.rowUpdated();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateObject(2, 910002451);
        } catch (Exception e) {
            System.out.println(e);
        }
        con.setTransactionIsolation(2);
        System.out.println(stmt.getUpdateCount());
        try {
            rs.updateRow();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.getHoldability();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.rowUpdated();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateObject(1, 225705182);
        } catch (Exception e) {
            System.out.println("11111111111");
            System.out.println(e);
            System.out.println("22222222222");
        }
        try {
            con.rollback(sp);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.cancelRowUpdates();
        } catch (Exception e) {
            System.out.println(e);
        }
        rs = stmt.executeQuery("SELECT id, value FROM table269_0;");
        System.out.println(rs.getType());
        System.out.println(rs.next());
        System.out.println(stmt.getResultSetType());
        try {
            rs.rowDeleted();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.beforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(stmt.getLargeUpdateCount());
        System.out.println(stmt.getResultSetType());
        System.out.println(rs.next());
        rs = stmt.executeQuery("SELECT id, value FROM table269_0 WHERE (table269_0.value > 'j') OR (table269_0.id > -9052785909881487205);");
        System.out.println(stmt.getResultSetConcurrency());
        System.out.println(stmt.getResultSetConcurrency());
        System.out.println(rs.next());
        try {
            rs.rowInserted();
        } catch (Exception e) {
            System.out.println(e);
        }
        sp = con.setSavepoint();
        rs = stmt.executeQuery("SELECT id, value FROM table269_0 WHERE (table269_0.value > 'eT6jIoE2!aBnpq@GK8gb;J') AND (table269_0.id != -6305954557349723889--6458880561418285542--3293896792246249967);");
        try {
            con.rollback();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(stmt.getUpdateCount());
        System.out.println(rs.next());
        System.out.println(stmt.getResultSetHoldability());
        try {
            rs.updateObject(1, "'dT23gfjrQx.yu8mgNdZ$.QftGpYB0s#sANwrenFmO'");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(con.getHoldability());
        try {
            rs.updateRow();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(stmt.getResultSetConcurrency());
        con.close();
        stmt.close();
        stmt.close();
        stmt.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
    }
}
