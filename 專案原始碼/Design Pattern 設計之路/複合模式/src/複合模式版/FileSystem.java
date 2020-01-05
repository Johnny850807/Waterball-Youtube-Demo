package 複合模式版;

public class FileSystem extends StandardDirectory {
    public FileSystem(String name) {
        super(name);
    }
    @Override
    public String getDisplayType() {
        return "File System";
    }

}
