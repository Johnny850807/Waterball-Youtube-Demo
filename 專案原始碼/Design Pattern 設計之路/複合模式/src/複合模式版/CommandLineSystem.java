package 複合模式版;

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
    private EntryItem currentDir;

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
                        ls(name);
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
                    case "cat":
                        cat(name);
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

    private void ls(String directoryName) {
        if (directoryName == null)
            ls(currentDir);
        else
            ls(currentDir.locate(directoryName));

    }

    private void ls(EntryItem listedItem) {
        if (listedItem instanceof Directory) {
            Directory dir = (Directory) listedItem;
            for (EntryItem item : dir.getChildren())
                System.out.println(item.getName() + " --> " + item.getDisplayType());
        }
        else
            System.err.println("The item " + listedItem.getName() + " of type " + listedItem.getDisplayType() +
                    " does not support the 'ls' command.");
    }

    private void mkdir(String directoryName) {
        if (currentDir.containsItem(directoryName))
            System.err.println("The directory " + directoryName + " has existed.");
        Directory directory = new StandardDirectory(directoryName);
        currentDir.addChild(directory);
    }

    private void touch(String fileName) {
        if (currentDir.containsItem(fileName))
            System.err.println("The file " + fileName + " has existed.");
        File file = new File(fileName);
        currentDir.addChild(file);
    }

    private void mount(FileSystem fileSystem) {
        if (currentDir.containsItem(fileSystem.getName()))
            System.err.println("The file system " + fileSystem.getName() + " has existed.");
        currentDir.addChild(root);
    }

    private void cd(String name) {
        name = name.trim();
        if (ROOT_ALIAS.equals(name))
            currentDir = root;
        else if (GO_BACK_ALIAS.equals(name) && currentDir.getParent() != null)
            currentDir = currentDir.getParent();
        else
            currentDir = currentDir.locate(name);
    }

    private void search(String name) {
        printItemNames(currentDir.search(name));
    }

    private void printItemNames(List<EntryItem> items) {
        for (EntryItem item : items) {
            System.out.println(item.getName());
        }
    }

    private void cat(String fileName) {
        EntryItem item = currentDir.locate(fileName);
        if (item instanceof File)
            System.out.println(((File) item).getContent());
        else
            System.err.println("The item " + fileName + " of type " + item.getDisplayType() +
                    " does not support the 'ls' command.");
    }
}
