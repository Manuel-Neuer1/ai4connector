package Schema;

import db.ConnectMysqlDB;

import java.util.ArrayList;
import java.util.Random;

import Type.Mysql.supportColType;


public class Meta {
    private int id;
    private String tableName;
    private Table table;
    private ConnectMysqlDB connect;

    private Random r;

    public Meta(ConnectMysqlDB connect, int id){

        r = new Random();
        this.id = id;
        this.connect = connect;
        int dbId = connect.getDbId();
        this.tableName = "table" + dbId + "_" + id;
    }

    public Table initTable(int maxColumnCount) {
        // 构建一个表结构
        // ValueCol 列表
        // 这里再补一个判断maxColumnCount是否大于1的逻辑
        int columnCnt = r.nextInt(1,maxColumnCount);
        ArrayList<Column> col = new ArrayList<>();
        // 为IdCol 列选择类型
        Column idCol = new Column();
        idCol.setColumnName("Id");
        idCol.setColumnType(getRandomValue(supportColType.getSupportedKeyType()));
        idCol.setKey(true);
        idCol.setValue(false);
        col.add(idCol);

        //为ValueCol 列选择类型
        for(int i = 0;i < columnCnt; i++) {
            Column valueCol = new Column("Value" + i,getRandomValue(supportColType.getSupportedValueType()));
            valueCol.setNullable(r.nextBoolean());
            col.add(valueCol);
        }


        Table table = new Table(this.tableName,col);
        this.table = table;
        return table;

    }

//    public boolean isAvailable() {
//        return this.con != null && this.db != null && this.table != null;
//    }

    public boolean hasTable() {
        return this.table != null;
    }

    public Table getTable() {
        return table;
    }

    public Column getRandomTableColumn(Random r) {
        return hasTable() ? table.getRandomColumn(r) : null;
    }


    public String getTableName() { return tableName; }

    public String getRandomValue(String[] values) {
        int randomIndex = r.nextInt(values.length);
        return values[randomIndex];
    }
}
