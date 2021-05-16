package ru.job4j.html;

public class Post {
    private String header;
    private String link;
    private String description;
    private String createdDate;

    public Post(String header, String link, String description, String createdDate) {
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
