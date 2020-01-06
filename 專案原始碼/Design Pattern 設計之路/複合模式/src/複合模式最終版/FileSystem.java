package 複合模式最終版;

public class FileSystem extends StandardDirectory {
    public FileSystem(String name) {
        super(name);
    }
    @Override
    public String getDisplayType() {
        return "StandardFile System";
    }

}
