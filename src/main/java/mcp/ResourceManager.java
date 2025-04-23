package mcp;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理测试生成所需的资源
 */
public class ResourceManager {

    /**
     * 获取标准JDBC测试所需的资源
     * 
     * @return 包含JDBC测试资源的Map
     */
    public Map<String, Object> getJDBCResources() {
        Map<String, Object> resources = new HashMap<>();

        // 添加通用JDBC资源
        resources.put("jdbc.driver", "com.mysql.cj.jdbc.Driver");
        resources.put("jdbc.user", "root");
        resources.put("jdbc.password", "1234");

        // 添加通用表结构
        if (!resources.containsKey("table.sql")) {
            resources.put("table.sql", "CREATE TABLE IF NOT EXISTS users (\n" +
                    "    id INT PRIMARY KEY,\n" +
                    "    name VARCHAR(100),\n" +
                    "    age INT\n" +
                    ");");
        }

        // 添加常见方法要求
        resources.put("methods.required", "- testConnection(): 测试数据库连接\n" +
                "- testInsert(): 测试插入操作\n" +
                "- testSelect(): 测试查询操作\n" +
                "- testUpdate(): 测试更新操作\n" +
                "- testDelete(): 测试删除操作\n" +
                "- testTransaction(): 测试事务功能");

        // 从prompt中检测并提取有效信息
        detectResourcesFromPrompt(resources);

        return resources;
    }

    /**
     * 从prompt文件中检测并提取有效资源信息
     * 
     * @param resources 要填充的资源Map
     */
    private void detectResourcesFromPrompt(Map<String, Object> resources) {
        // 这个方法将在实际的prompt处理过程中被调用，目前使用默认值
        // 在真实场景中，我们应该从prompt中解析出需要的信息
    }

    /**
     * 获取JDBCResource对象的方法
     * 
     * @param jdbcUrl 数据库连接URL
     * @return JDBCResource对象
     */
    public JDBCResource getJDBCResource(String jdbcUrl) {
        JDBCResource resource = new JDBCResource();

        // 设置JDBC URL
        resource.setJdbcUrl(jdbcUrl);

        // 根据URL确定驱动类
        if (jdbcUrl.contains("mysql")) {
            resource.setDriverClass("com.mysql.cj.jdbc.Driver");
        } else if (jdbcUrl.contains("h2")) {
            resource.setDriverClass("org.h2.Driver");
        } else if (jdbcUrl.contains("oceanbase")) {
            resource.setDriverClass("com.oceanbase.jdbc.Driver");
        } else {
            // 默认使用MySQL驱动
            resource.setDriverClass("com.mysql.cj.jdbc.Driver");
        }

        // 从URL中提取用户名和密码
        extractCredentialsFromUrl(jdbcUrl, resource);

        return resource;
    }

    /**
     * 从JDBC URL中提取用户名和密码
     * 
     * @param jdbcUrl  数据库连接URL
     * @param resource 要填充的JDBCResource对象
     */
    private void extractCredentialsFromUrl(String jdbcUrl, JDBCResource resource) {
        // 尝试从URL中提取用户名和密码
        // MySQL格式: jdbc:mysql://localhost:3306/db?user=root&password=1234
        // OceanBase格式: jdbc:oceanbase://host:port/db?user=root@tenant&password=1234

        try {
            // 提取用户名
            if (jdbcUrl.contains("user=")) {
                int userStart = jdbcUrl.indexOf("user=") + 5;
                int userEnd = jdbcUrl.indexOf("&", userStart);
                if (userEnd == -1) {
                    userEnd = jdbcUrl.length();
                }
                String user = jdbcUrl.substring(userStart, userEnd);
                resource.setUsername(user);
            } else {
                resource.setUsername("root"); // 默认用户名
            }

            // 提取密码
            if (jdbcUrl.contains("password=")) {
                int pwdStart = jdbcUrl.indexOf("password=") + 9;
                int pwdEnd = jdbcUrl.indexOf("&", pwdStart);
                if (pwdEnd == -1) {
                    pwdEnd = jdbcUrl.length();
                }
                String password = jdbcUrl.substring(pwdStart, pwdEnd);
                resource.setPassword(password);
            } else {
                resource.setPassword("1234"); // 默认密码
            }
        } catch (Exception e) {
            // 如果提取失败，使用默认值
            resource.setUsername("root");
            resource.setPassword("1234");
        }
    }
}