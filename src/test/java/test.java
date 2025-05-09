import java.sql.*;

public class test {
    public static void main(String[] args) throws SQLException {

//        String url1 = "jdbc:mysql://localhost:3306/test?user=root&password=1234&allowMultiQueries=true&rewriteBatchedStatements=true";
//        String url2 = "jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=true&rewriteBatchedStatements=true";
//        Connection con = null;
//        try {
//            con = DriverManager.getConnection(url1);
//
//            execute(con, "DROP DATABASE IF EXISTS test");
//            execute(con, "CREATE DATABASE test");
//            execute(con, "USE test");
//            execute(con, "CREATE TABLE t0(c0 DATE UNIQUE PRIMARY KEY NOT NULL) engine=InnoDB");
//
//            batchExecute(con, new String[]{"INSERT INTO t0 VALUES ('2010-10-02')"});
//
//            batchExecute(con, new String[]{"INSERT INTO t0 VALUES ('2024-04-11')", "INSERT INTO t0 VALUES ('2025-04-11')","INSERT INTO t0 VALUES ('2010-10-02')", "INSERT INTO t0 VALUES ('2006-04-01')",
//                    "INSERT INTO t0 VALUES ('2019-04-11')", "INSERT INTO t0 VALUES ('2006-04-01')", "INSERT INTO t0 VALUES ('2019-04-11')"});
//
//            executeAndPrint(con, "SELECT * FROM t0");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        Connection con = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //con = DriverManager.getConnection("jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234&allowMultiQueries=true&rewriteBatchedStatements=true");
        con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb0?user=root&password=1234&useServerPrepStmts=true&rewriteBatchedStatements=true&createDatabaseIfNotExist=false&cacheCallableStmts=true&useBulkStmts=false");
        stmt = con.createStatement(1003, 1007, 1);
        stmt.getQueryTimeout();
        stmt.setMaxRows(100);
        try {
            stmt.setFetchSize(50);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            stmt.setFetchDirection(1001);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS table0_0;");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            stmt.executeUpdate("CREATE TABLE table0_0 (Id DOUBLE PRIMARY KEY, Value0 TINYINT, Value1 DECIMAL);");
        } catch (Exception e) {
            System.out.println(e);
        }

        pstmt = con.prepareStatement("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (?, ?, ?);");
        pstmt.setObject(1, 1.0);
        pstmt.setObject(2, Byte.MAX_VALUE);
        pstmt.setObject(3, Double.MIN_VALUE);
        try {
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }

        stmt.addBatch("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (2.0, -128, 100.5);");
        stmt.addBatch("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (3.0, 127, -200.25);");
        try {
            stmt.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            rs = stmt.executeQuery("SELECT * FROM table0_0;");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.next());
        rs.getObject(1);
        rs.getObject("Value1");
        try {
            System.out.println(rs.previous());
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.beforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.afterLast();
        } catch (Exception e) {
            System.out.println(e);
        }

        stmt.setMaxFieldSize(1024);
        stmt.setEscapeProcessing(true);
        try {
            stmt.setFetchDirection(1002);
        } catch (Exception e) {
            System.out.println(e);
        }

        pstmt.clearParameters();
        pstmt.setObject(1, 4.0);
        pstmt.setObject(2, Byte.MIN_VALUE);
        pstmt.setObject(3, Double.MAX_VALUE);
        pstmt.addBatch();
        try {
            pstmt.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            con.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        Savepoint sp1 = con.setSavepoint("SP1");
        try {
            stmt.executeUpdate("UPDATE table0_0 SET Value0 = 0 WHERE Id = 1.0;");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con.rollback(sp1);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(stmt.getMoreResults());
        System.out.println(stmt.getUpdateCount());
        System.out.println(stmt.getLargeUpdateCount());
        try {
            stmt.getGeneratedKeys();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            rs.setFetchSize(10);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.setFetchDirection(1003);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.isFirst());
        System.out.println(rs.isLast());
        System.out.println(rs.isAfterLast());
        System.out.println(rs.isBeforeFirst());

        stmt.setMaxRows(Integer.MIN_VALUE);
        try {
            stmt.setFetchSize(Integer.MAX_VALUE);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            stmt.setFetchDirection(1004);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            stmt.executeUpdate("DELETE FROM table0_0 WHERE Id = 2.0;");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(stmt.getResultSetConcurrency());
        System.out.println(stmt.getResultSetType());
        System.out.println(stmt.getResultSetHoldability());

        try {
            rs.cancelRowUpdates();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateObject(1, 5.0);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateObject("Value0", Byte.MIN_VALUE);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateRow();
        } catch (Exception e) {
            System.out.println(e);
        }

        stmt.clearBatch();
        stmt.addBatch("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (5.0, 64, 75.5);");
        stmt.addBatch("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (6.0, -64, -75.5);");
        try {
            stmt.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            con.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        Savepoint sp2 = con.setSavepoint("SP2");
        try {
            stmt.executeUpdate("DELETE FROM table0_0 WHERE Id = 3.0;");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con.rollback(sp2);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(rs.getType());
        System.out.println(rs.getHoldability());
        rs.rowDeleted();
        rs.rowInserted();
        rs.rowUpdated();

        stmt.closeOnCompletion();
        stmt.getQueryTimeout();
        System.out.println(stmt.getMaxRows());
        System.out.println(stmt.getMaxFieldSize());
        System.out.println(stmt.getFetchSize());

        stmt.addBatch("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (7.0, 1, 1.1);");
        stmt.addBatch("INSERT INTO table0_0 (Id, Value0, Value1) VALUES (8.0, 0, 0.0);");
        try {
            stmt.executeBatch();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            rs.beforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.afterLast();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.next());
        System.out.println(rs.previous());

        stmt.close();
        pstmt.close();
        rs.close();
        con.close();


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
