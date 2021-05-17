package ru.job4j.grabber.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        LocalDateTime expected = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 12));
        LocalDateTime result = parser.parse(date);
        assertEquals(expected, result);
    }

    @Test
    public void whenParseYesterdayThenParsingCorrect() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "вчера, 12:12";
        LocalDateTime expected = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(12, 12));
        LocalDateTime result = parser.parse(date);
        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrongMonthFormatThenThrowException() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "14 мая 21, 11:13";
        parser.parse(date);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenWrongDateFormatThenThrowException() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "12 мая 2021, 11:13";
        parser.parse(date);
    }
}