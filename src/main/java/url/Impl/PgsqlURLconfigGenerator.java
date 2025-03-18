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
            case ALLOW_ENCODING_CHANGES: {
                return String.format("&%s=%s", "allowEncodingChanges", true);
            }
            case BINARY_TRANSFER: {
                return String.format("&%s=%s", "binaryTransfer", r.nextBoolean());
            }
            case CLEANUP_SAVEPOINTS: {
                return String.format("&%s=%s", "cleanupSavepoints", false);
            }
            case REWRITE_BATCHED_INSERTS:{
                return String.format("&%s=%s", "reWriteBatchedInserts", true);
            }
            case ADAPTIVE_FETCH:{
                return String.format("&%s=%s", "adaptiveFetch", true);
            }
            case QUOTE_RETURNING_IDENTIFIERS:{
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
