package 複合模式版;

import java.util.List;

/**
 * 項目節點
 */
public interface EntryItem {
    /**
     * @return 是否是根目錄
     */
    boolean isRoot();

    /**
     * @return 項目的名稱
     */
    String getName();

    /**
     * @param directory 父節點
     */
    void setParent(Directory directory);

    /**
     * @return 此項目的父節點
     */
    Directory getParent();

    /**
     * @param name 欲找尋的項目名稱
     * @return 是否存在子項目符合此名稱
     */
    boolean containsItem(String name);

    /**
     * @param name 欲找尋的項目名稱
     * @return 找尋到的項目
     */
    EntryItem getChild(String name);

    /**
     * @param name 搜索的檔案名稱
     * @return 所有包該關鍵字之項目
     */
    List<EntryItem> search(String name);

    /**
     * @param child 欲附加的子節點
     */
    void addChild(EntryItem child);

    /**
     * @return 此項目中的所有項目
     */
    List<EntryItem> getChildren();

    /**
     * @return 此項目的型態
     */
    String getDisplayType();

    /**
     * @return 取得此項目的路徑
     */
    String getPath();
}
