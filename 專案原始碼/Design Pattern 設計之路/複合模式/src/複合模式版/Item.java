package 複合模式版;

import java.util.List;

/**
 * 項目節點
 */
public interface Item {


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
     * @return 是否具有父節點
     */
    boolean hasParent();


    /**
     * @param name 搜索的檔案名稱
     * @return 所有包該關鍵字之項目
     */
    List<Item> search(String name);


    /**
     * @return 此項目的型態
     */
    String getDisplayType();

    /**
     * @return 取得此項目的路徑
     */
    String getPath();

}
