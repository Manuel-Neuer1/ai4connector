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
        stmt.setFetchSize(2147483647);
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
        System.out.println("rs.isFirst: " + rs.isFirst()); //OB、Mysql 输出false
        System.out.println("rs.isLast: " + rs.isLast()); //OB、Mysql 输出false
        //rs.afterLast(); //结果集类型为 TYPE_FORWARD_ONLY OB没有报错，Mysql报错
        //rs.beforeFirst(); //这OB直接执行了，没有报错，Mysql报错抛出了异常
        //rs.afterLast(); //这OB直接执行了，没有报错，Mysql报错抛出了异常
        //System.out.println("疑惑!" + rs.last()); //这里也离谱，OB返回的是true，Mysql还是抛出异常 Operation not allowed for a result set of type ResultSet.TYPE_FORWARD_ONLY
        //System.out.println("疑惑！" + rs.first()); //这里更离谱，OB返回的是true，Mysql也是抛出异常 Operation not allowed for a result set of type ResultSet.TYPE_FORWARD_ONLY
        //System.out.println("疑惑！" + rs.previous());//Mysql这里直接抛出异常，但是OB这里只是输出false（createStatement(1003, 1007, 1)），mysql：Operation not allowed for a result set of type ResultSet.TYPE_FORWARD_ONLY.
        System.out.println(rs.next());
        System.out.println(rs.getObject("Id"));
        System.out.println(rs.getObject("Value0"));
        System.out.println(rs.getObject("Value1"));
        System.out.println(rs.getObject("Value2"));
        try{
            System.out.println(rs.isFirst());
        } catch (SQLException e) {
            System.out.println(e);
        }
        try{
            System.out.println(rs.isLast());
        } catch (SQLException e) {
            System.out.println(e);
        }
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
        //try {
            rs.setFetchSize(2147483647); //这里OB报错，mysql没有报错
        //} catch (SQLException e) {
        //    System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   OB : " + e);
        //}
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
        try {
            pstmt.addBatch();
        } catch (SQLException e) {
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
        //try {
            stmt.setFetchSize(-2147483648);
        //} catch (SQLException e) {
        //    System.out.println("ERROR2!!!!!!!!!");
        //    System.out.println(e);
        //}
        rs = stmt.executeQuery("SELECT * FROM table0_0;");
        try {
            rs.setFetchDirection(1001);
        } catch (SQLException e) {
            System.out.println(e);
        }
        //JDK 17 中给出的解释：如果发生数据库访问错误；在关闭的结果集上调用此方法或结果集类型为TYPE_FORWARD_ONLY且提取方向不是FETCH_FORWARD
        System.out.println("rs.getFetchDirection() : " + rs.getFetchDirection()); //这里OB输出的是1002！！！！！！Mysql输出的是1000（第一行->下一行），1002：ResultSet.FETCH_UNKNOWN表示结果集的处理方向由 JDBC 驱动程序和数据库系统决定，这里虽然没问题但是感觉怪怪的
        System.out.println(rs.next());
        try {
            rs.previous(); // OB和Mysql都不支持因为是 Result set of type is ResultSet.TYPE_FORWARD_ONLY
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            rs.beforeFirst(); // OB和Mysql都不支持因为是 Result set of type is ResultSet.TYPE_FORWARD_ONLY
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            rs.afterLast(); // OB和Mysql都不支持因为是 Result set of type is ResultSet.TYPE_FORWARD_ONLY
        } catch (SQLException e) {
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
        try{
            rs.setFetchSize(-2147483648);
        } catch (Exception e){
            System.out.println("ERROR3!!!!!!!!!!!!!!!!!");
            System.out.println(e);
        }
        System.out.println(rs.next());
        System.out.println(rs.getObject("Id"));
        System.out.println(rs.getObject("Value0"));
        System.out.println(rs.getObject("Value1"));
        System.out.println(rs.getObject("Value2"));
        try {
            System.out.println("rs.ifFirst(): " + rs.isFirst()); //这里Mysql输出了true
            System.out.println("rs.isLast(): " + rs.isLast()); //这里mysql输出了false
        } catch (Exception e) {
            System.out.println(e + "这两个方法OB报错"); //java.sql.SQLException: Invalid operation on STREAMING ResultSet
        }
        System.out.println("ERROR 1 " + rs.isBeforeFirst()); //这里mysql、OB输出的是false
        System.out.println("ERROR 2 " + rs.isAfterLast()); // 这里mysql、OB输出的是false
        try {
            rs.close(); //这里OB直接执行
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
        stmt.setMaxRows(-2147483648);
        System.out.println(stmt.getFetchSize());
        try {
            stmt.setFetchSize(2147483647);
            int i = 1;
        } catch (Exception e){
            System.out.println("ERROR BIG !!!!!!!!!!!!!!!!!" + e);
            //System.out.println(e);
        }
        rs = stmt.executeQuery("SELECT * FROM table0_0;");
        try{
            rs.setFetchDirection(1001); //Mysql和OB都报错了，OB报错：Invalid operation. Allowed direction are ResultSet.FETCH_FORWARD and ResultSet.FETCH_UNKNOWN
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println(rs.next());
        try{
            rs.previous(); // 这里也是
        } catch (Exception e){
            System.out.println("mysql报错：Operation not allowed for a result set of type ResultSet.TYPE_FORWARD_ONLY.,OB 报错Invalid operation on STREAMING ResultSet。");

        }
        try {
            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println("Mysql 报错Operation not allowed for a result set of type ResultSet.TYPE_FORWARD_ONLY. OB 报错Invalid operation on STREAMING ResultSet");

        }
        try{
            rs.afterLast(); // 这里也是
        } catch (SQLException e) {
            System.out.println(" OB1 !!! Invalid operation on STREAMING ResultSet            mysql报错：Operation not allowed for a result set of type ResultSet.TYPE_FORWARD_ONLY");

        }

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
        try{
            rs.setFetchSize(-2147483648);
        } catch (Exception e){
            System.out.println("ERROR4!!!!!!!!!!!!!!!!!    OB 的 rs.setFetchSize(-2147483648) 报错" + e);
        }
        rs.setFetchSize(2147483647);
        System.out.println(rs.getFetchSize());
        rs.setFetchSize(-2147483648);
        System.out.println(rs.getFetchSize());
        rs.setFetchSize(-2147483648);
        System.out.println(rs.getFetchSize());
        rs.setFetchSize(2147483647);
        System.out.println(rs.getFetchSize());
        rs.setFetchSize(-2147483648);
        System.out.println(rs.getFetchSize());

        System.out.println(rs.next());
        System.out.println(rs.getObject("Id"));
        System.out.println(rs.getObject("Value0"));
        System.out.println(rs.getObject("Value1"));
        System.out.println(rs.getObject("Value2"));
        try {
            System.out.println(rs.isFirst());
        } catch (Exception e){
            System.out.println("rs.isFirst() : OB Invalid operation on STREAMING ResultSet" + e);
           // System.out.println(e);
        }
        try {
            System.out.println(rs.isLast());
        } catch (Exception e){
            System.out.println("rs.isLast() : OB Invalid operation on STREAMING ResultSet" + e);
            //System.out.println(e);
        }
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

//        try {
//            stmt.executeQuery("SELECT * FROM table0_0 WHERE invalid_condition;");
//        } catch (SQLException e) {
//            System.out.println(e);
//        }

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