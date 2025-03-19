package url.Impl;

import url.URLconfigGenerator;

import java.util.Random;



public class PgsqlURLconfigGenerator extends URLconfigGenerator {
    public PgsqlURLconfigGenerator(Random r) {
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

    @Override
    public Object generateRandomBoolUrlConfig() {
        return null;
    }

    private static String getSpecificConfig(Random r, UrlConfig config) {
        switch (config) {
            case ALLOW_ENCODING_CHANGES: { //是否允许数据库连接在运行时更改字符编码
                return String.format("&%s=%s", "allowEncodingChanges", true);
            }
            case BINARY_TRANSFER: { //是否启用二进制传输模式（用于提高性能）
                return String.format("&%s=%s", "binaryTransfer", r.nextBoolean());
            }
            case CLEANUP_SAVEPOINTS: { //是否在事务回滚后自动清理 savepoint（一般数据库默认 false）
                return String.format("&%s=%s", "cleanupSavepoints", false);
            }
            case REWRITE_BATCHED_INSERTS:{ //是否让 INSERT 语句批量执行，提高效率
                return String.format("&%s=%s", "reWriteBatchedInserts", true);
            }
            case ADAPTIVE_FETCH:{ //是否自适应数据获取，用于优化查询结果的获取方式，可能会根据数据量调整 fetchSize
                return String.format("&%s=%s", "adaptiveFetch", true);
            }
            case QUOTE_RETURNING_IDENTIFIERS:{ //是否对 RETURNING 语句的标识符加引号
                return String.format("&%s=%s", "quoteReturningIdentifiers", false);
            }
            default:
                return "";
        }
    }

    enum UrlConfig {
        ALLOW_ENCODING_CHANGES,
        BINARY_TRANSFER,
        CLEANUP_SAVEPOINTS,
        REWRITE_BATCHED_INSERTS,
        ADAPTIVE_FETCH,
        QUOTE_RETURNING_IDENTIFIERS,
    }
}
