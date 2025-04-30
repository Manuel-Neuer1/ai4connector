import java.sql.*;
import java.util.Arrays;

public class test110586Mbug {
    public static void main(String[] args) throws ClassNotFoundException {
        /*mysql connector 8.0.31 和 mysql connector 9.x.x版本结果不一样*/
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=true";
        /*这里输出的都是*/
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = null;

        try {
            con = DriverManager.getConnection(url2);

            execute(con, "DROP DATABASE IF EXISTS test");
            execute(con, "CREATE DATABASE test");
            execute(con, "USE test");
            execute(con, "CREATE TABLE IF NOT EXISTS t0(c0 INT ZEROFILL)");
            execute(con, "INSERT INTO t0 VALUES(1483647)");

            Statement stmt = con.createStatement();
            if (stmt.execute("SELECT * FROM t0")) {
                ResultSet rs = stmt.getResultSet();
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int count = rsMetaData.getColumnCount();
//                StringBuffer sb = new StringBuffer();

//                while (rs.next()) {
//                    sb.setLength(0);
//                    for (int i = 1; i <= count; i++) {
//                        sb.append("* " + rs.getString(i) + " *");
//                    }
//                    System.out.println();
//                }
                // 遍历每一行
                while (rs.next()) {
                    for (int i = 1; i <= count; i++) {
                        String columnName = rsMetaData.getColumnName(i);
                         // 也可以用 getInt(i), getObject(i), getBytes(i) 等
                        System.out.println(columnName + ": " + Arrays.toString(rs.getBytes(i)) + "   ");
                        System.out.println(columnName + ": " + rs.getString(i) + "   ");
                    }
                    System.out.println();  // 换行
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void execute(Connection con, String sql) {
        try {
            Statement statement = con.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
