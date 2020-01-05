package 複合模式版;

import java.util.Collections;
import java.util.List;

public class File extends AbstractEntryItem {
    private String content;

    public File(String name) {
        this(name, "");
    }

    public File(String name, String content) {
        super(name);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean containsItem(String name) {
        return false;  // 檔案並沒有子節點 所以一定不存在
    }

    @Override
    public EntryItem getChild(String name) {
        throw new FileSystemException("Only directory can have children.");
    }

    @Override
    public List<EntryItem> search(String name) {
        return Collections.emptyList();
    }

    @Override
    public void addChild(EntryItem child) {
        throw new FileSystemException("Only directory can have children.");
    }

    @Override
    public List<EntryItem> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String getDisplayType() {
        return "File";
    }
}
