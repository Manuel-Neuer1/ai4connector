package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    /**
     * 从 prompt 文件路径中提取数字，例如 prompt123.txt → 123
     * @param promptPath 文件路径
     * @return 数字字符串（如 "123"），如果未匹配则返回 null
     */
    public static String extractPromptNumber(String promptPath) {
        Pattern pattern = Pattern.compile("prompt(\\d+)\\.txt");
        Matcher matcher = pattern.matcher(promptPath);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
