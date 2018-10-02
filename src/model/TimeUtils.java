package model;

import javafx.scene.control.DateCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    private static TimeUtils ourInstance = new TimeUtils();

    public static TimeUtils getInstance() {
        return ourInstance;
    }

    private TimeUtils() {}

    public long getTodayMilli() {
        return LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    public long getTomorrowMilli() {
        return LocalDate.now().atStartOfDay(ZoneId.systemDefault()).plus(1,ChronoUnit.DAYS).toEpochSecond();
    }

    public StringConverter<LocalDate> getDateStringConverter() {
        return new StringConverter<>() {
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateTimeFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.trim().equals("")) ? LocalDate.parse(string, dateTimeFormatter) : null;
            }
        };
    }

    public DateCell getDayCellFactory() {
        return new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now().plus(Period.ofDays(1))) < 0);
            }
        };
    }
}
