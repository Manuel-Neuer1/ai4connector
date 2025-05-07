import java.sql.*;

public class test113326MOpbug {

    public static void main(String[] args) throws SQLException {
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=true";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&rewriteBatchedStatements=true&continueBatchOnError=true";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        con = DriverManager.getConnection(url2);
        stmt = con.createStatement(1003, 1008, 2);
        stmt.executeUpdate("DROP TABLE IF EXISTS table0_0");
        stmt.executeUpdate("CREATE TABLE table0_0(id INT AUTO_INCREMENT PRIMARY KEY,value INT)");
//        stmt.addBatch("DROP TABLE IF EXISTS table0_0");
//        stmt.addBatch("CREATE TABLE table0_0(id INT AUTO_INCREMENT PRIMARY KEY,value INT)");
        stmt.addBatch("INSERT INTO table0_0 VALUES(1, -179653912)");
        stmt.addBatch("INSERT INTO table0_0 VALUES(2, 1207965915)");
        stmt.executeBatch();
        stmt.executeUpdate("INSERT INTO table0_0 (value) VALUES(667711856)", Statement.RETURN_GENERATED_KEYS);
        rs = stmt.getGeneratedKeys();
        System.out.println(stmt.getResultSetType()); // 1003
        System.out.println(rs.getType()); // MYSQL: 1004   OceanBase: 1005
    }
}
