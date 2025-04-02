import java.io.*;
import java.nio.file.*;

public class TextFileProcessor {
    public static void main(String[] args) {
        for (int i = 4; i <= 23; i++) {

            // 输入文件路径
            String inputFilePath = "LLM/Fine-tuning/Output1/PreparedStatement/execute_query/" + i + ".txt";
            // 输出文件路径
            String outputFilePath = "LLM/Fine-tuning/Output1/PreparedStatement/executeQuery/" + i + ".txt";

            try {
                // 读取文件内容
                String content = Files.readString(Paths.get(inputFilePath));

                // 1. 去除 "catch" 块
                content = content.replaceAll("\\}\\s*catch\\s*\\(Exception\\s+e\\)\\s*\\{\\s*System\\.out\\.println\\(e\\);\\s*\\}", "");

                // 2. 去除 "try {"
                content = content.replaceAll("try\\s*\\{", "");

                // 3. 处理 System.out.println 语句，删除 "System.out.println(" 和 ")"，保留 ";"
                content = content.replaceAll("System\\.out\\.println\\((.*?)\\);", "$1;");

                // 4. 删除包含 "// seed" 的整行
                content = content.replaceAll("(?m)^\\s*//\\s*seed.*(?:\\r?\\n|\\r)", "");

                // 5. 删除包含 "// con =" 的整行
                content = content.replaceAll("(?m)^\\s*//\\s*con\\s*=.*(?:\\r?\\n|\\r)", "");
                // 将处理后的内容写入输出文件
                Files.write(Paths.get(outputFilePath), content.getBytes());

                System.out.println("处理完成，结果已保存到 " + outputFilePath);

            } catch (IOException e) {
                System.err.println("文件处理失败: " + e.getMessage());
            }
        }
    }
}
