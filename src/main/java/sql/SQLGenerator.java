package sql;

import Schema.Meta;

import java.sql.*;
import java.util.Random;

public abstract class SQLGenerator {

    protected int SELECT = 1;
    protected int INSERT = 2;
    protected int UPDATE = 3;
    protected int DELETE = 4;
    protected Random r;

    protected String[] supportedValueType;
    protected String[] supportedKeyType;
    protected String[] supportedExpr;
    protected String[] supportedOp;

    protected Meta meta;

    public SQLGenerator(Meta meta) {
        this.meta = meta;
        this.r = new Random();
    }

    public abstract Object generateCreateSql();

    public abstract Object generateUpdateSql();

    public abstract Object generateDeleteSql();

    public abstract Object generateSelectSql();

    protected String getRandomValue(String[] values) {
        int randomIndex = r.nextInt(values.length);
        return values[randomIndex];
    }
}
