package com.example.equi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class SessionDateUtils {

    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private SessionDateUtils() {
    }

    public static String todayIso() {
        return LocalDate.now().toString();
    }

    public static String getSuggestedFakeDate(int index) {
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        return monday.plusDays(Math.floorMod(index, 7)).toString();
    }

    public static LocalDate parseDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException ignored) {
            return null;
        }
    }

    public static LocalTime parseTime(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalTime.parse(value.trim());
        } catch (DateTimeParseException ignored) {
            return null;
        }
    }

    public static LocalDateTime parseDateTime(Session session) {
        LocalDate date = parseDate(session != null ? session.getDate() : null);
        LocalTime time = parseTime(session != null ? session.getHeure() : null);
        if (date == null || time == null) {
            return null;
        }
        return LocalDateTime.of(date, time);
    }

    public static boolean isCompleted(Session session, LocalDateTime now) {
        LocalDateTime sessionDateTime = parseDateTime(session);
        return sessionDateTime != null && !sessionDateTime.isAfter(now);
    }

    public static String formatDateForDisplay(String value) {
        LocalDate date = parseDate(value);
        return date != null ? date.format(DISPLAY_DATE_FORMATTER) : "Date non definie";
    }
}
