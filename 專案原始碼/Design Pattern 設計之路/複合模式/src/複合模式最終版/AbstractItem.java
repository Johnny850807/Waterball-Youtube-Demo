package 複合模式最終版;

public abstract class AbstractItem implements Item {
    protected Directory parent;
    protected String name;

    public AbstractItem(String name) {
        this.name = name;
    }

    @Override
    public boolean hasParent() {
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
        Item item = this;
        if (item.hasParent())
            return "\\";
        // 一路從目前項目節點走訪到父節點一直到根目錄為止
        while (!item.hasParent()) {
            stringBuilder.insert(0, item.getName())
                    .insert(0, "\\");  //附加斜線在開頭
            item = item.getParent();
        }
        return stringBuilder.toString();
    }
}
