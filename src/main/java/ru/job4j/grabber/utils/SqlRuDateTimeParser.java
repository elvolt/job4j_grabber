package ru.job4j.grabber.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class SqlRuDateTimeParser implements DateTimeParser {
    private int getMonthNumber(String month) {
        int result;
        switch (month) {
            case "янв":
                result = 1;
                break;
            case "фев":
                result = 2;
                break;
            case "мар":
                result = 3;
                break;
            case "апр":
                result = 4;
                break;
            case "май":
                result = 5;
                break;
            case "июн":
                result = 6;
                break;
            case "июл":
                result = 7;
                break;
            case "авг":
                result = 8;
                break;
            case "сен":
                result = 9;
                break;
            case "окт":
                result = 10;
                break;
            case "ноя":
                result = 11;
                break;
            case "дек":
                result = 12;
                break;
            default:
                throw new IllegalArgumentException("Wrong month format");
        }
        return result;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d M yy");
        String formattedDate = dayMonthYear[0] + " "
                + getMonthNumber(dayMonthYear[1]) + " "
                + dayMonthYear[2];
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
