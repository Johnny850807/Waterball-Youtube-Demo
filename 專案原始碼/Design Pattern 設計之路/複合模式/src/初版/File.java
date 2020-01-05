package 初版;

import 複合模式版.EntryItem;

public class File {
    private Directory parent;
    private String name;
    private String content;

    public File(String name) {
        this(name, "");
    }

    public File(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Directory getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder();

        if (parent == null)
            return "\\";

        Directory tempParent = parent;

        // 一路從目前項目節點走訪到父節點一直到根目錄為止
        while (tempParent != null) {
            stringBuilder.insert(0, getName())
                    .insert(0, "\\");  //附加斜線在開頭
            tempParent = tempParent.getParent();
        }
        return stringBuilder.toString();
    }
}
