import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class rewriteFile {
    // 定义可能抛出异常的JDBC方法
    private static final List<String> EXCEPTION_METHODS = new ArrayList<>();

    // 定义需要添加System.out.println()的方法
    private static final List<String> PRINT_METHODS = new ArrayList<>();

    static {
        EXCEPTION_METHODS.add("commit");
        EXCEPTION_METHODS.add("rollback");
        EXCEPTION_METHODS.add("setAutoCommit");
        EXCEPTION_METHODS.add("setTransactionIsolation");
        EXCEPTION_METHODS.add("setReadOnly");
        EXCEPTION_METHODS.add("getGeneratedKeys");
        // rs
        EXCEPTION_METHODS.add("cancelRowUpdates");
        EXCEPTION_METHODS.add("updateObject");
        EXCEPTION_METHODS.add("updateRow");

        PRINT_METHODS.add("isReadOnly");
        PRINT_METHODS.add("getHoldability");
        PRINT_METHODS.add("getNetworkTimeout");
        PRINT_METHODS.add("executeUpdate");
        PRINT_METHODS.add("executeLargeUpdate");
        PRINT_METHODS.add("getUpdateCount");
        PRINT_METHODS.add("getMoreResults");
        PRINT_METHODS.add("getLargeUpdateCount");
        PRINT_METHODS.add("getMaxFieldSize");
        PRINT_METHODS.add("getFetchDirection");
        PRINT_METHODS.add("getFetchSize");
        PRINT_METHODS.add("getResultSetConcurrency");
        PRINT_METHODS.add("getResultSetType");
        PRINT_METHODS.add("getResultSetHoldability");
        PRINT_METHODS.add("getMaxRows");
        // rs
        // PRINT_METHODS.add("next");
        PRINT_METHODS.add("previous");
        PRINT_METHODS.add("isFirst");
        PRINT_METHODS.add("isLast");
        PRINT_METHODS.add("isBeforeFirst");
        PRINT_METHODS.add("isAfterLast");
        PRINT_METHODS.add("getType");
        PRINT_METHODS.add("getHoldability");
    }

    public void rewriteFile() {
    };

    /**
     * 重写JDBC代码，将可能抛出异常的方法包裹在try-catch块中
     *
     * @param inputFilePath  输入文件路径
     * @param outputFilePath 输出文件路径
     * @throws IOException 如果读写文件时发生错误
     */
    public rewriteFile(String inputFilePath, String outputFilePath) throws IOException {
        // 读取输入文件
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // 改写内容
        List<String> rewrittenLines = new ArrayList<>();
        for (String line : lines) {
            String trimmedLine = line.trim();
            // 检查是否包含JDBC方法调用
            boolean isExceptionMethod = false;
            boolean isPrintMethod = false;
            for (String method : EXCEPTION_METHODS) {
                if (trimmedLine.contains(method)) {
                    isExceptionMethod = true;
                    break;
                }
            }
            // 检查是否包含需要添加System.out.println()的方法调用
            for (String method : PRINT_METHODS) {
                if (trimmedLine.contains(method)) {
                    isPrintMethod = true;
                    break;
                }
            }
            if (isExceptionMethod) {
                // 构造try-catch块
                String tryBlockStart = "try {";
                String catchBlock = "} catch (Exception e) {";
                String exceptionHandler = "System.out.println(e);";
                String catchBlockEnd = "}";
                // 将方法调用包裹在try-catch块中
                String rewrittenLine = tryBlockStart + "\n\t" + line + "\n" + catchBlock + "\n\t" + exceptionHandler
                        + "\n" + catchBlockEnd;
                rewrittenLines.add(rewrittenLine);
            } else {
                if (isPrintMethod) {
                    // 去掉最后的 ; 号（如果有的话）
                    if (trimmedLine.endsWith(";")) {
                        trimmedLine = trimmedLine.substring(0, trimmedLine.length() - 1);
                    }
                    String printStatement = "System.out.println(" + trimmedLine + ");";
                    rewrittenLines.add(printStatement);
                } else {
                    // 如果不包含特殊处理的方法调用，直接添加原行
                    rewrittenLines.add(line);
                }
            }
        }
        // 写入输出文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String line : rewrittenLines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

}
