import java.math.BigDecimal;
import java.sql.*;

public class testtt {
    public static void main(String[] args) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb0?user=root&password=1234&useServerPrepStmts=false&allowMultiQueries=true&dumpQueriesOnException=true&tinyInt1isBit=false&yearIsDateType=true&createDatabaseIfNotExist=true");
        stmt = con.createStatement(1005, 1007, 1);
        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS table0_2;");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            stmt.executeUpdate("CREATE TABLE table0_2 (Id INT PRIMARY KEY, Value0 TINYINT, Value1 TEXT(100));");
        } catch (Exception e) {
            System.out.println(e);
        }

        stmt.setMaxFieldSize(1024);
        try {
            stmt.setFetchDirection(1001);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            stmt.setFetchSize(100);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(stmt.getResultSetConcurrency());
        System.out.println(stmt.getResultSetType());
        System.out.println(stmt.getResultSetHoldability());

        pstmt = con.prepareStatement("INSERT INTO table0_2 (Id, Value0, Value1) VALUES (?, ?, ?)", new String[] {"Id"});
        pstmt.setObject(1, Integer.MAX_VALUE);
        pstmt.setObject(2, Byte.MIN_VALUE);
        pstmt.setObject(3, "Special Characters: !@#$%^&*()");
        pstmt.addBatch();
        pstmt.clearParameters();

        try {
            stmt.executeLargeUpdate("INSERT INTO table0_2 (Id, Value0, Value1) VALUES (1, 127, 'Unicode Test: \u03A9\u03B1\u03B2');");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            stmt.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(stmt.getMoreResults());
        System.out.println(stmt.getLargeUpdateCount());

        try {
            rs = stmt.executeQuery("SELECT * FROM table0_2 WHERE Id = 1;");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.next());
        rs.getObject(1);
        rs.getObject("Value1");
        try {
            rs.cancelRowUpdates();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateObject(2, Byte.MIN_VALUE);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateRow();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.isFirst());
        System.out.println(rs.isLast());
        System.out.println(rs.isAfterLast());
        System.out.println(rs.isBeforeFirst());

        stmt.setMaxRows(10);
        System.out.println(stmt.getMaxRows());
        stmt.setEscapeProcessing(true);

        pstmt = con.prepareStatement("UPDATE table0_2 SET Value0 = ?, Value1 = ? WHERE Id = ?", new int[] {1});
        pstmt.setObject(1, Byte.MAX_VALUE);
        pstmt.setObject(2, "Long String Test: ".repeat(100));
        pstmt.setObject(3, 1);
        try {
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            pstmt.getGeneratedKeys();
        } catch (Exception e) {
            System.out.println(e);
        }

        stmt.execute("DELETE FROM table0_2 WHERE Id = 1;");
        stmt.getQueryTimeout();
        try {
            stmt.setFetchSize(10);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(stmt.getFetchSize());

        try {
            con.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        Savepoint sp = con.setSavepoint("savepoint1");
        try {
            con.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con.rollback(sp);
        } catch (Exception e) {
            System.out.println(e);
        }
        con.releaseSavepoint(sp);

        stmt.execute("ALTER TABLE table0_2 ADD COLUMN NewColumn INT;");
        stmt.execute("ALTER TABLE table0_2 DROP COLUMN NewColumn;");

        try {
            rs = stmt.executeQuery("SHOW TABLES;");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.beforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.afterLast();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.previous());
        System.out.println(rs.getHoldability());
        System.out.println(rs.getType());

        stmt.execute("SET SESSION wait_timeout = 1;");
        stmt.execute("SELECT SLEEP(2);");

        stmt.execute("INSERT INTO table0_2 (Id, Value0, Value1) VALUES (1, 127, 'Test')");
        stmt.execute("INSERT INTO table0_2 (Id, Value0, Value1) VALUES (1, 127, 'Duplicate Key Test')");

        stmt.execute("SELECT * FROM non_existent_table;");
        stmt.execute("SELECT * FROM table0_2 WHERE Id > ?");
        stmt.execute("SELECT * FROM table0_2 LIMIT ?");
        stmt.execute("SELECT * FROM table0_2 ORDER BY Id DESC;");

        stmt.execute("INSERT INTO table0_2 (Id, Value0, Value1) VALUES (?, ?, ?)");

        try {
            stmt.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }
        stmt.clearBatch();

        stmt.execute("SELECT * FROM table0_2;");
        rs = stmt.getResultSet();
        try {
            rs.setFetchSize(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.setFetchDirection(1003);
        } catch (Exception e) {
            System.out.println(e);
        }
        rs.close();

        stmt.execute("TRUNCATE TABLE table0_2;");
        stmt.execute("DROP TABLE table0_2;");

        stmt.closeOnCompletion();
        stmt.close();
        pstmt.close();
        con.close();




    }
}
