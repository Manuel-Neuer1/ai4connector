import compare.OutputFileComparator;

public class OutputFileCompare {

    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDir);
        System.out.println("OutputFileCompare Test!");
        OutputFileComparator.compare();
    }
}
