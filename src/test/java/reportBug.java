import java.sql.*;

public class reportBug {
    public static void main(String[] args) throws SQLException {
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=false&rewriteBatchedStatements=false";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=false&rewriteBatchedStatements=true";
        Connection con = DriverManager.getConnection(url1);
        Statement stmt = con.createStatement();
        stmt.execute("DROP TABLE IF EXISTS t;");
        stmt.execute(
                "CREATE TABLE t(" +
                        "  id INT PRIMARY KEY," +
                        "  value1 BIT," +
                        "  value2 BIT" +
                        ");"
        );

        // this method returns empty resultset
        // stmt.execute("INSERT INTO t VALUES(1, false)", 1);
        stmt.addBatch("INSERT INTO t VALUES(1,0,0)");
        stmt.addBatch("INSERT INTO t VALUES(5,1,1)");
        stmt.addBatch("INSERT INTO t VALUES(7,1,1)");
        stmt.addBatch("INSERT INTO t VALUES(2,1,1)");
        stmt.addBatch("INSERT INTO t VALUES(3,1,1)");
        stmt.addBatch("INSERT INTO t VALUES(1,1,1)");
        stmt.addBatch("INSERT INTO t VALUES(101,1,1)");
        stmt.addBatch("INSERT INTO t VALUES(103,1,1)");

        try{
            stmt.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }
        executeAndPrint(con, "SELECT * FROM t");
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
}
