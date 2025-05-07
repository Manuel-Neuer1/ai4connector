import java.sql.*;

public class test112213 {
    public static void main(String[] args) throws SQLException
    {
        /*
        * 这里有一个bug，当把allowMultiQueries设置为false时结果为-3 1
        * 但是把allowMultiQueries设置为true时结果为-1 -3
        * 这就很令人费解
        * 理论上来说，allowMultiQueries=true或者false 应该仅仅影响执行批处理时是否能在同一条 Statement 中执行多个查询，并不应该影响单个查询的执行结果。
        * 若两者导致批处理结果有明显不同的行为，这确实可能是 JDBC 驱动的设计问题或实现 bug。
        * */
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=false";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=true";
        Connection con = DriverManager.getConnection(url1);

        Statement stmt = con.createStatement();
        stmt.execute("DROP TABLE IF EXISTS t0");
        stmt.execute("CREATE TABLE t0(c0 REAL SIGNED PRIMARY KEY NOT NULL) engine=InnoDB");
        stmt.execute("INSERT INTO t0 VALUES (1670697762)");

        Statement bstmt = con.createStatement();
        bstmt.addBatch("INSERT INTO t0 VALUES (1670697762)");
        bstmt.addBatch("INSERT INTO t0 VALUES (697762)");
        try {
            bstmt.executeBatch();
        } catch (BatchUpdateException e) {
            int[] res = e.getUpdateCounts();
            for (int r : res) {
                System.out.println(r);
            }
        }
        executeAndPrint(con, "SELECT * FROM t0");
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
}
