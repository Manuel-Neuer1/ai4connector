package mcp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * 管理提示的加载和处理
 */
public class PromptManager {

    /**
     * 从文件中加载提示
     * 
     * @param promptPath 提示文件路径
     * @return 提示内容
     * @throws PromptLoadException 如果加载过程中出错
     */
    public String loadPrompt(String promptPath) throws PromptLoadException {
        try {
            System.out.println("Loading prompt from: " + promptPath);
            String content = new String(Files.readAllBytes(Paths.get(promptPath)));
            System.out.println("Prompt loaded successfully");
            return content;
        } catch (IOException e) {
            System.err.println("Failed to load prompt from: " + promptPath);
            throw new PromptLoadException("Failed to load prompt: " + e.getMessage(), e);
        }
    }

    /**
     * 根据资源丰富提示内容
     * 
     * @param basePrompt 基础提示
     * @param resources  资源映射
     * @return 丰富后的提示
     */
    public String enrichPrompt(String basePrompt, Map<String, Object> resources) {
        StringBuilder enrichedPrompt = new StringBuilder(basePrompt);

        // 添加JDBC相关信息
        if (resources.containsKey("jdbc.url")) {
            enrichedPrompt.append("\n\n数据库连接URL: ")
                    .append(resources.get("jdbc.url"));
        }

        if (resources.containsKey("jdbc.driver")) {
            enrichedPrompt.append("\n驱动类: ")
                    .append(resources.get("jdbc.driver"));
        }

        // 添加表结构信息
        if (resources.containsKey("table.sql")) {
            enrichedPrompt.append("\n\n表结构SQL:\n")
                    .append(resources.get("table.sql"));
        }

        // 添加方法相关信息
        if (resources.containsKey("methods.required")) {
            enrichedPrompt.append("\n\n需要实现的方法:\n")
                    .append(resources.get("methods.required"));
        }

        enrichedPrompt.append("\n\n生成的代码必须满足以下要求:\n")
                .append("1. 使用JUnit进行测试\n")
                .append("2. 包含完整的数据库连接、查询和更新操作\n")
                .append("3. 使用try-with-resources管理资源\n")
                .append("4. 包含适当的异常处理\n")
                .append("5. 代码格式良好，包含必要的注释");

        System.out.println("Prompt enriched with resources");
        return enrichedPrompt.toString();
    }
}