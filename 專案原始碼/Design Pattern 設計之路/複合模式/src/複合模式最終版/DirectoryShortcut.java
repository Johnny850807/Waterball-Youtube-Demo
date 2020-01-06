package 複合模式最終版;

import java.util.List;

public class DirectoryShortcut extends AbstractItem implements Directory {
    private Directory linkedDir;

    public DirectoryShortcut(Directory linkedDir) {
        super(linkedDir.getName());
        this.linkedDir = linkedDir;
    }


    @Override
    public boolean contains(String name) {
        return linkedDir.contains(name);
    }

    @Override
    public Item getChild(String name) {
        return linkedDir.getChild(name);
    }

    @Override
    public List<Item> search(String name) {
        return linkedDir.search(name);
    }

    @Override
    public void addChild(Item child) {
        linkedDir.addChild(child);
    }

    @Override
    public List<Item> getChildren() {
        return linkedDir.getChildren();
    }

    @Override
    public String getDisplayType() {
        return "Shortcut => " + linkedDir.getDisplayType();
    }

    public Directory getLinkedDirectory() {
        return linkedDir;
    }

    @Override
    public String getPath() {
        return super.getPath() + " ---> " + linkedDir.getPath();
    }
}
