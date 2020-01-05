package 複合模式版;

public abstract class AbstractEntryItem implements EntryItem {
    protected Directory parent;
    protected String name;

    public AbstractEntryItem(String name) {
        this.name = name;
    }

    @Override
    public boolean isRoot() {
        return parent == null;  //沒有父節點就代表是根目錄
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Directory getParent() {
        return parent;
    }

    @Override
    public void setParent(Directory parent) {
        this.parent = parent;
    }

    @Override
    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder();
        EntryItem entryItem = this;
        if (entryItem.isRoot())
            return "\\";
        // 一路從目前項目節點走訪到父節點一直到根目錄為止
        while (!entryItem.isRoot()) {
            stringBuilder.insert(0, entryItem.getName())
                    .insert(0, "\\");  //附加斜線在開頭
            entryItem = entryItem.getParent();
        }
        return stringBuilder.toString();
    }
}
