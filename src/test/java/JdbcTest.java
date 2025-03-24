import java.sql.*;

public class JdbcTest {

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        Savepoint sp = null;


        // 获取连接
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test18?user=root&password=1234");
//
//        // 创建 Statement
//        stmt = con.createStatement(1004, 1008, 1);
//        stmt.executeUpdate("DROP TABLE IF EXISTS table18_0;");
//        stmt.executeLargeUpdate("CREATE TABLE table18_0(id VARCHAR(100) PRIMARY KEY, value VARCHAR(100));", 1);
//        stmt.closeOnCompletion();
//
//        stmt = con.createStatement(1005, 1007, 2);
//        stmt.addBatch("DELETE FROM table18_0 WHERE (table18_0.value > '0');");
//        stmt.executeLargeBatch();
//
//        try{
//            con.commit();
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//        sp = con.setSavepoint();
//
//        stmt = con.prepareStatement("INSERT INTO table18_0 VALUES(?, ?);", 1005, 1008, 2);
//        con.setAutoCommit(true);
//        stmt.closeOnCompletion();
//
//        stmt = con.createStatement(1003, 1007, 2);
//        stmt.addBatch("DELETE FROM table18_0 WHERE (table18_0.id <> 'test') AND (table18_0.value = 'test');");
//        stmt.addBatch("UPDATE table18_0 SET value = 'updated' WHERE (table18_0.id != 'test') OR (table18_0.value >= 'test');");
//        stmt.addBatch("DELETE FROM table18_0 WHERE (table18_0.id >= 'test');");
//        stmt.executeLargeBatch();
//
//        stmt = con.prepareStatement("SELECT table18_0.value FROM table18_0 WHERE table18_0.value = ?", 1005, 1007, 1);
//        stmt.setFetchSize(100);
//        stmt.closeOnCompletion();
//
//        con.setAutoCommit(true);
//
//        stmt = con.createStatement(1003, 1007, 1);
//        stmt.getFetchDirection();
//        stmt.setFetchSize(2000000000);
//        stmt.executeLargeUpdate("UPDATE table18_0 SET value = 'updated' WHERE (table18_0.id = 'test');", 1);
//
//        rs = stmt.getGeneratedKeys();
//        rs.next();
//
//        //rs.getHoldability(); //mysql在这里报错
//        rs.updateObject(2, "test"); //ob在这里报错
//        rs.isFirst();
//        rs.cancelRowUpdates();
//
//        con.setTransactionIsolation(2);
//        rs.previous();
//
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.value != 'test');");
//        con.commit();
//        rs.next();
//        stmt.getResultSetConcurrency();
//
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
//        rs.isAfterLast();
//        rs.getType();
//        rs.next();
//
//        rs.updateObject(2, "test");
//        rs.getType();
//        rs.cancelRowUpdates();
//        stmt.getResultSetType();
//
//        rs.updateObject(2, "test");
//        con.commit();
//        con.getHoldability();
//        rs.updateRow();
//        rs.beforeFirst();
//
//        con.setAutoCommit(true);
//        rs.next();
//        rs.setFetchDirection(1000);
//        rs.updateObject(2, "test");
//        stmt.getMoreResults(3);
//        stmt.getUpdateCount();
//        rs.updateRow();
//        rs.afterLast();
//
//        rs.isLast();
//        rs.updateObject(1, "test");
//        con.rollback();
//
//        rs.isAfterLast();
//        rs.cancelRowUpdates();
//        con.getHoldability();
//        stmt.executeLargeUpdate("INSERT INTO table18_0 VALUES('test', 'test')", 2);
//
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
//        rs.next();
//        rs.updateObject(1, "test");
//        rs.updateRow();
//        rs.beforeFirst();
//
//        rs.getHoldability();
//        rs.next();
//        rs.isFirst();
//        con.rollback();
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.id <= 'test') OR (table18_0.id <> 'test');");
//        stmt.getMoreResults(1);
//        rs.isAfterLast();
//        rs.next();
//        rs.updateObject(2, "test");
//        rs.getType();
//        rs.cancelRowUpdates();
//        rs.updateObject(1, "test");
//        stmt.getResultSetConcurrency();
//        rs.updateRow();
//        stmt.executeLargeUpdate("INSERT INTO table18_0 VALUES('test', 'test')", 2);
//        con.getHoldability();
//        rs.beforeFirst();
//        con.setTransactionIsolation(1);
//        stmt.getResultSetConcurrency();
//        rs.next();
//        con.getNetworkTimeout();
//        rs.beforeFirst();
//        con.setAutoCommit(true);
//        con.isReadOnly();
//        rs.next();
//        con.rollback(sp);
//        rs.afterLast();
//        rs.updateObject(1, "test");
//
//        // 关闭资源
//        con.close();
//        stmt.close();
//        rs.close();

        // seed: 1741608171531




// con = DriverManager.getConnection("jdbc:mariadb://localhost:3366/test24?user=root&password=123456&rewriteBatchedStatements=true&dumpQueriesOnException=true");
        //con = DriverManager.getConnection("jdbc:mysql://localhost:3366/test24?user=root&password=123456&rewriteBatchedStatements=true&dumpQueriesOnException=true");
//        stmt = con.createStatement(1003, 1008, 2);
//        System.out.println(stmt.getResultSetHoldability());
//        System.out.println(stmt.executeUpdate("DROP TABLE IF EXISTS table18_0;"));
//        System.out.println(stmt.executeLargeUpdate("CREATE TABLE table18_0(id VARCHAR(100) PRIMARY KEY,value VARCHAR(5));", 2));
//        sp = con.setSavepoint();
//        System.out.println(stmt.getResultSetType());
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.value <= ')XfeUrXwE;!B21');");
//        rs = stmt.getResultSet();
//        System.out.println(rs.next());
//        System.out.println(stmt.getLargeUpdateCount());
//        System.out.println(stmt.getLargeUpdateCount());
//        try {
//            rs.updateObject(1, "'Ejwo.wfX'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println("ERROR:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        try{
//            rs.cancelRowUpdates();
//        }catch (Exception e){
//            System.out.println("mysql error!");
//            System.out.println(e);
//        }
//
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.id = 'Rq4,cM7KOs^A,u0%dGt0@E9EqZI)8VFUkeZIXCoXp)4'-'P)ab2bh4u') OR (table18_0.id != 'wWc#;8g4;IjtMhC5m^@5x7MJywO'-'^0T1m%7&hDTcwj,d4m3uXpFsfMGS0Pj22!TT9!^');");
//        System.out.println(rs.isAfterLast());
//        System.out.println(stmt.executeLargeUpdate("INSERT INTO table18_0 VALUES('4K5R)1hRxv@$ogZU@BmF9W.TX67cjy!,b', 'QlUvr')", 1));
//
//        System.out.println("ERROR:");
//        try {
//            rs.next();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(stmt.getResultSetHoldability());
//        try {
//            rs.updateObject(1, "'I2ywI!%88B)@IchM&M%AJ*3wUDAQZ,eckF6!zXCZ*BJ'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        rs.cancelRowUpdates();
//        System.out.println(con.isReadOnly());
//        try {
//            rs.updateObject(2, "'NPp9*!(T^F7,*X4nZwMckQ1Rf6OO,3sMbm$CHapH#bP'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            rs.updateRow();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(con.isReadOnly());
//        try {
//            rs.updateObject(1, "'^obxjW$(#;ICku'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(stmt.getMoreResults(1));
//        rs.cancelRowUpdates();
//        try {
//            rs.rowInserted();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("ERROR:");
//        try {
//            rs.beforeFirst();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("ERROR:");
//        try {
//            rs.next();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            con.rollback(sp);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("ERROR:");
//        try {
//            rs.beforeFirst();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        sp = con.setSavepoint();
//        System.out.println(con.getNetworkTimeout());
//
//        System.out.println("ERROR:");
//        try {
//            rs.next();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("ERROR:");
//        try {
//            rs.isAfterLast();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            rs.rowUpdated();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            rs.updateObject(2, "'y'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            rs.updateRow();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("ERROR:");
//        try {
//            rs.isLast();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("ERROR:");
//        try {
//            rs.previous();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            rs.updateObject(2, "'(hgacF&Yr(;H1%1UZwbe$)naCq0(6FCRWm^;'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(stmt.getMoreResults(3));
//        System.out.println(stmt.getUpdateCount());
//        rs.cancelRowUpdates();
//        con.setAutoCommit(false);
//        try {
//            rs.updateObject(1, "'2wP2#@VodSqYq,EFmnD#H@ATzTVBUrOVham%8Bf^4%pcj&em$'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(stmt.getUpdateCount());
//        System.out.println(stmt.executeUpdate("DELETE FROM table18_0 WHERE (table18_0.id = '7p*tAXx^VID') AND (table18_0.value = ',5HbF%2;8*3EOfV');", 1));
//        try {
//            rs.updateRow();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(con.getHoldability());
//        try {
//            rs.updateObject(2, "'jt9T83hz9hb^mcMxL4)S@wbd#Oe,Df!P'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        con.commit();
//        try {
//            rs.updateRow();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
//        System.out.println(stmt.getMoreResults(3));
//
//        System.out.println("ERROR:");
//        try {
//            rs.next();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println("ERROR:");
//        try {
//            rs.previous();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        stmt.getResultSet();
//        try {
//            rs.updateObject(1, "'*qqXKIk8DJniyN8SEOxUJYCpY,Jekzh8Jhj^CBCIT)'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(stmt.getResultSetType());
//        rs.cancelRowUpdates();
//        System.out.println(rs.getType());
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
//        System.out.println(stmt.getResultSetHoldability());
//        System.out.println(rs.next());
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
//        con.commit();
//        con.commit();
//        System.out.println(rs.next());
//        System.out.println(stmt.getMoreResults(2));
//
//        System.out.println("ERROR:");
//        try {
//            rs.beforeFirst();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(stmt.getResultSetConcurrency());
//        sp = con.setSavepoint();
//
//        System.out.println("ERROR:");
//        System.out.println(rs.next());
//// con.rollback(org.mariadb.jdbc.Connection$MariaDbSavepoint@336bebc);
//        con.rollback(sp);
//        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.id >= 'DIXu$Ey$NWU2gXg0qPFln');");
//        System.out.println(rs.next());
//        System.out.println(rs.isLast());
//        System.out.println(stmt.executeUpdate("DELETE FROM table18_0 WHERE (table18_0.id != 'C5dnbcIWzmcttXnSnUboXvz2EVckiStEf7OEA') AND (table18_0.value > 'kpTgyYU(RcZ)GOmmVHie');", 2));
//        try {
//            rs.updateObject(2, "'0nV@Mq@gl,Ke&lcDoRLFU1CBF51HQDXi*q1DhQa4S8^FHF9f6'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            rs.updateRow();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(con.getNetworkTimeout());
//        try {
//            rs.updateObject(2, "'4vOw.0#B'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        rs.cancelRowUpdates();
//
//        System.out.println("ERROR:");
//        try {
//            rs.isFirst();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(stmt.getUpdateCount());
//
//        System.out.println("ERROR:");
//        try {
//            rs.beforeFirst();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(stmt.getMoreResults(1));
//
//        System.out.println("ERROR:");
//        try {
//            rs.next();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try {
//            rs.rowUpdated();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(con.getHoldability());
//        try {
//            rs.updateObject(1, "'9Gg9IG3VQ1j8FrMR8mje34p'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(stmt.getResultSetConcurrency());
//
//        System.out.println("ERROR:");
//        try {
//            rs.setFetchSize(1317680093);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        rs.cancelRowUpdates();
//        try {
//            rs.updateObject(1, "'$mH4X3*kaFr.Km5tyIGM'");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        con.setTransactionIsolation(4);
//        System.out.println(stmt.executeLargeUpdate("UPDATE table18_0 SET value = '^*uew.a#&ueZ5m;5VxxknFd77M62YFnroAfHY' WHERE (table18_0.id < 'Uq$VtbVcMtKj5J$%ptpg)d5$dkqs2HRg^9a^4,3nE&');", 1));
//        try {
//            rs.updateRow();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println(con.getHoldability());
//        System.out.println(stmt.getLargeUpdateCount());
//        con.close();
//        stmt.close();
//        stmt.close();
//        stmt.close();
//        rs.close();
//        rs.close();
//        rs.close();
//        rs.close();
//        rs.close();
//        rs.close();
//        rs.close();
//        rs.close();
//        rs.close();

        stmt = con.createStatement(1005, 1007, 2);
        System.out.println(stmt.getQueryTimeout());
        stmt.executeUpdate("DROP TABLE IF EXISTS table18_0;");
        System.out.println(stmt.executeLargeUpdate("CREATE TABLE table18_0(id SMALLINT PRIMARY KEY,value TINYINT);", 1));
        System.out.println(stmt.executeLargeUpdate("UPDATE table18_0 SET value = 446748952 WHERE (table18_0.value > -628126433/-1075983817);", 2));
        System.out.println(stmt.getResultSetType());
        try {
            stmt.getGeneratedKeys();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(stmt.getLargeUpdateCount());

        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.value > -273963601);");
        try {
            con.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.setFetchDirection(1001);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.next());
        System.out.println(con.getAutoCommit());
        //con.setTransactionIsolation(1);
        //System.out.println(con.getAutoCommit());
        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.value > -431675329-1315731580-257152969) OR (table18_0.id != 936751358);");
        try {
            con.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.next());
        System.out.println(con.getHoldability());
        try {
            stmt.executeUpdate("INSERT INTO table18_0 VALUES(-1000758006, 14319940)", 2);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.beforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
// rs = stmt.getResultSet();
        stmt.getResultSet();

        System.out.println("ERROR:");
        try {
            rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.setFetchDirection(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.getObject(2);
        } catch (Exception e) {
            System.out.println(e);
        }
        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.id != -275616731) OR (table18_0.value = -132209959);");
        sp = con.setSavepoint();
        System.out.println(rs.isBeforeFirst());
        System.out.println(rs.next());
        System.out.println(con.getNetworkTimeout());
        System.out.println(stmt.getUpdateCount());
        try {
            rs.updateObject(2, -1073289554);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con.rollback();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(con.isReadOnly());
        try {
            rs.cancelRowUpdates();
        } catch (Exception e) {
            System.out.println(e);
        }
        rs.afterLast();
        rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
        rs = stmt.getResultSet();
        con.setTransactionIsolation(4);
        System.out.println(rs.next());
        System.out.println(stmt.getResultSetConcurrency());
        rs.beforeFirst();
        System.out.println(rs.next());
        System.out.println(con.getNetworkTimeout());
        System.out.println(stmt.getResultSetConcurrency());
        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.id != 795770810*-2070864731*1430613002) OR (table18_0.value = -592844164);");
        System.out.println(stmt.getResultSetConcurrency());
        System.out.println(rs.next());
        rs = stmt.executeQuery("SELECT id, value FROM table18_0 WHERE (table18_0.value <= 2028192250);");
        System.out.println(stmt.getUpdateCount());
        System.out.println(rs.next());
        rs = stmt.getResultSet();
        rs.beforeFirst();
        System.out.println(stmt.executeLargeUpdate("UPDATE table18_0 SET value = 1128998489 WHERE (table18_0.value = -693768693);", 1));
        try {
            con.rollback();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
        System.out.println(rs.next());
        System.out.println(stmt.executeUpdate("UPDATE table18_0 SET value = 217126606 WHERE (table18_0.value <= 1179528556) OR (table18_0.value != 4606564);", 2));
        try {
            rs.updateObject(1, 20334753);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con.rollback(sp);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateRow();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateObject(1, -1130305811);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(stmt.getResultSetType());
        System.out.println(stmt.getResultSetType());
        try {
            rs.updateRow();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.isAfterLast();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.beforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con.rollback();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateObject(1, 1955808245);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.setFetchSize(-1);
        } catch (Exception e) {
            System.out.println(e);
        }
        con.setTransactionIsolation(2);
        try {
            rs.cancelRowUpdates();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.rowUpdated();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            con.rollback();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateObject(1, -766821226);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(stmt.getLargeUpdateCount());
        try {
            rs.cancelRowUpdates();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.rowInserted();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.beforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.isBeforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.setFetchSize(0);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.isBeforeFirst();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("ERROR:");
        try {
            rs.setFetchSize(-2147483648);
        } catch (Exception e) {
            System.out.println(e);
        }
        rs = stmt.executeQuery("SELECT id, value FROM table18_0;");
        System.out.println(rs.isBeforeFirst());
        System.out.println(rs.isFirst());
        System.out.println(rs.next());
        try {
            rs.deleteRow();
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.updateObject(1, -1781266802);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            rs.cancelRowUpdates();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.isLast());
        rs.beforeFirst();
        System.out.println(rs.isFirst());
        System.out.println(rs.next());
        System.out.println(rs.getType());
        rs.beforeFirst();
        System.out.println(rs.next());
        try {
            rs.getObject(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(rs.isLast());
        con.close();
        stmt.close();
        stmt.close();
        stmt.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();
        rs.close();

    }
}
