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
            ls(currentDir.getChild(directoryName));

    }

    private void ls(Item listedItem) {
        if (listedItem instanceof Directory) {
            Directory dir = (Directory) listedItem;
            for (Item item : dir.getChildren())
                System.out.println(item.getName() + " --> " + item.getDisplayType());
        }
        else
            System.err.println("The item " + listedItem.getName() + " of type " + listedItem.getDisplayType() +
                    " does not support the 'ls' command.");
    }

    private void mkdir(String directoryName) {
        if (currentDir.contains(directoryName))
            System.err.println("The directory " + directoryName + " has existed.");
        Directory directory = new Directory(directoryName);
        currentDir.addChild(directory);
    }

    private void touch(String fileName) {
        if (currentDir.contains(fileName))
            System.err.println("The file " + fileName + " has existed.");
        File file = new File(fileName);
        currentDir.addChild(file);
    }

    private void cd(String name) {
        name = name.trim();
        if (ROOT_ALIAS.equals(name))  //回到根目錄
            currentDir = root;
        else if (GO_BACK_ALIAS.equals(name) && currentDir.getParent() != null) //回到上一層
            currentDir = currentDir.getParent();
        else  //既然已經確定我們要的是Directory型態 那就直接強制轉型就好 如果發生錯誤 那就是使用者輸入的命令不對 予以告知
            currentDir = (Directory) currentDir.getChild(name);
    }

    private void search(String name) {
        printItemNames(currentDir.search(name));
    }

    private void printItemNames(List<Item> items) {
        for (Item item : items) {
            System.out.println(item.getName());
        }
    }

    private void cat(String fileName) {
        cat(currentDir.getChild(fileName));
    }

    private void cat(Item item) {
        if (item instanceof File)
            System.out.println(((File) item).getContent());
        else
            System.err.println("The item " + item.getName() + " of type " + item.getDisplayType() +
                    " does not support the 'ls' command.");
    }
}
