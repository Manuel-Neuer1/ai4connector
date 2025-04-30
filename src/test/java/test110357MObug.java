import java.sql.*;

public class test110357MObug {
    public static void main(String[] args) throws SQLException {
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=true";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=false";

        Connection con1 = DriverManager.getConnection(url1);

        execute(con1, "DROP DATABASE IF EXISTS test");
        execute(con1, "CREATE DATABASE test");
        execute(con1, "USE test");
        execute(con1, "CREATE TABLE t0(c0 REAL SIGNED PRIMARY KEY NOT NULL) engine=InnoDB");
        execute(con1, "INSERT INTO t0 VALUES (1670697762)");
        batchExecute(con1, new String[]{
                "INSERT INTO t0 VALUES (1670697762);",
                "INSERT INTO t0 VALUES (86274641)"
        });
        executeAndPrint(con1, "SELECT * FROM t0");

        con1.close();
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

    public static void executeAndPrint(Connection con, String sql) {
        try {
            Statement statement = con.createStatement();
            if (statement.execute(sql)) {
                ResultSet rs = statement.getResultSet();
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int count = rsMetaData.getColumnCount();
                StringBuilder sb = new StringBuilder();

                while (rs.next()) {
                    sb.setLength(0);
                    for (int i = 1; i <= count; i++) {
                        sb.append("* ").append(rs.getString(i)).append(" *");
                    }
                    System.out.println(sb);
                }
            } else {
                System.out.println("count: " + statement.getUpdateCount());
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void batchExecute(Connection con, String[] sql) {
        try {
            Statement statement = con.createStatement();
            for (String s : sql) {
                statement.addBatch(s);
            }
            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

