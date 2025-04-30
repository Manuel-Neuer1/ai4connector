import java.sql.*;

public class test110323bug {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=true&rewriteBatchStatements=true");
            Statement statement = con.createStatement();
//            statement.execute("DROP DATABASE IF EXISTS test;");
//            statement.execute("CREATE DATABASE test;");
//            statement.execute("USE test;");
            statement.execute("DROP TABLE IF EXISTS t0;");
            statement.execute("CREATE TABLE t0(c0 SMALLINT NOT NULL);");
            statement.addBatch("INSERT INTO t0 VALUES (-1);");
            statement.addBatch("INSERT INTO t0 VALUES (-1);");

            int[] res = statement.executeBatch();
            for (int r : res) {
                System.out.println(r);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
