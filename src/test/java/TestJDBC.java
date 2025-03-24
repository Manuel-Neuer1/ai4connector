import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestJDBC {

    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Savepoint sp = null;

        try {
            // 获取连接
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test18?user=root&password=1234");

            // 创建 Statement
            stmt = con.createStatement(1004, 1008, 1);
            System.out.println(stmt.executeUpdate("DROP TABLE IF EXISTS table18_0;"));
            System.out.println(stmt.executeLargeUpdate("CREATE TABLE table18_0(id VARCHAR(100) PRIMARY KEY, value VARCHAR(100));", 1));
            stmt.closeOnCompletion();

            // 再次创建 Statement
            stmt = con.createStatement(1005, 1007, 2);
            stmt.addBatch("DELETE FROM table18_0 WHERE (table18_0.value > '0');");
            System.out.println(Arrays.toString(stmt.executeLargeBatch()));

            // 提交事务
            try {
                con.commit();
            } catch (Exception e) {
                System.out.println(e);
            }

            // 设置保存点
            sp = con.setSavepoint();

            // 创建 PreparedStatement
            stmt = con.prepareStatement("INSERT INTO table18_0 VALUES(?, ?);", 1005, 1008, 2);
            con.setAutoCommit(true);
            stmt.closeOnCompletion();

            // 获取网络超时时间
            System.out.println(con.getNetworkTimeout());

            // 再次创建 Statement
            stmt = con.createStatement(1003, 1007, 2);
            System.out.println(stmt.getMaxFieldSize());

            // 添加批量操作
            stmt.addBatch("DELETE FROM table18_0 WHERE (table18_0.id <> 'test') AND (table18_0.value = 'test');");
            stmt.addBatch("UPDATE table18_0 SET value = 'updated' WHERE (table18_0.id != 'test') OR (table18_0.value >= 'test');");
            stmt.addBatch("DELETE FROM table18_0 WHERE (table18_0.id >= 'test');");
            System.out.println(Arrays.toString(stmt.executeLargeBatch()));

            // 创建 PreparedStatement
            stmt = con.prepareStatement("SELECT table18_0.value FROM table18_0 WHERE table18_0.value = ?", 1005, 1007, 1);
            stmt.setFetchSize(100);
            stmt.closeOnCompletion();

            // 设置自动提交
            con.setAutoCommit(true);

            // 再次创建 Statement
            stmt = con.createStatement(1003, 1007, 1);
            System.out.println(stmt.getFetchDirection());
            stmt.setFetchSize(100);
            System.out.println(stmt.executeLargeUpdate("UPDATE table18_0 SET value = 'updated' WHERE (table18_0.id = 'test');", 1));

            // 获取结果集
            rs = stmt.getGeneratedKeys();
            System.out.println(rs.next());

            // 捕获异常
            System.out.println("ERROR:");
            try {
                rs.getHoldability();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.updateObject(2, "test");
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(rs.isFirst());
            try {
                rs.cancelRowUpdates();
            } catch (Exception e) {
                System.out.println(e);
            }

            // 设置事务隔离级别
            con.setTransactionIsolation(2);
            System.out.println(rs.previous());

            // 执行查询
            rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.value != 'test');");
            try {
                con.commit();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(rs.next());
            System.out.println(stmt.getResultSetConcurrency());

            // 执行查询
            rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
            System.out.println(rs.isAfterLast());
            System.out.println(rs.getType());
            System.out.println(rs.next());

            // 捕获异常
            try {
                rs.updateObject(2, "test");
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(rs.getType());
            try {
                rs.cancelRowUpdates();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(stmt.getResultSetType());

            // 捕获异常
            try {
                rs.updateObject(2, "test");
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con.commit();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(con.getHoldability());

            // 捕获异常
            try {
                rs.updateRow();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.beforeFirst();
            } catch (Exception e) {
                System.out.println(e);
            }

            // 设置自动提交
            con.setAutoCommit(true);
            System.out.println(rs.next());
            rs.setFetchDirection(1000);

            // 捕获异常
            try {
                rs.updateObject(2, "test");
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(stmt.getMoreResults(3));
            System.out.println(stmt.getUpdateCount());

            // 捕获异常
            try {
                rs.updateRow();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.afterLast();
            } catch (Exception e) {
                System.out.println(e);
            }

            // 捕获异常
            System.out.println("ERROR:");
            try {
                rs.isLast();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.updateObject(1, "test");
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con.rollback();
            } catch (Exception e) {
                System.out.println(e);
            }

            // 捕获异常
            System.out.println("ERROR:");
            try {
                rs.isAfterLast();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.cancelRowUpdates();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(con.getHoldability());
            System.out.println(stmt.executeLargeUpdate("INSERT INTO table18_0 VALUES('test', 'test')", 2));

            // 执行查询
            rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
            System.out.println(rs.next());

            // 捕获异常
            try {
                rs.updateObject(1, "test");
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.updateRow();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.beforeFirst();
            } catch (Exception e) {
                System.out.println(e);
            }

            // 捕获异常
            System.out.println("ERROR:");
            try {
                rs.getHoldability();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(rs.next());
            System.out.println(rs.isFirst());

            // 捕获异常
            try {
                con.rollback();
            } catch (Exception e) {
                System.out.println(e);
            }
            rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.id <= 'test') OR (table18_0.id <> 'test');");
            System.out.println(stmt.getMoreResults(1));

            // 捕获异常
            try {
                rs.isAfterLast();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.next();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.updateObject(2, "test");
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(rs.getType());

            // 捕获异常
            try {
                rs.cancelRowUpdates();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.updateObject(1, "test");
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(stmt.getResultSetConcurrency());

            // 捕获异常
            try {
                rs.updateRow();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(stmt.executeLargeUpdate("INSERT INTO table18_0 VALUES('test', 'test')", 2));
            System.out.println(con.getHoldability());

            // 捕获异常
            try {
                rs.beforeFirst();
            } catch (Exception e) {
                System.out.println(e);
            }
            con.setTransactionIsolation(1);
            System.out.println(stmt.getResultSetConcurrency());

            // 捕获异常
            try {
                rs.next();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(con.getNetworkTimeout());

            // 捕获异常
            try {
                rs.beforeFirst();
            } catch (Exception e) {
                System.out.println(e);
            }
            con.setAutoCommit(true);
            System.out.println(con.isReadOnly());

            // 捕获异常
            try {
                rs.next();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                con.rollback(sp);
            } catch (Exception e) {
                System.out.println(e);
            }

            // 捕获异常
            try {
                rs.afterLast();
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                rs.updateObject(1, "test");
            } catch (Exception e) {
                System.out.println(e);
            }

            // 关闭资源
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}