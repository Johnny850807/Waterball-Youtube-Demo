package 複合模式版;

public interface DictionaryGroup extends Dictionary {
    Dictionary findDictionary(String title);
}
