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
}
