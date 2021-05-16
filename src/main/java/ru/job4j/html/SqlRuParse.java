package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
    public void parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements rows = doc.select(".forumTable tr");
        for (Element row : rows) {
            Element theme = row.selectFirst(".postslisttopic");
            if (theme == null) {
                continue;
            }
            Element link = theme.selectFirst("a");
            Element dateTime = row.select(".altCol").last();
            System.out.println(link.attr("href"));
            System.out.println(link.text());
            System.out.println(dateTime.text());
        }
    }

    public static void main(String[] args) throws IOException {
        SqlRuParse parser = new SqlRuParse();
        for (int i = 1; i <= 5; i++) {
            parser.parse("https://www.sql.ru/forum/job-offers/" + i);
        }
    }
}
