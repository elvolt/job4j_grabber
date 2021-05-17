package ru.job4j.grabber.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
    private static final Map<String, Integer> MONTHS = new HashMap<>();
    private static final String DATETIME_PATTERN = "d M yy";

    static {
        MONTHS.put("янв", 1);
        MONTHS.put("фев", 2);
        MONTHS.put("мар", 3);
        MONTHS.put("апр", 4);
        MONTHS.put("май", 5);
        MONTHS.put("июн", 6);
        MONTHS.put("июл", 7);
        MONTHS.put("авг", 8);
        MONTHS.put("сен", 9);
        MONTHS.put("окт", 10);
        MONTHS.put("ноя", 11);
        MONTHS.put("дек", 12);
    }

    private LocalDate parseDate(String date) {
        if (date.equals("сегодня")) {
            return LocalDate.now();
        }
        if (date.equals("вчера")) {
            return LocalDate.now().minusDays(1);
        }
        String[] dayMonthYear  = date.split(" ");
        if (dayMonthYear.length != 3) {
            throw new IllegalArgumentException("Wrong date format");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);
        String day = dayMonthYear[0];
        String year = dayMonthYear[2];
        Integer month = MONTHS.get(dayMonthYear[1]);
        if (month == null) {
            throw new IllegalArgumentException("Wrong month format");
        }
        String formattedDate = String.format("%s %s %s", day, month, year);
        return LocalDate.parse(formattedDate, formatter);
    }

    @Override
    public LocalDateTime parse(String parse) {
        String[] dateTime = parse.split(", ");
        if (dateTime.length != 2) {
            throw new IllegalArgumentException("Wrong date format");
        }
        LocalDate date = parseDate(dateTime[0]);
        LocalTime time = LocalTime.parse(dateTime[1]);
        return LocalDateTime.of(date, time);
    }
}
