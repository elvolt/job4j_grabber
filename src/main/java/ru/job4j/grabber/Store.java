package ru.job4j.grabber;

import java.util.List;
import ru.job4j.html.Post;

public interface Store {
    void save(Post post);

    List<Post> getAll();

    Post findById(String id);
}