
import java.sql.*;

public class test113336 {
    public static void main(String[] args) throws SQLException {
//        for (int i = 0; i < 1; i++) {

        String url = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=false";
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=true";
        Connection con = null;
        Statement stmt = null;
        con = DriverManager.getConnection(url1);
        stmt = con.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS table343_0;");
        stmt.executeUpdate("CREATE TABLE table343_0(id INT PRIMARY KEY,value TEXT(5));", 2);

        stmt = con.createStatement();
        stmt.addBatch("UPDATE table343_0 SET value = 'U8cuL9jRqqj1#wT*Dw8o3JuYQL6%$I#lBB1r1V&3x' WHERE id >= 741449035");
        stmt.addBatch("DELETE FROM table343_0 WHERE id <= -1946432626");
        stmt.addBatch("DELETE FROM table343_0 WHERE id <= -1749798027");
        stmt.executeBatch();

        System.out.println(stmt.getUpdateCount()); // -1 if allowMultiQueries


    }

}
