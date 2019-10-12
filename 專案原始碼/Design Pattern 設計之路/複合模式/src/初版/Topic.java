package 初版;

import java.util.Arrays;
import java.util.List;

public class Topic {
    private String name;
    private List<Word> words;

    public Topic(String name, Word ...words) {
        this.name = name;
        this.words = Arrays.asList(words);
    }

    public String getName() {
        return name;
    }

    public List<Word> getWords() {
        return words;
    }

}
