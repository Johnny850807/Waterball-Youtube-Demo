package 複合模式版;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StandardDictionary implements Dictionary {
    private String name;
    private List<Topic> topics;

    public StandardDictionary(String name, Topic... topics) {
        this.name = name;
        this.topics = Arrays.asList(topics);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        for (Topic topic : topics)
            words.addAll(topic.getWords());
        return words;
    }

    @Override
    public List<Topic> getTopics() {
        return topics;
    }

    @Override
    public void search(String key, int indentationDegree) {
        PrintUtils.print("----", indentationDegree);
        System.out.println("Search in the dictionary " + name + " ... ");

        if (name.contains(key)) {
            PrintUtils.print("----", indentationDegree);
            System.out.println("(Dictionary) " + name);
        }

        for (Topic topic : topics)
            topic.search(key, indentationDegree+1);
    }
}
