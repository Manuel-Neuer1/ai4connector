package Type.Mysql;

import java.util.Random;

public class supportColType {


    static String[] supportedValueType = {"INT", "TINYINT", "BIGINT", "SMALLINT",
            "FLOAT", "DOUBLE", "DECIMAL",
            "CHAR", "VARCHAR(5)", "TEXT(5)", "VARCHAR(100)", "TEXT(100)", "BOOL"};

    static String[] supportedKeyType = {"INT", "TINYINT", "BIGINT", "SMALLINT",
            "FLOAT", "DOUBLE", "DECIMAL",
            "CHAR", "VARCHAR(5)", "VARCHAR(100)", "BOOL"};

    public static String[] getSupportedKeyType() {
        return supportedKeyType;
    }
    public static String[] getSupportedValueType() {
        return supportedValueType;
    }
}
