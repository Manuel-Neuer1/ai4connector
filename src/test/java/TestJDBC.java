import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestJDBC {

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Savepoint savepoint = null;

        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb0?user=root&password=1234");
        //conn = DriverManager.getConnection("jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234");
        conn.setAutoCommit(false);
        stmt = conn.createStatement(1003, 1007, 1);
        System.out.println(stmt.getQueryTimeout());
        stmt.setQueryTimeout(30);
        System.out.println(stmt.getFetchSize());
        stmt.setFetchSize(50);
        System.out.println(stmt.getMaxRows());
        stmt.setMaxRows(100);
        stmt.setEscapeProcessing(true);
        System.out.println(stmt.getResultSetType());
        System.out.println(stmt.getResultSetConcurrency());
        System.out.println(stmt.getResultSetHoldability());

        System.out.println(stmt.executeUpdate("DROP TABLE IF EXISTS table0_0;"));
        System.out.println(stmt.executeLargeUpdate(
                "CREATE TABLE table0_0(Id VARCHAR(5) PRIMARY KEY, Value0 TINYINT, Value1 DECIMAL, Value2 BIGINT);", 1));
        savepoint = conn.setSavepoint();

        stmt.addBatch("INSERT INTO table0_0 VALUES ('ID1', 1, 10.5, 100);");
        stmt.addBatch("INSERT INTO table0_0 VALUES ('ID2', 2, 20.5, 200);");
        System.out.println(Arrays.toString(stmt.executeBatch()));
        rs = stmt.executeQuery("SELECT * FROM table0_0;");
        System.out.println(rs.next());
        System.out.println(rs.getObject("Id"));
        System.out.println(rs.getObject("Value0"));
        System.out.println(rs.getObject("Value1"));
        System.out.println(rs.getObject("Value2"));
        System.out.println(rs.isFirst());
        System.out.println(rs.isLast());
        System.out.println(rs.isBeforeFirst());
        System.out.println(rs.isAfterLast());

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println(stmt.executeUpdate("UPDATE table0_0 SET Value0 = 10 WHERE Id = 'ID1';"));

        try {
            conn.rollback(savepoint);
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e);
        }

        conn.setAutoCommit(false);
        savepoint = conn.setSavepoint("SP1");
        System.out.println(stmt.executeUpdate("INSERT INTO table0_0 VALUES ('ID3', 3, 30.5, 300);"));
        System.out.println(stmt.executeUpdate("INSERT INTO table0_0 VALUES ('ID4', 4, 40.5, 400);"));
        rs = stmt.executeQuery("SELECT * FROM table0_0 WHERE Value0 > 2;");
        rs.setFetchSize(100);
        System.out.println(rs.next());
        System.out.println(rs.getObject(1));
        System.out.println(rs.getObject(2));
        System.out.println(rs.getObject(3));
        System.out.println(rs.getObject(4));

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        pstmt = conn.prepareStatement("INSERT INTO table0_0 VALUES (?, ?, ?, ?);");
        pstmt.setObject(1, "ID5");
        pstmt.setObject(2, 5);
        pstmt.setObject(3, 50.5);
        pstmt.setObject(4, 500);
        System.out.println(pstmt.executeUpdate());
        pstmt.clearParameters();
        try{
            pstmt.addBatch();
        }catch (SQLException e){
            System.out.println(e);
        }
        System.out.println(Arrays.toString(pstmt.executeBatch()));

        rs = stmt.executeQuery("SELECT * FROM table0_0 WHERE Value0 = 5;");
        System.out.println(rs.next());

        try {
            rs.updateObject("Value0", 15);
            rs.updateRow();
        } catch (SQLException e) {
            System.out.println(e);//
        }

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println(stmt.getQueryTimeout());
        stmt.setQueryTimeout(60);
        System.out.println(stmt.getMaxRows());
        stmt.setMaxRows(200);
        System.out.println(stmt.getFetchSize());
        stmt.setFetchSize(150);
        rs = stmt.executeQuery("SELECT * FROM table0_0;");
        try{
            rs.setFetchDirection(1001);
        }catch (SQLException e){
            System.out.println(e);
        }
        System.out.println(rs.next());
        try{
            rs.previous();
        }catch (SQLException e){
            System.out.println(e);
        }
        try{
            rs.beforeFirst();
        }catch (SQLException e){
            System.out.println(e);
        }
        try{
            rs.afterLast();
        }catch (SQLException e) {
            System.out.println(e);
        }

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            System.out.println(stmt.executeUpdate("INSERT INTO table0_0 VALUES ('ID1', 1, 10.5, 100);"));
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            System.out.println(stmt.executeUpdate("UPDATE table0_0 SET Value0 = 200 WHERE Id = 'ID2';"));
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            stmt.executeQuery("SELECT * FROM non_existent_table;");
        } catch (SQLException e) {
            System.out.println(e);
        }

        conn.setAutoCommit(false);
        savepoint = conn.setSavepoint("SP2");
        System.out.println(stmt.executeUpdate("UPDATE table0_0 SET Value1 = 15.5 WHERE Id = 'ID3';"));

        try {
            conn.rollback(savepoint);
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e);
        }

        rs = stmt.executeQuery("SELECT * FROM table0_0 WHERE Value0 > 1 OR Id != 'ID2';");
        rs.setFetchSize(200);
        System.out.println(rs.next());
        System.out.println(rs.getObject("Id"));
        System.out.println(rs.getObject("Value0"));
        System.out.println(rs.getObject("Value1"));
        System.out.println(rs.getObject("Value2"));
        System.out.println(rs.isFirst());
        System.out.println(rs.isLast());
        System.out.println(rs.isBeforeFirst());
        System.out.println(rs.isAfterLast());

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println(stmt.getResultSetConcurrency());
        System.out.println(stmt.getResultSetType());
        System.out.println(stmt.getResultSetHoldability());

        pstmt = conn.prepareStatement("SELECT * FROM table0_0 WHERE Value0 > ?");
        pstmt.setObject(1, 2);
        rs = pstmt.executeQuery();
        System.out.println(rs.next());
        System.out.println(rs.getObject(1));
        System.out.println(rs.getObject(2));
        System.out.println(rs.getObject(3));
        System.out.println(rs.getObject(4));

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println(stmt.getQueryTimeout());
        stmt.setQueryTimeout(120);
        System.out.println(stmt.getMaxRows());
        stmt.setMaxRows(500);
        System.out.println(stmt.getFetchSize());
        stmt.setFetchSize(250);
        rs = stmt.executeQuery("SELECT * FROM table0_0;");
        rs.setFetchDirection(1001);
        System.out.println(rs.next());
        rs.previous();
        rs.beforeFirst();
        rs.afterLast();

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        conn.setAutoCommit(false);
        savepoint = conn.setSavepoint("SP3");
        System.out.println(stmt.executeUpdate("UPDATE table0_0 SET Value2 = 150 WHERE Id = 'ID4';"));

        try {
            conn.rollback(savepoint);
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e);
        }

        rs = stmt.executeQuery("SELECT * FROM table0_0 WHERE Value1 > 20.0 AND Value2 < 400;");
        rs.setFetchSize(300);
        System.out.println(rs.next());
        System.out.println(rs.getObject("Id"));
        System.out.println(rs.getObject("Value0"));
        System.out.println(rs.getObject("Value1"));
        System.out.println(rs.getObject("Value2"));
        System.out.println(rs.isFirst());
        System.out.println(rs.isLast());
        System.out.println(rs.isBeforeFirst());
        System.out.println(rs.isAfterLast());

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            System.out.println(stmt.executeUpdate("UPDATE table0_0 SET Value0 = -200 WHERE Id = 'ID2';"));
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            stmt.executeQuery("SELECT invalid_column FROM table0_0;");
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            stmt.executeQuery("SELECT * FROM table0_0 WHERE invalid_condition;");
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}