import java.sql.Connection;
import java.sql.SQLException;

public class TestDuckDB {
    public static void main(String[] args) {
//        try {
//            // 尝试加载 DuckDB JDBC 驱动
//            Class.forName("org.duckdb.DuckDBDriver");
//            System.out.println("DuckDB Driver Loaded!");
//        } catch (ClassNotFoundException e) {
//            System.err.println("ERROR: Could not load DuckDB JDBC driver.");
//            e.printStackTrace();
//            return;
//        }

        // 连接到 DuckDB 数据库
        String url = "jdbc:duckdb:test18.db";
        try (Connection conn = java.sql.DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to the database successfully.");
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Could not connect to the database.");
            e.printStackTrace();
        }
    }
}
