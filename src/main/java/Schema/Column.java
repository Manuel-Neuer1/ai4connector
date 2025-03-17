package Schema;

public class Column {

    private String columnName; // 列名
    private String columnType; // 列元素的类型

    private boolean isKey = false;
    private boolean isValue = true;
    private boolean isNullable = false;

    private Table table;


    public Column(){}

    public Column(String columnName, String columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return this.table;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean isKey) {
        this.isKey = isKey;
    }

    public boolean isValue() {
        return isValue;
    }

    public void setValue(boolean isValue) {
        this.isValue = isValue;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

}
