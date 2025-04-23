package mcp;

public class MethodDoc {
    private String description;
    private String[] parameters;
    private String usage;

    public MethodDoc(String description, String[] parameters, String usage) {
        this.description = description;
        this.parameters = parameters;
        this.usage = usage;
    }

    public String getDescription() {
        return description;
    }

    public String[] getParameters() {
        return parameters;
    }

    public String getUsage() {
        return usage;
    }

    @Override
    public String toString() {
        return "MethodDoc{" +
                "description='" + description + '\'' +
                ", parameters=" + String.join(", ", parameters) +
                ", usage='" + usage + '\'' +
                '}';
    }
}