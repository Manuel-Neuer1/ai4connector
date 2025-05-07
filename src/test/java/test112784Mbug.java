import java.sql.*;

public class test112784Mbug {
    public static void main(String[] args) throws SQLException {
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=true";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=false";
        Connection con = DriverManager.getConnection(url2);
        Statement stmt = con.createStatement();
        //Statement stmt = con.createStatement(1003, 1007, 1);
        System.out.println(stmt.getResultSetHoldability());
        ResultSet rs = stmt.executeQuery("SELECT 1;");
        System.out.println(rs.getHoldability()); // expect return the same results as stmt.getResultSetHoldability()
    }
}
