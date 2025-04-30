import java.sql.*;

public class test {
    public static void main(String[] args) {

        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=true&rewriteBatchedStatements=true";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=true&rewriteBatchedStatements=true";
        Connection con = null;
        try {
            con = DriverManager.getConnection(url1);

            execute(con, "DROP DATABASE IF EXISTS test");
            execute(con, "CREATE DATABASE test");
            execute(con, "USE test");
            execute(con, "CREATE TABLE t0(c0 DATE UNIQUE PRIMARY KEY NOT NULL) engine=InnoDB");

            batchExecute(con, new String[]{"INSERT INTO t0 VALUES ('2010-10-02')"});

            batchExecute(con, new String[]{"INSERT INTO t0 VALUES ('2024-04-11')", "INSERT INTO t0 VALUES ('2025-04-11')","INSERT INTO t0 VALUES ('2010-10-02')", "INSERT INTO t0 VALUES ('2006-04-01')",
                    "INSERT INTO t0 VALUES ('2019-04-11')", "INSERT INTO t0 VALUES ('2006-04-01')", "INSERT INTO t0 VALUES ('2019-04-11')"});

            executeAndPrint(con, "SELECT * FROM t0");

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

    public static void executeAndPrint(Connection con, String sql) {
        try {
            Statement statement = con.createStatement();
            if (statement.execute(sql)) {
                ResultSet rs = statement.getResultSet();
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int count = rsMetaData.getColumnCount();
                StringBuffer sb = new StringBuffer();

                while (rs.next()) {
                    sb.setLength(0);
                    for (int i = 1; i <= count; i++) {
                        sb.append("* " + rs.getString(i) + " *");
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
