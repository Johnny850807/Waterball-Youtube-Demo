package 複合模式版;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StandardDictionaryGroup implements DictionaryGroup {
    private List<Dictionary> dictionaries;
    private String name;

    public StandardDictionaryGroup(String name, Dictionary ...dictionaries) {
        this.name = name;
        this.dictionaries = Arrays.asList(dictionaries);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Dictionary findDictionary(String title) {
        for (Dictionary dictionary : dictionaries) {
            if (dictionary.getName().equals(title))
                return dictionary;
        }
        throw new RuntimeException("The dictionary " + title + " is not found.");
    }

    @Override
    public List<Topic> getTopics() {
        List<Topic> topics = new ArrayList<>();
        for (Dictionary dictionary : dictionaries) {
            topics.addAll(dictionary.getTopics());
        }
        return topics;
    }

    @Override
    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        for (Dictionary dictionary : dictionaries) {
            words.addAll(dictionary.getWords());
        }
        return words;
    }

    @Override
    public void search(String key, int indentationDegree) {
        PrintUtils.print("----", indentationDegree);
        System.out.println("Search in the dictionary-group " + name + " ... ");

        if (name.contains(key)) {
            PrintUtils.print("----", indentationDegree);
            System.out.println("(Dictionary-Group) " + name);
        }

        for (Dictionary dictionary : dictionaries)
            dictionary.search(key, indentationDegree+1);
    }
}
