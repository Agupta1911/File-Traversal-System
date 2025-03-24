import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

// File traversal system that recursively traverses a file system, processes text files, and generates summaries based on specific criteria.
public class FileTraversal {
    private static LinkedQueue<TextFile> queueFiles = new LinkedQueue<>();;
    private static String shortFile = null;
    private static long shortSize = Long.MAX_VALUE;
    private static String largeFile = null;
    private static long largeSize = 0;
    private static long sizeNumber = 0;
    private static int fileNumber = 0;

        public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please give a file directory as an argument.");
            return;
        }
        String direct = args[0];
        File directory = new File (direct);
        if (!directory.isDirectory()){
            System.out.println("Please give a valid file directory as an argument.");
            return;
        }
        boolean flag = false;
        Scanner scanner = new Scanner(System.in);

        while (!flag) {
            System.out.println("Please type 1 to search for a file");
            System.out.println("Please type 2 to display a summary for text files in a folder");
            System.out.println("Please type 3 to exit");
            int input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
                case 1:
                    System.out.print("Please enter the file name to search for: ");
                    String fileName = scanner.nextLine();
                    searchFile(directory, fileName);
                    break;
                case 2:
                    summary(directory);
                    break;
                case 3:
                    flag = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
    //Class for TextFile objects which have the fileName, fileSize and filePath attributes.
    static class TextFile {
        private String fileName;
        private long fileSize;
        private String filePath;

        public TextFile(String fileName, long fileSize, String filePath) {
            this.fileName = fileName;
            this.fileSize = fileSize;
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }

        public String getFileName() {
            return fileName;
        }

        public long getFileSize() {
            return fileSize;
        }

    }

    // Searches for a specific file and writes out full paths of all the matching files to "search_results.txt" in the following format. 
    private static void searchFile(File directory, String fileName) {
        if (!directory.exists()) {
            System.out.println("The directory does not exist.");
            return;
        }

        StringBuilder fileSearch = new StringBuilder();
        fileRecursive(directory, fileName, fileSearch);
        if (fileSearch == null || fileSearch.toString().isEmpty()){
            System.out.println("The file does not exist");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("search_results.txt"))) {
            writer.write(fileSearch.toString());
        } catch (IOException e) {
            System.out.println("There is an error writing to search_results.txt: " + e.getMessage());
        }
    }

    // a recursive file system traversal method that finds all files matching a user-specified name and displays the full paths (absolute path) of all matching files found during the traversal.
    private static void fileRecursive(File directory, String fileName, StringBuilder fileSearch) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    fileRecursive(files[i], fileName, fileSearch);
                } else if (files[i].getName().equals(fileName)) {
                    String filePath = files[i].getAbsolutePath();
                    System.out.println(filePath);
                    fileSearch.append(filePath+"\n");
                }
            }
        }else{
            System.out.println("The directory is empty");
        }
    }

    //Creates a summary for the text files in the file directory, displays it and writes it to results.txt
    private static void summary(File directory) {
        if (!directory.exists()) {
            System.out.println("The directory does not exist.");
            return;
        }

        sizeNumber = 0;
        fileNumber = 0;
        shortFile = null;
        largeFile = null;
        shortSize = Long.MAX_VALUE;
        largeSize = 0;
        queueFiles = new LinkedQueue<>();
        double average;

        textSummary(directory);
        if (fileNumber>0){
            average = (double) sizeNumber / fileNumber;
            System.out.println("Total number of text files found: " + fileNumber);
            System.out.println("Average size of all text files in current folder and all it's subfolders: " + average + " bytes");
            System.out.println("The shortest text file is: " + shortFile + " and it's size is: " + shortSize + " bytes");
            System.out.println("The largest text file is: " + largeFile + " and it's size is: " + largeSize + " bytes");
        }else{
            average = 0;
            shortSize = 0;
            System.out.println("Total number of text files found: " + fileNumber);
            System.out.println("Average size of all text files in current folder and all it's subfolders: " + average + " bytes");
            System.out.println("There is no shortest or largest files as there are no text files");

        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt"))) {
            while (!queueFiles.isEmpty()) {
                TextFile textFile = queueFiles.dequeue();
                writer.write(textFile.getFileName() + ", " + textFile.getFilePath() + ", " + textFile.getFileSize());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("There was an error writing to results.txt: " + e.getMessage());
        }
    }

    //Recursive travel method to focus on text files
    private static void textSummary(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    textSummary(files[i]);
                } else if (files[i].getName().endsWith(".txt")) {
                    textFile(files[i]);
                }
            }
        }
    }

    // Processes each text file encountered during traversal
    private static void textFile(File file) {
        long fileSize = file.length();
        sizeNumber += fileSize;
        fileNumber++;

        // Finds the largest and shortest text files
        if (fileSize < shortSize) {
            shortFile = file.getName();
            shortSize = fileSize;
            
        }
        if (fileSize > largeSize) {
            largeSize = fileSize;
            largeFile = file.getName();
        }

        //Adds TextFile object to the queue
        queueFiles.enqueue(new TextFile(file.getName(), fileSize, file.getAbsolutePath()));
    }
}
