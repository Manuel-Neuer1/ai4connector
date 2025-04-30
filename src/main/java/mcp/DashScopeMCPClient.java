package mcp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

/**
 * 使用DashScope API的MCP客户端实现
 */
public class DashScopeMCPClient {
    private final ResourceManager resourceManager;
    private final PromptManager promptManager;
    private final String outputDir;
    // 从环境变量获取API密钥，与LLMalbb.java使用相同的环境变量名
    private static final String API_KEY = "sk-02b4fc0304604239b4d9fca0fa191385";
    private static final String MODEL_NAME = "qwen-plus-2025-01-25";

    public DashScopeMCPClient(String outputDir) {
        this.resourceManager = new ResourceManager();
        this.promptManager = new PromptManager();
        this.outputDir = outputDir;

        // 验证API密钥是否存在
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new RuntimeException("API key not set. Please set the API key.");
        }
    }

    /**
     * 根据promptPath生成测试用例
     * 
     * @param promptPath
     */
    public void generateTestCases(String promptPath) {
        try {
            // 0.1判断promptPath是否存在
            if (!Files.exists(Paths.get(promptPath))) {
                throw new RuntimeException("Prompt file not found: " + promptPath);
            }
            // 0.2 计算omptPath下有几个文件
            File promptDir = new File(promptPath);
            File[] promptFiles = promptDir.listFiles();
            if (promptFiles == null || promptFiles.length == 0) {
                throw new RuntimeException("No prompt files found in: " + promptPath);
            }
            int promptFileCount = promptFiles.length;
            for (int i = 0; i < promptFileCount; i++) {
                // 1. 加载提示
                String basePrompt = promptManager.loadPrompt(promptFiles[i].getPath());

                // 2. 准备JDBC资源
                Map<String, Object> resources = resourceManager.getJDBCResources();

                // 3. 丰富提示
                //String enrichedPrompt = promptManager.enrichPrompt(basePrompt, resources);

                // 4. 创建输出目录
                Path outputPath = Paths.get(outputDir);
                Files.createDirectories(outputPath);

                // 5. 生成多个测试用例

                String testCase = generateWithDashScope(basePrompt);
                String fileName = String.format("test_%d.java", i);
                Path filePath = outputPath.resolve(fileName);
                Files.write(filePath, testCase.getBytes());
                System.out.println("Generated test case: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error generating test cases: " + e.getMessage());
            throw new RuntimeException("Failed to generate test cases", e);
        }
    }

    private String generateWithDashScope(String prompt) {
        // 构建请求给DashScope
        //String agentPrompt = buildAgentRequest(prompt);

        // 调用DashScope生成测试用例
        String generatedCode = callDashScope(prompt);

//        // 验证生成的代码是否符合要求
//        if (!validateGeneratedCode(generatedCode)) {
//            throw new RuntimeException("Generated code does not meet requirements");
//        }

        return generatedCode;
    }

    private String buildAgentRequest(String prompt) {
        // 构建请求提示
        StringBuilder request = new StringBuilder();
        request.append(prompt);
        request.append("\n\nPlease generate a complete JUnit test class that:\n");
        request.append("1. Follows the code format requirements\n");
        request.append("2. Contains at least 150 lines of effective code\n");
        request.append("3. Tests all specified JDBC operations\n");
        request.append("4. Includes proper error handling and resource management\n");
        return request.toString();
    }

    private String callDashScope(String request) {
        try {
            System.out.println("Calling DashScope API using SDK...");

            // 创建 Generation 实例
            Generation gen = new Generation();

            // 创建系统消息
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("You are a Java testing expert.")
                    .build();

            // 创建用户消息
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(request)
                    .build();

            // 构建生成参数
            GenerationParam param = GenerationParam.builder()
                    .apiKey(API_KEY)
                    .model(MODEL_NAME)
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .temperature(0.2f)
                    .maxTokens(4000)
                    .build();

            System.out.println("Request sent to DashScope API, waiting for response...");

            // 调用生成服务
            GenerationResult result = gen.call(param);
            System.out.println("Received response from API");

            // 从结果中提取文本内容
            String generatedText = result.getOutput().getChoices().get(0).getMessage().getContent();

            // 提取代码部分
            String extractedCode = extractCodeFromResponse(generatedText);
            System.out.println("Successfully extracted code from response");
            return extractedCode;

        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            String errorMsg = "Failed to call DashScope API: " + e.getMessage();
            System.err.println(errorMsg);
            throw new RuntimeException(errorMsg, e);
        }
    }

    private String extractCodeFromResponse(String response) {
        // 使用正则表达式提取代码部分
        Pattern pattern = Pattern.compile("```java\\s*(.*?)\\s*```", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            // 如果没有找到代码块，可能代码没有被```java标记，尝试其他方式提取
            String[] lines = response.split("\n");
            StringBuilder code = new StringBuilder();
            boolean inCodeBlock = false;

            for (String line : lines) {
                if (line.trim().startsWith("public class") || line.trim().startsWith("import ")) {
                    inCodeBlock = true;
                }

                if (inCodeBlock) {
                    code.append(line).append("\n");
                }
            }

            return code.length() > 0 ? code.toString() : response;
        }
    }

    private boolean validateGeneratedCode(String code) {
        // 验证生成的代码是否符合要求
        return code != null && !code.isEmpty() &&
                (code.contains("import java.sql") ||
                        code.contains("import java.sql.*"))
                &&
                (code.contains("import org.junit.jupiter.api.Test") ||
                        code.contains("import org.junit.Test"));
    }
}