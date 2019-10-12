package 複合模式版;

public class Word implements SearchedItem {
    private String name;

    public Word(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void search(String key, int indentationDegree) {
        if (name.contains(key)) {
            PrintUtils.print("----", indentationDegree);
            System.out.println("(Word) " + name);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
