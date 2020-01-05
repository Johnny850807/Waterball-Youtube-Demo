package 複合模式版;

import java.util.List;

public interface Directory extends EntryItem {
    @Override
    boolean containsItem(String name);

    @Override
    EntryItem getChild(String name);

    @Override
    List<EntryItem> search(String name);

    @Override
    void addChild(EntryItem child);

    @Override
    List<EntryItem> getChildren();
}
