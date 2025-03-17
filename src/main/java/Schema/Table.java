package Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Table {
    private String tableName;
    private Column keyCol;
    private List<Column> valueCol;

    public String getName() { return tableName; }

    public Column getKeyCol() { return keyCol; }

    public List<Column> getValueCol() { return valueCol; }

    public Column getRandomColumn(Random r) { return valueCol.get(r.nextInt(valueCol.size())); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 表名
        sb.append("Table Name: ").append(tableName).append("\n");
        // 键列
        if (keyCol != null) {
            sb.append("Key Column: \n \t").append(keyCol.getColumnName()).append("\t").append(getKeyCol().getColumnType()).append("\n");
        } else {
            sb.append("Key Column: null\n");
        }
        // 值列
        sb.append("Value Columns:\n");
        if (valueCol != null && !valueCol.isEmpty()) {
            for (Column col : valueCol) {
                sb.append("\t").append(col.getColumnName()).append("\t").append(col.getColumnType()).append("\n");
            }
        } else {
            sb.append("\tNo value columns available.\n");
        }
        return sb.toString();
    }

    public Table(String name, List<Column> cols) {
        this.tableName = name;
        this.valueCol = new ArrayList<>();
        for (Column col : cols) {
            col.setTable(this);
            if (col.isKey()) {
                this.keyCol = col;
            }
            if (col.isValue()) {
                valueCol.add(col);
            }
        }
    }
}
