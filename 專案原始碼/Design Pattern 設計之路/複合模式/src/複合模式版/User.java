package 複合模式版;

import java.util.ArrayList;
import java.util.List;

public class User {
    private List<SearchedItem> favoriteItems = new ArrayList<>();

    public void addFavorite(SearchedItem searchedItem) {
        favoriteItems.add(searchedItem);
    }

    public void removeFavorite(SearchedItem searchedItem) {
        favoriteItems.remove(searchedItem);
    }

    public void searchWord(String key) {
        for (SearchedItem favoriteItem : favoriteItems) {
            favoriteItem.search(key);
        }
    }
}
