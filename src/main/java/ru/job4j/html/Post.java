package ru.job4j.html;

import java.time.LocalDateTime;

public class Post {
    private String header;
    private String link;
    private String description;
    private LocalDateTime createdDate;

    public Post(String header, String link, String description, LocalDateTime createdDate) {
        this.header = header;
        this.link = link;
        this.description = description;
        this.createdDate = createdDate;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Post{"
                + "header='" + header + '\''
                + ", link='" + link + '\''
                + ", description='" + description + '\''
                + ", createdDate=" + createdDate
                + '}';
    }
}
