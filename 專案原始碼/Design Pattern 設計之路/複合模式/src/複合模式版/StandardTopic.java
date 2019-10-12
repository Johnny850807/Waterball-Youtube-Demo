package 複合模式版;

import java.util.Arrays;
import java.util.List;

public class StandardTopic implements Topic {
    private String name;
    private List<Word> words;

    public StandardTopic(String name, Word ...words) {
        this.name = name;
        this.words = Arrays.asList(words);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Word> getWords() {
        return words;
    }

    @Override
    public void search(String key, int indentationDegree) {
        PrintUtils.print("----", indentationDegree);
        System.out.println("Search in topic " + name + " ... ");

        if (name.contains(key)) {
            PrintUtils.print("----", indentationDegree);
            System.out.println("(Topic) " + name);
        }

        for (Word word : words)
            word.search(key, indentationDegree+1);
    }
}
