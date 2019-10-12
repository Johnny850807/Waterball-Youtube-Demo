package 複合模式版;


import java.util.List;

public interface Dictionary extends Topic {
    List<Topic> getTopics();
}
