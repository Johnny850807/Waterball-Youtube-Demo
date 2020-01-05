package 初版;

import java.util.List;
import java.util.Scanner;

/**
 * 指令介面
 * 又被稱之為 Command Line Interface (CLI)
 */
public class CommandLineSystem {
    private final static String ROOT_ALIAS = "\\";
    private final static String GO_BACK_ALIAS = "..";

    private Directory root;
    private Directory currentDir;

    public CommandLineSystem(Directory root, Directory currentDir) {
        this.root = root;
        this.currentDir = currentDir;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(currentDir.getPath() + "> ");
            String line = scanner.nextLine();
            String[] splits = line.split(" ");
            String command = splits[0].trim();
            String name = splits.length > 1 ? splits[1] : null;

            try {
                // 由於CLI不應負責指令的實作，此處未來可使用 "指令模式 Command Pattern" 來優化
                // (在現今作業系統中就是用了指令模式唷)
                switch (command) {
                    case "ls":
                        ls();
                        break;
                    case "cd":
                        cd(name);
                        break;
                    case "touch":
                        touch(name);
                        break;
                    case "mkdir":
                        mkdir(name);
                        break;
                    case "search":
                        search(name);
                        break;
                    default:
                        System.out.println(command + " command is not found.");
                }
            } catch (FileSystemException err) {
                System.err.println(err.getMessage());
            } catch (NullPointerException err) {
                System.err.println("The argument is required.");
            }
        }
    }

    private void ls() {
        for (Directory directory : currentDir.getDirectories()) {
            System.out.println(directory.getName());
        }
        for (File file : currentDir.getFiles()) {
            System.out.println(file.getName());
        }
    }


    private void mkdir(String directoryName) {
        if (currentDir.containsItem(directoryName))
            System.err.println("The directory " + directoryName + " has existed.");
        Directory directory = new Directory(directoryName);
        currentDir.addChild(directory);
    }

    private void touch(String fileName) {
        if (currentDir.containsItem(fileName))
            System.err.println("The file " + fileName + " has existed.");
        File file = new File(fileName);
        currentDir.addChild(file);
    }


    private void cd(String name) {
        name = name.trim();
        if (ROOT_ALIAS.equals(name))
            currentDir = root;
        else if (GO_BACK_ALIAS.equals(name) && currentDir.getParent() != null)
            currentDir = currentDir.getParent();
        else
            currentDir = currentDir.getDirectory(name);
    }

    private void search(String name) {
        printFileNames(currentDir.searchFile(name));
        printDirectoryNames(currentDir.searchDirectories(name));
    }

    private void printFileNames(List<File> files) {
        for (File file : files) {
            System.out.println(file.getName());
        }
    }

    private void printDirectoryNames(List<Directory> directories) {
        for (Directory directory : directories) {
            System.out.println(directory.getName());
        }
    }

}
