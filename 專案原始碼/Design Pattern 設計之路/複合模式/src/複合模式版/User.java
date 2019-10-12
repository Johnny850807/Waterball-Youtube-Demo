package 複合模式版;

import java.util.ArrayList;
import java.util.List;

public class User {
    private List<SearchableItem> favoriteItems = new ArrayList<>();

    public void addFavorite(SearchableItem searchableItem) {
        favoriteItems.add(searchableItem);
    }

    public void removeFavorite(SearchableItem searchableItem) {
        favoriteItems.remove(searchableItem);
    }

    public void searchWord(String key) {
        for (SearchableItem favoriteItem : favoriteItems) {
            favoriteItem.search(key);
        }
    }
}
