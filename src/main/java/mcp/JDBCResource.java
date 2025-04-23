package mcp;

/**
 * 表示JDBC资源的类
 */
public class JDBCResource {
    private String name;
    private String[] methods;
    private String description;
    private String jdbcUrl;
    private String driverClass;
    private String username;
    private String password;

    /**
     * 默认构造函数
     */
    public JDBCResource() {
        // 默认构造函数
    }

    /**
     * 带参数的构造函数
     * 
     * @param name        资源名称
     * @param methods     方法数组
     * @param description 资源描述
     */
    public JDBCResource(String name, String[] methods, String description) {
        this.name = name;
        this.methods = methods;
        this.description = description;
    }

    /**
     * 获取资源名称
     * 
     * @return 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     * 
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取方法数组
     * 
     * @return 方法数组
     */
    public String[] getMethods() {
        return methods;
    }

    /**
     * 设置方法数组
     * 
     * @param methods 方法数组
     */
    public void setMethods(String[] methods) {
        this.methods = methods;
    }

    /**
     * 获取资源描述
     * 
     * @return 资源描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置资源描述
     * 
     * @param description 资源描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取JDBC URL
     * 
     * @return JDBC URL
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * 设置JDBC URL
     * 
     * @param jdbcUrl JDBC URL
     */
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * 获取驱动类
     * 
     * @return 驱动类
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * 设置驱动类
     * 
     * @param driverClass 驱动类
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * 获取用户名
     * 
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     * 
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * 
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" - ").append(description).append("\n方法：\n");
        for (String method : methods) {
            sb.append("  - ").append(method).append("\n");
        }
        return sb.toString();
    }
}