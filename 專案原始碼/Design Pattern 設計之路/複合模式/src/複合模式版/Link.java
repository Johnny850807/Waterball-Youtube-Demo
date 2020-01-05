package 複合模式版;

import java.util.List;

public class Link extends AbstractEntryItem {
    private EntryItem linkedItem;

    public Link(EntryItem linkedItem) {
        super(linkedItem.getName());
        this.linkedItem = linkedItem;
    }

    @Override
    public boolean isRoot() {
        return linkedItem.isRoot();
    }

    @Override
    public Directory getParent() {
        return parent;
    }

    @Override
    public boolean containsItem(String name) {
        return linkedItem.containsItem(name);
    }

    @Override
    public EntryItem getChild(String name) {
        return linkedItem.getChild(name);
    }

    @Override
    public List<EntryItem> search(String name) {
        return linkedItem.search(name);
    }

    @Override
    public void addChild(EntryItem child) {
        linkedItem.addChild(child);
    }

    @Override
    public List<EntryItem> getChildren() {
        return linkedItem.getChildren();
    }

    @Override
    public String getDisplayType() {
        return "Link => " + linkedItem.getDisplayType();
    }

    public EntryItem getLinkedItem() {
        return linkedItem;
    }

    @Override
    public String getPath() {
        return super.getPath() + " ---> " + linkedItem.getPath();
    }
}
