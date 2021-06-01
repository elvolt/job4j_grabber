package ru.job4j.grabber;

import ru.job4j.quartz.Config;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        String sql = "INSERT INTO post (name, text, link, created) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = cnn.prepareStatement(sql)) {
            stm.setString(1, post.getName());
            stm.setString(2, post.getText());
            stm.setString(3, post.getLink());
            stm.setObject(4, post.getCreated());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM post";
        try (Statement stm = cnn.createStatement()) {
            ResultSet resultSet = stm.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String text = resultSet.getString("text");
                String link = resultSet.getString("link");
                LocalDateTime created = resultSet.getObject("created", LocalDateTime.class);
                posts.add(new Post(name, text, link, created));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(String id) {
        Post result = null;
        String sql = "SELECT * FROM post WHERE id = ?";
        try (PreparedStatement stm = cnn.prepareStatement(sql)) {
            stm.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = stm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String text = resultSet.getString("text");
                String link = resultSet.getString("link");
                LocalDateTime created = resultSet.getObject("created", LocalDateTime.class);
                result = new Post(name, text, link, created);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) {
        Properties config = Config.load("rabbit.properties");
        PsqlStore store = new PsqlStore(config);
        Post post1 = new Post("post1", "Text in post 1", "link1", LocalDateTime.now());
        Post post2 = new Post("post2", "Text in post 2", "link2", LocalDateTime.now());
        Post post3 = new Post("post3", "Text in post 3", "link3", LocalDateTime.now());
        store.save(post1);
        store.save(post2);
        store.save(post3);
        store.getAll().forEach(System.out::println);
        System.out.println(store.findById("2").toString());
    }
}