package 複合模式版;


import java.util.List;

public interface Topic extends SearchedItem {
    List<Word> getWords();
}
