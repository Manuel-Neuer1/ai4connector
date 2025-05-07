import java.sql.*;

public class test110432 {

    public static void main(String[] args) throws SQLException {
        String url1 = "jdbc:mysql://localhost:3306?user=root&password=1234&allowMultiQueries=false";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&rewriteBatchedStatements=true&continueBatchOnError=true";
        Connection con = DriverManager.getConnection(url1);

        execute(con, "DROP DATABASE IF EXISTS test");
        execute(con, "CREATE DATABASE test");
        execute(con, "USE test");
        execute(con, "CREATE TABLE t0(c0 DATE UNIQUE PRIMARY KEY NOT NULL) ENGINE=InnoDB");

        batchExecute(con, new String[]{"INSERT INTO t0 VALUES ('2010-10-02')"});

        batchExecute(con, new String[]{
                "INSERT INTO t0 VALUES ('2010-10-02')",  // duplicate
                "INSERT INTO t0 VALUES ('2006-04-01')",
                "INSERT INTO t0 VALUES ('2019-04-11')",
                "INSERT INTO t0 VALUES ('2006-04-01')",  // duplicate
                "INSERT INTO t0 VALUES ('2019-04-11')"   // duplicate
        });

        executeAndPrint(con, "SELECT * FROM t0");

        con.close();
    }

    public static void execute(Connection con, String sql) {
        try (Statement statement = con.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void executeAndPrint(Connection con, String sql) {
        try (Statement statement = con.createStatement()) {
            if (statement.execute(sql)) {
                ResultSet rs = statement.getResultSet();
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int count = rsMetaData.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= count; i++) {
                        System.out.print("* " + rs.getString(i) + " * ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Update count: " + statement.getUpdateCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void batchExecute(Connection con, String[] sql) {
        try (Statement statement = con.createStatement()) {
            for (String s : sql) {
                statement.addBatch(s);
            }
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
//
//public class test110432 {
//
//    public static void main(String[] args) throws SQLException {
//        String url1 = "jdbc:mysql://localhost:3306?user=root&password=1234&rewriteBatchedStatements=true&continueBatchOnError=true";
//        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&rewriteBatchedStatements=true&continueBatchOnError=true";
//        Connection con = DriverManager.getConnection(url2);
//
//        execute(con, "DROP DATABASE IF EXISTS test");
//        execute(con, "CREATE DATABASE test");
//        execute(con, "USE test");
//        execute(con, "CREATE TABLE t0(c0 DATE UNIQUE PRIMARY KEY NOT NULL) ENGINE=InnoDB");
//
//        batchExecuteWithPrepared(con, new String[]{"2010-10-02"});
//        batchExecuteWithPrepared(con, new String[]{
//                "2010-10-02",  // duplicate
//                "2006-04-01",
//                "2019-04-11",
//                "2006-04-01",  // duplicate
//                "2019-04-11"   // duplicate
//        });
//
//        executeAndPrint(con, "SELECT * FROM t0");
//
//        con.close();
//    }
//
//    public static void execute(Connection con, String sql) {
//        try (Statement statement = con.createStatement()) {
//            statement.execute(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void executeAndPrint(Connection con, String sql) {
//        try (Statement statement = con.createStatement()) {
//            if (statement.execute(sql)) {
//                ResultSet rs = statement.getResultSet();
//                while (rs.next()) {
//                    System.out.println("* " + rs.getString(1) + " *");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void batchExecuteWithPrepared(Connection con, String[] values) {
//        String sql = "INSERT INTO t0 VALUES (?)";
//        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
//            for (String value : values) {
//                pstmt.setDate(1, Date.valueOf(value));
//                pstmt.addBatch();
//            }
//            pstmt.executeBatch();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
