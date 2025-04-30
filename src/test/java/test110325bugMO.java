import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class test110325bugMO {
    public static void main(String[] args) throws SQLException {
        /*executeBatch 方法返回-1，不符合JDK的规范*/
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=true";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=true";

        Connection con = null;
        //Statement stmt = null;
        con = DriverManager.getConnection(url2);
        Statement statement = con.createStatement();
        statement.execute("DROP DATABASE IF EXISTS test;");
        statement.execute("CREATE DATABASE test;");
        statement.execute("USE test;");
        statement.execute("CREATE TABLE t0(c0 SMALLINT NOT NULL);");

        //statement.addBatch("REPAIR TABLE t0");
        statement.addBatch("DESC t0");

        int[] res = statement.executeBatch();
        for (int r : res) {
            System.out.println(r);
        }
        statement.close();
    }
}
