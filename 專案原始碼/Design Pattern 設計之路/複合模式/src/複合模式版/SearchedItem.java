package 複合模式版;

public interface SearchedItem {
    String getName();
    void search(String key, int indentationDegree);
    default void search(String key) {
        search(key, 0);
    }
}
