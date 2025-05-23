import java.sql.*;

import static com.oceanbase.jdbc.internal.com.send.authentication.ed25519.Utils.bytesToHex;

public class test115893 {
    public static void main(String[] args) {
        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234";
        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234";
        try (Connection conn = DriverManager.getConnection(url2)) {

            try (Statement stmt = conn.createStatement()) {
                // 清理环境
                stmt.execute("DROP TABLE IF EXISTS t0");
                stmt.execute("DROP TABLE IF EXISTS t1");

                // 创建表并插入数据
                stmt.execute("CREATE TABLE t0 (c0 int)");
                stmt.execute("INSERT INTO t0 VALUES (0)");

                //stmt.execute("SELECT COALESCE(t0.c0, t0.c1)  FROM t0");

                // 直接查询 COALESCE 的结果
                System.out.println("Result of direct SELECT:");
                try (ResultSet rs = stmt.executeQuery("SELECT (COALESCE(t0.c0)) AS c0 FROM t0")) {
                    while (rs.next()) {
                        byte[] result = rs.getBytes("c0");
                        //int result = rs.getInt("c0");
                        //System.out.println(result);
                        System.out.println("c0 = 0x" + bytesToHex(result));


                        System.out.println();
                    }


                }

                // 创建 t1 表保存 COALESCE 结果
                stmt.execute("CREATE TABLE t1 AS (SELECT (COALESCE(t0.c0)) AS c0 FROM t0)");

                // 查询 t1 中的值
                System.out.println("Result from t1:");
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM t1")) {
                    while (rs.next()) {
                        byte result = rs.getByte("c0");
                        //System.out.println("c0 = 0x" + bytesToHex(result));
                        System.out.println(result);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
