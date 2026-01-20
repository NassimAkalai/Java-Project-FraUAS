package utils;

import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Utility class for formatting URLs based on drone IDs and offsets.
 */
public class LinkFormatter {
    private static final Logger logger = LoggerFactory.getLogger();
    public static final String DRONE_DYNAMICS_URL = ConfigLoader.getDroneDynamicsUrl();
    public static final String DEFAULT_URL = ConfigLoader.getDefaultUrl();
    public static String newLink; // Processed URL

    /**
     * Processes the drone ID to generate a new URL.
     *
     * @param droneId The ID of the drone.
     */
    public static void processDrone(int droneId) {
        if (droneId == 0) {
            logger.info("Set new URL to the default drone dynamics URL to get newest information.");
            newLink = DEFAULT_URL;
        } else {
            logger.info("Adjust the new URL to the corresponding ID from user input.");
            newLink = DRONE_DYNAMICS_URL.replace("{id}", String.valueOf(droneId));
        }
    }

    /**
     * Returns the processed URL.
     *
     * @return The processed URL.
     * @throws IllegalStateException If the URL has not been initialized.
     */
    public static String getProcessedLink() {
        if (newLink == null) {
            JOptionPane.showMessageDialog(null, "Error: newLink has not been initialized. Call processDrone() first.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalStateException("Error: newLink has not been initialized. Call processDrone() first.");
        }
        return newLink;
    }

    /**
     * Calculates the offset URL for pagination.
     *
     * @param offset The offset value.
     * @return The URL with the offset applied.
     * @throws IOException If the URL is not properly initialized.
     */
    public static String offsetCalculator(int offset) throws IOException {
        if (newLink == null || !newLink.contains("%d")) {
            JOptionPane.showMessageDialog(null, "The URL has not been initialized properly.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IOException("The URL has not been initialized properly.");
        } else {
            return newLink.replace("%d", String.valueOf(offset));
        }
    }
}