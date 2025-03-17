package sql.Impl;

import Schema.Column;
import Schema.Meta;
import Schema.Table;
import sql.SQLGenerator;

import java.util.List;

public class MysqlSQLGenerator extends SQLGenerator {

    static String[] supportedExpr = {"+", "-", "*", "/"};

    static String[] supportedOp = {"=", ">", "<", ">=", "<=", "<>", "!="};

    public MysqlSQLGenerator(Meta meta) {
        super(meta);
    }

    @Override
    public String generateCreateSql() {
        // CREATE TABLE tableName (id idType PRIMARY KEY, value valueType);
        // 先获取表结构
        StringBuilder createSql = new StringBuilder("CREATE TABLE ");
        createSql.append(meta.getTableName()).append(" (");

        // 处理主键列
        Column keyCol = meta.getTable().getKeyCol();
        createSql.append(keyCol.getColumnName()).append(" ")
                .append(keyCol.getColumnType()).append(" PRIMARY KEY");

        // 处理值列
        List<Column> valueCols = meta.getTable().getValueCol();
        for (Column col : valueCols) {
            createSql.append(", ").append(col.getColumnName()).append(" ").append(col.getColumnType());
        }

        createSql.append(");");  // 结束括号
        return createSql.toString();
    }

    @Override
    public Object generateUpdateSql() {
        return null;
    }

    @Override
    public Object generateDeleteSql() {
        return null;
    }

    @Override
    public Object generateSelectSql() {
        return null;
    }
}
