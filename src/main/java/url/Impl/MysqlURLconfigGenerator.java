package url.Impl;

import url.URLconfigGenerator;

import java.util.Random;

public class MysqlURLconfigGenerator extends URLconfigGenerator {


    public MysqlURLconfigGenerator(Random r) {
        super(r);
    }

    @Override
    public Object generateRandomUrlConfig() {
        StringBuilder sb = new StringBuilder();
        for (UrlConfig config : UrlConfig.values()) {
            if (this.r.nextBoolean()) {
                sb.append(getSpecificConfig(r, config));
            }
        }
        return sb.toString();
    }

    /**
     * 随机生成Mysql规范的一个布尔型的URL
     * @return
     */
    @Override
    public Object generateRandomBoolUrlConfig() {
        int op = r.nextInt(3);
        if (op == 0) {
            return "&useServerPrepStmts=";
        } else if (op == 1) {
            return "&allowMultiQueries=";
        } else {
            return "&rewriteBatchedStatements=";
        }
    }


    private static String getSpecificConfig(Random r, UrlConfig config) {
        switch (config) {
            case USE_SERVER_PREP_STMTS: {
                return String.format("&%s=%s", "useServerPrepStmts", r.nextBoolean());
            }
            case ALLOW_MULTI_QUERIES: {
                //return String.format("&%s=%s", "allowMultiQueries", true);
                return String.format("&%s=%s", "allowMultiQueries", r.nextBoolean());
            }
            case REWRITE_BATCHED_STMT:{
                //return String.format("&%s=%s", "rewriteBatchedStatements", true);
                return String.format("&%s=%s", "rewriteBatchedStatements", r.nextBoolean());
            }
            case DUMP_QUERIES_ON_EXCEPTION:{
                //return String.format("&%s=%s", "dumpQueriesOnException", true);
                return String.format("&%s=%s", "dumpQueriesOnException", r.nextBoolean());
            }
            case TINY_INT1_IS_BIT:{
                //return String.format("&%s=%s", "tinyInt1isBit", false);
                return String.format("&%s=%s", "tinyInt1isBit", r.nextBoolean());
            }
            case YEAR_IS_DATE_TYPE:{
                //return String.format("&%s=%s", "yearIsDateType", false);
                return String.format("&%s=%s", "yearIsDateType", r.nextBoolean());
            }
            case CREATE_DATABASE_IF_NOT_EXIST:{
                //return String.format("&%s=%s", "createDatabaseIfNotExist", true);
                return String.format("&%s=%s", "createDatabaseIfNotExist", r.nextBoolean());
            }
            case CACHE_CALLABLE_STMTS:{
                return String.format("&%s=%s", "cacheCallableStmts", r.nextBoolean());
            }
            case USE_BULK_STMTS:{
                return String.format("&%s=%s", "useBulkStmts", r.nextBoolean());
            }
            default:
                return "";
        }
    }

    enum UrlConfig {
        USE_SERVER_PREP_STMTS,
        ALLOW_MULTI_QUERIES,
        REWRITE_BATCHED_STMT,
        DUMP_QUERIES_ON_EXCEPTION,
        TINY_INT1_IS_BIT,
        YEAR_IS_DATE_TYPE,
        CREATE_DATABASE_IF_NOT_EXIST,
        CACHE_CALLABLE_STMTS,
        USE_BULK_STMTS,
    }
}
