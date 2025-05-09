Connection con = null;
Statement stmt = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb0?user=root&password=1234&useServerPrepStmts=true&allowMultiQueries=false&dumpQueriesOnException=true&yearIsDateType=true&useBulkStmts=false");
stmt = con.createStatement(1003, 1007, 1);
stmt.executeUpdate("DROP TABLE IF EXISTS table0_2;");
stmt.executeUpdate("CREATE TABLE table0_2 (Id INT PRIMARY KEY, Value0 DECIMAL);");
stmt.setMaxFieldSize(1024);
stmt.setFetchDirection(1001);
stmt.setFetchSize(50);

pstmt = con.prepareStatement("INSERT INTO table0_2 (Id, Value0) VALUES (?, ?)");
pstmt.setObject(1, 1);
pstmt.setObject(2, 123.45);
pstmt.addBatch();
pstmt.setObject(1, 2);
pstmt.setObject(2, -999.99);
pstmt.addBatch();
pstmt.executeBatch();

rs = stmt.executeQuery("SELECT * FROM table0_2 WHERE Id = 1;");
rs.next();
rs.getObject(1);
rs.getObject("Value0");

stmt.setMaxRows(10);
stmt.getMoreResults();
stmt.getUpdateCount();
stmt.getLargeUpdateCount();
stmt.getGeneratedKeys();

stmt.setEscapeProcessing(true);
stmt.setFetchDirection(1002);
stmt.setFetchSize(100);

stmt.executeUpdate("UPDATE table0_2 SET Value0 = 0 WHERE Id = 1;");
stmt.executeUpdate("DELETE FROM table0_2 WHERE Id = 2;");

con.setAutoCommit(false);
Savepoint sp = con.setSavepoint("savepoint1");
stmt.executeUpdate("INSERT INTO table0_2 (Id, Value0) VALUES (3, 456.78);");
con.rollback(sp);
con.commit();

stmt.setFetchSize(Integer.MAX_VALUE);
stmt.setMaxRows(Integer.MIN_VALUE);
stmt.setEscapeProcessing(false);

rs = stmt.executeQuery("SELECT * FROM table0_2;");
rs.next();
rs.previous();
rs.beforeFirst();
rs.afterLast();
rs.isFirst();
rs.isLast();
rs.isAfterLast();
rs.isBeforeFirst();

stmt.setFetchDirection(1003);
stmt.setFetchSize(1);

stmt.executeUpdate("ALTER TABLE table0_2 ADD COLUMN NewColumn VARCHAR(255);");
stmt.executeUpdate("UPDATE table0_2 SET NewColumn = 'Test' WHERE Id = 1;");

pstmt = con.prepareStatement("SELECT * FROM table0_2 WHERE Id = ?");
pstmt.setObject(1, 1);
rs = pstmt.executeQuery();
rs.getObject(1);
rs.getObject("NewColumn");

stmt.setFetchSize(1000000);
stmt.setMaxRows(-1);

stmt.executeUpdate("TRUNCATE TABLE table0_2;");
stmt.executeUpdate("DROP TABLE table0_2;");

stmt.setFetchDirection(1000);
stmt.setFetchSize(10);

stmt.executeUpdate("CREATE TABLE table0_2 (Id INT PRIMARY KEY, Value0 DECIMAL);");
stmt.executeUpdate("INSERT INTO table0_2 (Id, Value0) VALUES (1, 123.45);");

stmt.setFetchDirection(1001);
stmt.setFetchSize(50);

stmt.executeUpdate("UPDATE table0_2 SET Value0 = 0 WHERE Id = 1;");
stmt.executeUpdate("DELETE FROM table0_2 WHERE Id = 1;");

stmt.setFetchSize(1000000);
stmt.setMaxRows(-1);

stmt.executeUpdate("DROP TABLE IF EXISTS table0_2;");
stmt.executeUpdate("CREATE TABLE table0_2 (Id INT PRIMARY KEY, Value0 DECIMAL);");

stmt.setFetchDirection(1002);
stmt.setFetchSize(10);

stmt.executeUpdate("INSERT INTO table0_2 (Id, Value0) VALUES (1, 123.45);");
stmt.executeUpdate("INSERT INTO table0_2 (Id, Value0) VALUES (2, -999.99);");

rs = stmt.executeQuery("SELECT * FROM table0_2;");
rs.next();
rs.getObject(1);
rs.getObject("Value0");

stmt.setFetchDirection(1003);
stmt.setFetchSize(1);

stmt.executeUpdate("UPDATE table0_2 SET Value0 = 0 WHERE Id = 1;");
stmt.executeUpdate("DELETE FROM table0_2 WHERE Id = 2;");

stmt.setFetchSize(Integer.MAX_VALUE);
stmt.setMaxRows(Integer.MIN_VALUE);

stmt.executeUpdate("ALTER TABLE table0_2 ADD COLUMN NewColumn VARCHAR(255);");
stmt.executeUpdate("UPDATE table0_2 SET NewColumn = 'Test' WHERE Id = 1;");

stmt.setFetchDirection(1000);
stmt.setFetchSize(10);

stmt.executeUpdate("TRUNCATE TABLE table0_2;");
stmt.executeUpdate("DROP TABLE table0_2;");

rs.close();
stmt.close();
pstmt.close();
con.close();