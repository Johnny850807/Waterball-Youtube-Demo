package 複合模式最終版;

import java.util.List;

public class FileShortcut extends AbstractItem implements File {
    private File linkedFile;

    public FileShortcut(File linkedFile) {
        super(linkedFile.getName());
        this.linkedFile = linkedFile;
    }

    @Override
    public String getContent() {
        return linkedFile.getContent();
    }

    @Override
    public List<Item> search(String name) {
        return linkedFile.search(name);
    }

    @Override
    public String getDisplayType() {
        return "Shortcut => " + linkedFile.getDisplayType();
    }

    public File getLinkedFile() {
        return linkedFile;
    }

    @Override
    public String getPath() {
        return super.getPath() + " ---> " + linkedFile.getPath();
    }
}
