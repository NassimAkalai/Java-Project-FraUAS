package utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Utility class for formatting date and time strings.
 */
public class DateFormatter {
    private static final Logger logger = LoggerFactory.getLogger();

    /**
     * Formats a date string into a custom format.
     *
     * @param date The date string to format.
     * @return The formatted date string.
     */
    public static String main(String date) {
        OffsetDateTime dateTime = OffsetDateTime.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("// dd.MM.yyyy // HH:mm:ss //");
        logger.info("Date and time formatted.");
        return dateTime.format(formatter);
    }
}