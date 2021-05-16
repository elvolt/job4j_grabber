package ru.job4j.grabber.utils;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class SqlRuDateTimeParserTest {
    @Test
    public void whenParseShortDateThenParsingCorrect() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "2 май 02, 11:12";
        LocalDateTime expected = LocalDateTime.of(2002, 5, 2, 11, 12);
        LocalDateTime result = parser.parse(date);
        assertEquals(expected, result);
    }

    @Test
    public void whenParseLongDateThenParsingCorrect() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "12 дек 12, 13:14";
        LocalDateTime expected = LocalDateTime.of(2012, 12, 12, 13, 14);
        LocalDateTime result = parser.parse(date);
        assertEquals(expected, result);
    }

    @Test
    public void whenParseTodayThenParsingCorrect() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "сегодня, 12:12";
        LocalDateTime expected = LocalDateTime.of(2021, 5, 16, 12, 12);
        LocalDateTime result = parser.parse(date);
        assertEquals(expected, result);
    }

    @Test
    public void whenParseYesterdayThenParsingCorrect() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "вчера, 12:12";
        LocalDateTime expected = LocalDateTime.of(2021, 5, 15, 12, 12);
        LocalDateTime result = parser.parse(date);
        assertEquals(expected, result);
    }
}