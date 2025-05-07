
import java.sql.*;

public class test113336 {
    public static void main(String[] args) throws SQLException {
//        for (int i = 0; i < 1; i++) {

        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=true";
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=true";
        Connection con = null;
        Statement stmt = null;
        con = DriverManager.getConnection(url1);
        stmt = con.createStatement();
        //stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

        stmt.executeUpdate("DROP TABLE IF EXISTS table0_0;");
        stmt.executeUpdate("CREATE TABLE table0_0(id INT PRIMARY KEY,value TINYINT);", 1);

        //stmt = con.createStatement();
        stmt.addBatch("UPDATE table0_0 SET value = 0 WHERE id >= 457264");
        stmt.addBatch("DELETE FROM table0_0 WHERE id <= 4575621");
        stmt.addBatch("DELETE FROM table0_0 WHERE id <= -17754685");
//        stmt.executeUpdate("UPDATE table0_0 SET value = 0 WHERE id >= 457264",Statement.RETURN_GENERATED_KEYS);
//        stmt.executeUpdate("DELETE FROM table0_0 WHERE id <= 4575621",Statement.RETURN_GENERATED_KEYS);
//        stmt.executeUpdate("DELETE FROM table0_0 WHERE id <= -17754685",Statement.RETURN_GENERATED_KEYS);
       stmt.executeBatch();
        //输出主键
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            System.out.println("id: " + rs.getInt(1));
        }
        System.out.println(stmt.getUpdateCount()); // -1 if allowMultiQueries


    }

}
