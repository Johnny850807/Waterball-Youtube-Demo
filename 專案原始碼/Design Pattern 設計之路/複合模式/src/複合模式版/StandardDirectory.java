package 複合模式版;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StandardDirectory extends AbstractEntryItem implements Directory {
    private LinkedList<EntryItem> entryItems = new LinkedList<>();

    public StandardDirectory(String name) {
        super(name);
    }

    @Override
    public boolean containsItem(String name) {
        for (EntryItem entryItem : entryItems) {
            if (name.equals(entryItem.getName()))
                return true;
        }
        return false;
    }

    @Override
    public EntryItem getChild(String name) {
        for (EntryItem file : getChildren()) {
            if (name.equals(file.getName()))
                return file;
        }
        throw new FileSystemException("The file " + name + " is not found.");
    }

    @Override
    public List<EntryItem> search(String name) {
        List<EntryItem> results = new ArrayList<>();

        for (EntryItem file : getChildren()) {  //然後要求每一個子檔案或資料夾也搜尋，達成深度優先搜尋
            if (file.getName().contains(name))
                results.add(file);
            results.addAll(file.search(name));
        }
        return results;
    }

    @Override
    public void addChild(EntryItem child) {
        if (!containsItem(child.getName()))
        {
            entryItems.add(child);
            child.setParent(this);
        }
    }

    @Override
    public List<EntryItem> getChildren() {
        return entryItems;
    }

    @Override
    public String getDisplayType() {
        return "Directory";
    }


}
