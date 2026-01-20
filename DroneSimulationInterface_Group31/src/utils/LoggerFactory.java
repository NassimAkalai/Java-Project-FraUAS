package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Factory class for creating and configuring a shared logger instance.
 */
public class LoggerFactory {
    private static final Logger logger = Logger.getLogger("SharedLogger");

    static {
        try {
            // Configure the logger to write to a file
            FileHandler fileHandler = new FileHandler("sharedLog.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.FINEST);
        } catch (IOException exceptionVariable) {
            ErrorLogger.handleIOException(exceptionVariable);
        }
    }

    /**
     * Returns the shared logger instance.
     *
     * @return The shared Logger instance.
     */
    public static Logger getLogger() {
        return logger;
    }
}