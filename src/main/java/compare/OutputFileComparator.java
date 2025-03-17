package compare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OutputFileComparator {
    private static int value; // 静态变量
    private static final String BASE_DIR = "ResultComparison";
    private static final String FILE_NAME = "output.txt";
    private static final String COMPARISON_DIR = "/Comparison"; // Comparison目录
    private static final String DIFF_FILE_NAME = "diff_output.txt"; // 输出差异的文件名

    public static boolean compare() {
        Path file1 = Paths.get(BASE_DIR, "connector1", FILE_NAME);
        Path file2 = Paths.get(BASE_DIR, "connector2", FILE_NAME);

        try {
            List<String> content1 = Files.readAllLines(file1);
            List<String> content2 = Files.readAllLines(file2);

            // 如果行数不同，直接返回 false
            if (content1.size() != content2.size()) {
                return false;
            }

            // 创建BufferedWriter来写入不同的行
            Path diffFilePath = Paths.get(BASE_DIR, COMPARISON_DIR, DIFF_FILE_NAME);
            try (BufferedWriter writer = Files.newBufferedWriter(diffFilePath)) {

                // 逐行比对
                for (int i = 0; i < content1.size(); i++) {
                    String line1 = content1.get(i).trim();
                    String line2 = content2.get(i).trim();

                    // 如果当前行是 `java.sql.SQLException`，仅比对当前行是否相同
                    if (line1.contains("java.sql.SQLException") || line2.contains("java.sql.SQLException")) {
                        if (!line1.equals(line2)) {
                            writer.write("ERROR Difference at line " + (i + 1) + ":\n");
                            writer.write("File1: " + line1 + "\n");
                            writer.write("File2: " + line2 + "\n\n\n");
                        }
                    }
                    // 如果当前行是 `java.sql.SQLSyntaxErrorException`
                    else if (line1.contains("java.sql.SQLSyntaxErrorException") || line2.contains("java.sql.SQLSyntaxErrorException")){

                        if (!line1.equals(line2)) {
                            // 如果是因为savepoint不存在导致的错误就直接跳过
                            if (isIgnorableSavepointError(line1, line2)) {
                                continue; // 直接跳过
                            }
                            writer.write("ERROR Difference at line " + (i + 1) + ":\n");
                            writer.write("File1: " + line1 + "\n");
                            writer.write("File2: " + line2 + "\n\n\n");
                        }
                    }
                    else {
                        // 否则，按正常逻辑比较整个文件内容
                        if (!line1.equals(line2)) {
                            writer.write("Difference at line " + (i + 1) + ":\n");
                            writer.write("File1: " + line1 + "\n");
                            writer.write("File2: " + line2 + "\n\n\n");
                        }
                    }
                }
                writer.write("Comparison complete.");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
            return false;
        }
    }

    /**
     * 判断是否是可忽略的 SAVEPOINT 差异
     */
    private static boolean isIgnorableSavepointError(String line1, String line2) {
        System.out.println("Entered isIgnorableSavepointError with:");
        System.out.println("Line1: " + line1);
        System.out.println("Line2: " + line2);

        String regex = "SAVEPOINT [a-fA-F0-9_-]{8,}";
        String normalizedLine1 = line1.trim().replaceAll(regex, "SAVEPOINT <PLACEHOLDER>");
        String normalizedLine2 = line2.trim().replaceAll(regex, "SAVEPOINT <PLACEHOLDER>");

        boolean result = normalizedLine1.equals(normalizedLine2);
        System.out.println("Comparison result: " + result);
        return result;
    }

    public static void setValue(int newValue) {
        value = newValue;
    }

}
