package 複合模式最終版;

import java.util.Collections;
import java.util.List;

public class StandardFile extends AbstractItem implements File {
    private String content;

    public StandardFile(String name) {
        this(name, "");
    }

    public StandardFile(String name, String content) {
        super(name);
        this.content = content;
    }

    @Override
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
