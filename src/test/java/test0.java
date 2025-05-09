import java.sql.*;

public class test0 {
    public static void main(String[] args) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb0?user=root&password=1234");
        stmt = con.createStatement(1003, 1007, 1);
        stmt.executeUpdate("DROP TABLE IF EXISTS table0_0");
        stmt.executeUpdate("CREATE TABLE table0_0 (Id DECIMAL PRIMARY KEY, Value0 VARCHAR(5))");

        pstmt = con.prepareStatement("INSERT INTO table0_0 (Id, Value0) VALUES (?, ?)");
        pstmt.setObject(1, Integer.MAX_VALUE);
        pstmt.setObject(2, "abcde");
        pstmt.addBatch();
        pstmt.setObject(1, Integer.MIN_VALUE);
        pstmt.setObject(2, "ABCDE");
        pstmt.addBatch();
        pstmt.executeBatch();

        rs = stmt.executeQuery("SELECT * FROM table0_0 WHERE Id = " + Integer.MAX_VALUE);
        rs.next();
        rs.getObject(1);
        rs.getObject("Value0");
        try {
            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            rs.afterLast();
        } catch (SQLException e) {
            System.out.println(e);
        }


        stmt.setMaxRows(1);
        try {
            stmt.setFetchSize(0);
            stmt.setMaxRows(1);
        } catch (SQLException e) {
            System.out.println("error " + e);
        }
        System.out.println("size:" + stmt.getFetchSize());
        stmt.setEscapeProcessing(true);
        stmt.setFetchDirection(1001);

        stmt.executeUpdate("UPDATE table0_0 SET Value0 = 'xyz' WHERE Id = " + Integer.MAX_VALUE);
        stmt.getUpdateCount();
        stmt.getMoreResults();

        stmt.setMaxFieldSize(100);
        stmt.getResultSetConcurrency();
        stmt.getResultSetType();
        stmt.getResultSetHoldability();

        con.setAutoCommit(false);
        Savepoint sp = con.setSavepoint("savepoint1");
        stmt.executeUpdate("DELETE FROM table0_0 WHERE Id = " + Long.MIN_VALUE);
        con.rollback(sp);
        con.commit();

        stmt.clearWarnings();
        stmt.closeOnCompletion();
        stmt.getQueryTimeout();

        pstmt.clearParameters();
        pstmt.setObject(1, 12345);
        pstmt.setObject(2, "特殊字符");
        pstmt.execute();

        try {
            rs = pstmt.getGeneratedKeys();
        } catch (SQLException e) {
            System.out.println(e);
        }
        rs.next();
        rs.getObject(1);

        stmt.setFetchDirection(1002);
        stmt.setFetchSize(1);
        stmt.setMaxRows(Integer.MIN_VALUE);
        rs = stmt.executeQuery("SELECT * FROM table0_0");
        rs.next();
        rs.getObject(1);
        rs.getObject("Value0");
        rs.isFirst();
        rs.isLast();
        rs.isAfterLast();
        rs.isBeforeFirst();

        stmt.executeLargeUpdate("TRUNCATE TABLE table0_0");
        stmt.getLargeUpdateCount();

        stmt.close();
        pstmt.close();
        rs.close();
        con.close();
    }
}
