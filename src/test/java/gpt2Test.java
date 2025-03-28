import java.sql.*;

import static java.sql.ResultSet.FETCH_UNKNOWN;

public class gpt2Test {
    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Savepoint savepoint = null;

        try {
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb0?user=root&password=1234");
            con = DriverManager.getConnection("jdbc:oceanbase://49.52.27.61:2881/test?user=root@test&password=1234");
            con.setAutoCommit(false); // Disable auto-commit to test transactions

            // Test statement creation with different configurations
            stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
            con.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
            System.out.println("here: " + con.getHoldability());
            stmt.executeUpdate("DROP TABLE IF EXISTS table0_0;");
            stmt.executeUpdate("CREATE TABLE table0_0 (Id DOUBLE PRIMARY KEY, Value0 TEXT(5));");
            // 1. Test commit() and rollback() with multiple transactions

            stmt.executeUpdate("INSERT INTO table0_0 (Id, Value0) VALUES (1, 'Test1')");
            try {
                savepoint = con.setSavepoint("savepoint1");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            stmt.executeUpdate("INSERT INTO table0_0 (Id, Value0) VALUES (2, 'Test2')");
            con.commit(); // Commit the first transaction

            stmt.executeUpdate("INSERT INTO table0_0 (Id, Value0) VALUES (3, 'Test3')");
            try {
                con.rollback(savepoint); // Rollback to savepoint, canceling the last insert
            } catch (SQLException e) {
                System.out.println(e);
            }

            stmt.executeUpdate("INSERT INTO table0_0 (Id, Value0) VALUES (4, 'Test4')");
            con.commit(); // Commit the second transaction

            // 2. Test batch processing and large updates
            stmt.addBatch("INSERT INTO table0_0 (Id, Value0) VALUES (5, 'Test5')");
            stmt.addBatch("INSERT INTO table0_0 (Id, Value0) VALUES (6, 'Test6')");
            stmt.addBatch("INSERT INTO table0_0 (Id, Value0) VALUES (7, 'Test7')");
            stmt.executeBatch(); // Execute the batch insert

            // 3. Test insert with large updates
            pstmt = con.prepareStatement("UPDATE table0_0 SET Value0 = ? WHERE Id = ?");
            pstmt.setString(1, "UpdatedValue");
            pstmt.setDouble(2, 1);
            pstmt.executeLargeUpdate(); // Execute large update for single row

            // 4. Test select queries and result set handling
            rs = stmt.executeQuery("SELECT Id, Value0 FROM table0_0 WHERE Id > 0");
            while (rs.next()) {
                System.out.println("Id: " + rs.getDouble("Id") + ", Value0: " + rs.getString("Value0"));
            }

            // 5. Test ResultSet fetching and navigation
            rs.setFetchSize(5); // Test fetch size for large result sets
            rs.setFetchDirection(ResultSet.FETCH_FORWARD); // Test forward direction
            rs.next(); // Move to next row

            System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            try{
                rs.beforeFirst(); // Move to before first row
                System.out.println("ERROR rs.beforeFirst()!");
            } catch (Exception e){
                System.out.println("mysql抛出异常，OB没有");
            }
            try{
                rs.previous(); // Move to before first row
                System.out.println("rs.previous()!");
                System.out.println(rs.first());
                System.out.println(rs.absolute(2));
                System.out.println(rs.absolute(1));
                System.out.println(rs.last());
            } catch (Exception e){
                System.out.println("OB nb");
            }

            System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            try{
                rs.afterLast(); // Move to after last row
                System.out.println("ERROR rs.afterLast()!");
            } catch (Exception e){
                System.out.println("mysql抛出异常，OB没有");
            }
            // 6. Test batch insert with large dataset
            pstmt = con.prepareStatement("INSERT INTO table0_0 (Id, Value0) VALUES (?, ?)");
            for (int i = 8; i <= 1000; i++) {
                pstmt.setDouble(1, i);
                pstmt.setString(2, "BatchValue" + i);
                pstmt.addBatch(); // Add to batch
            }
            pstmt.executeBatch(); // Execute the entire batch of inserts

            // 7. Test SQL query timeout
            stmt.setQueryTimeout(10); // Set query timeout for 10 seconds
            rs = stmt.executeQuery("SELECT * FROM table0_0 WHERE Id < 100");

            // 8. Test duplicate key insertion (error handling for MySQL)
            try {
                stmt.executeUpdate("INSERT INTO table0_0 (Id, Value0) VALUES (1, 'DuplicateTest')");
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Error: Duplicate key insertion detected.");
            }

            // 10. Test transaction behavior with Savepoints and rollback scenarios
            savepoint = con.setSavepoint("savepoint2");
            stmt.executeUpdate("INSERT INTO table0_0 (Id, Value0) VALUES (999, 'SavepointTest')");
            con.rollback(savepoint); // Rollback to savepoint, canceling this insert
            con.commit(); // Commit the changes made before the savepoint

            // Final ResultSet navigation
            rs.beforeFirst();
            while (rs.next()) {
                System.out.println("Final - Id: " + rs.getDouble("Id") + ", Value0: " + rs.getString("Value0"));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
