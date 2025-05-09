import java.sql.*;

public class reportBug1 {

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //con = DriverManager.getConnection("jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=true&rewriteBatchedStatements=true");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb0?user=root&password=1234&useServerPrepStmts=true&rewriteBatchedStatements=true&createDatabaseIfNotExist=false&cacheCallableStmts=true&useBulkStmts=false");
        stmt = con.createStatement(1003, 1007, 1);
        stmt.getQueryTimeout();
        stmt.setMaxRows(100);
        try {
            stmt.setFetchSize(50);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            stmt.setFetchDirection(1001);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS table0_0;");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            stmt.executeUpdate("CREATE TABLE table0_0 (Id DOUBLE PRIMARY KEY, Value0 TINYINT, Value1 DECIMAL);");
        } catch (Exception e) {
            System.out.println(e);
        }

        pstmt = con.prepareStatement("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (?, ?, ?);");
        pstmt.setObject(1, 1.0);
        pstmt.setObject(2, Byte.MAX_VALUE);
        pstmt.setObject(3, Double.MIN_VALUE);
        try {
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

        stmt.addBatch("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (2.0, -128, 100.5);");
        stmt.addBatch("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (3.0, 127, -200.25);");
        try {
            stmt.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            rs = stmt.executeQuery("SELECT * FROM table0_0;");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.next());
        rs.getObject(1);
        rs.getObject("Value1");
        try {
            System.out.println(rs.previous());
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

        stmt.setMaxFieldSize(1024);
        stmt.setEscapeProcessing(true);
        try {
            stmt.setFetchDirection(1002);
        } catch (Exception e) {
            System.out.println(e);
        }

        pstmt.clearParameters();
        pstmt.setObject(1, 4.0);
        pstmt.setObject(2, Byte.MIN_VALUE);
        pstmt.setObject(3, Double.MAX_VALUE);
        pstmt.addBatch();
        try {
            pstmt.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            con.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        Savepoint sp1 = con.setSavepoint("SP1");
        try {
            stmt.executeUpdate("UPDATE table0_0 SET Value0 = 0 WHERE Id = 1.0;");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con.rollback(sp1);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(stmt.getMoreResults());
        System.out.println(stmt.getUpdateCount());
        System.out.println(stmt.getLargeUpdateCount());
        try {
            stmt.getGeneratedKeys();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            rs.setFetchSize(10);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.setFetchDirection(1003);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.isFirst());
        System.out.println(rs.isLast());
        System.out.println(rs.isAfterLast());
        System.out.println(rs.isBeforeFirst());

        stmt.setMaxRows(Integer.MIN_VALUE);

        rs.close();
        pstmt.close();
        stmt.close();
        con.close();
    }
}
