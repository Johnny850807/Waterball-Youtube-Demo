package 初版;

import java.util.ArrayList;
import java.util.List;

public class User {
    private List<Dictionary> favoriteDictionaries = new ArrayList<>();
    private List<Topic> favoriteTopics = new ArrayList<>();
    private List<Word> favoriteWords = new ArrayList<>();

    public void addFavorite(Dictionary dictionary) {
        this.favoriteDictionaries.add(dictionary);
    }

    public void addFavorite(Topic topic) {
        this.favoriteTopics.add(topic);
    }

    public void addFavorite(Word word) {
        this.favoriteWords.add(word);
    }
    
    public void searchWord(String key) {
        for (Dictionary dictionary : favoriteDictionaries) {
            System.out.println("- Search in the dictionary " + dictionary.getName() + " ... ");
            if (dictionary.getName().contains(key))
                System.out.println("- (Dictionary) " + dictionary.getName());

            for (Topic topic : dictionary.getTopics()) {
                System.out.println("---- Search in the topic " + topic.getName() + " ... ");
                if (topic.getName().contains(key))
                    System.out.println("---- (Topic) " + topic.getName());
                for (Word word : topic.getWords()) {
                    if (word.getName().contains(key))
                        System.out.println("-------- (Word) " + word.getName());
                }
            }

        }

        for (Topic topic : favoriteTopics) {
            System.out.println("- Search in the topic " + topic.getName() + " ... ");
            if (topic.getName().contains(key))
                System.out.println("- (Topic) " + topic.getName());
            for (Word word : topic.getWords()) {
                if (word.getName().contains(key))
                    System.out.println("---- (Word) " + word.getName());
            }
        }

        for (Word word : favoriteWords) {
            if (word.getName().contains(key))
                System.out.println("- (Word) " + word.getName());
        }
    }
}
