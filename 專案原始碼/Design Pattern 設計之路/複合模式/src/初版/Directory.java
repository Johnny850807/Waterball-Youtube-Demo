package 初版;

import 複合模式版.FileSystem;

import java.util.*;

public class Directory {
    private Directory parent;
    private String name;
    private Collection<Directory> directories = new LinkedHashSet<>();
    private Collection<File> files = new LinkedHashSet<>();

    public Directory(String name) {
        this.name = name;
    }

    public boolean containsItem(String name) {
        for (Directory directory : directories) {
            if (name.equals(directory.getName()))
                return true;
        }
        for (File file : files) {
            if (name.equals(file.getName()))
                return true;
        }
        return false;
    }

    public File locateFile(String name) {
        for (File file : getFiles()) {
            if (name.equals(file.getName()))
                return file;
        }
        throw new FileSystemException("The file " + name + " is not found.");
    }

    public Directory locateDirectory(String name) {
        for (Directory directory : getDirectories()) {
            if (name.equals(directory.getName()))
                return directory;
        }
        throw new FileSystemException("The file " + name + " is not found.");
    }

    public List<File> searchFile(String name) {
        List<File> results = new ArrayList<>();

        for (File file : getFiles()) {  //然後要求每一個子檔案或資料夾也搜尋，達成深度優先搜尋
            if (file.getName().contains(name))
                results.add(file);
        }

        for (Directory directory : getDirectories()) {
            results.addAll(directory.searchFile(name));
        }
        return results;
    }

    public List<Directory> searchDirectories(String name) {
        List<Directory> results = new ArrayList<>();

        for (Directory directory : getDirectories()) {  //然後要求每一個子檔案或資料夾也搜尋，達成深度優先搜尋
            if (directory.getName().contains(name)) {
                results.add(directory);
                results.addAll(directory.searchDirectories(name));
            }
        }
        return results;
    }

    public void addChild(Directory directory) {
        if (!containsItem(directory.getName()))
        {
            directories.add(directory);
            directory.setParent(this);
        }
    }

    public void addChild(File file) {
        if (!containsItem(file.getName()))
        {
            files.add(file);
            file.setParent(this);
        }
    }

    public Directory getDirectory(String name) {
        for (Directory directory : getDirectories()) {
            if (name.equals(directory.getName()))
                return directory;
        }
        throw new FileSystemException("The directory " + name + " not found.");
    }

    public Collection<Directory> getDirectories() {
        return directories;
    }

    public Collection<File> getFiles() {
        return files;
    }

    public Directory getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder();

        if (parent == null)
            return "\\";

        Directory tempParent = parent;

        // 一路從目前項目節點走訪到父節點一直到根目錄為止
        while (tempParent != null) {
            stringBuilder.insert(0, getName())
                    .insert(0, "\\");  //附加斜線在開頭
            tempParent = tempParent.getParent();
        }
        return stringBuilder.toString();
    }
}
