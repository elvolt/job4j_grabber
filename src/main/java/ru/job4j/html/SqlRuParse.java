package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlRuParse implements Parse {
    private static final String DATETIME_REGEXP = "\\d{1,2} [а-я]{3} \\d{2}, \\d{2}:\\d{2}";

    public List<Post> list(String url) {
        List<Post> posts = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements rows = doc.select(".forumTable tr");
            for (Element row : rows) {
                Element theme = row.selectFirst(".postslisttopic");
                if (theme == null) {
                    continue;
                }
                Element link = theme.selectFirst("a");
                Post post = detail(link.attr("href"));
                posts.add(post);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post detail(String url) {
        Post post = null;
        try {
            Document doc = Jsoup.connect(url).get();
            String header = doc.selectFirst(".msgTable .messageHeader").ownText();
            String description = doc.select(".msgTable .msgBody").get(1).text();
            Pattern pattern = Pattern.compile(DATETIME_REGEXP);
            Matcher matcher = pattern.matcher(doc.selectFirst(".msgTable .msgFooter").ownText());
            LocalDateTime createdDate = null;
            if (matcher.find()) {
                createdDate = new SqlRuDateTimeParser().parse(matcher.group());
            }
            post = new Post(header, url, description, createdDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static void main(String[] args) {
        SqlRuParse parser = new SqlRuParse();
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            posts.addAll(parser.list("https://www.sql.ru/forum/job-offers/" + i));
        }
        posts.forEach(System.out::println);
    }
}
