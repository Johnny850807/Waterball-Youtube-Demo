package 複合模式版;

import java.util.Collections;
import java.util.List;

public class File extends AbstractItem {
    private String content;

    public File(String name) {
        this(name, "");
    }

    public File(String name, String content) {
        super(name);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public List<Item> search(String name) {
        return Collections.emptyList();
    }

    @Override
    public String getDisplayType() {
        return "StandardFile";
    }
}
