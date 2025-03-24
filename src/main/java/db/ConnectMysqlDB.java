package db;

import java.sql.*;

public class ConnectMysqlDB {
    private Connection con;
    static int dbId = 0; // 全局自增 ID，每次创建对象递增

    public ConnectMysqlDB(String url) {
        //这个url就是最简单的：jdbc:mysql://localhost:3306/?user=root&password=123456
        try {
            this.con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDatabase(int dbId) {
        //e.g. CREATE DATABASE IF NOT EXISTS test1;
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS testdb" + dbId);
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void closeConnection() {
        try {
            if (this.con != null && !this.con.isClosed()) {
                this.con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDbId() { return dbId; }
}
