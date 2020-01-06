package 複合模式最終版;

import java.util.List;

public interface Directory extends Item {
    boolean contains(String name);

    Item getChild(String name);

    void addChild(Item child);

    List<Item> getChildren();
}
