package utils;

import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import drones.DroneDataFetcher;

/**
 * Fetches and processes ID ranges for drones from the API.
 */
public class IdFetcher {
    private static final Logger logger = LoggerFactory.getLogger();
    private static final String ENDPOINT_URL = ConfigLoader.getIdFetcherUrl();
    private static int firstId; // First ID in the range
    private static int lastId; // Last ID in the range

    /**
     * Main method to fetch and process drone data for ID range calculation.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        logger.info("Fetch data of the first drone to get ID range.");
        DroneDataFetcher.fetchAndProcessDroneData(ENDPOINT_URL);
    }

    /**
     * Processes the JSON input to calculate the ID range.
     *
     * @param input The JSON input as a string.
     */
    public static void processId(String input) {
        if (input == null || input.isEmpty()) {
            ErrorLogger.handleJsonException();
            return;
        }

        logger.info("Load the needed objects/arrays from the JSON to calculate the ID range.");
        JSONObject wholeFile = new JSONObject(input);
        JSONArray jsonFile = wholeFile.getJSONArray("results");
        JSONObject objectFile = jsonFile.getJSONObject(0);
        int count = wholeFile.getInt("count");

        if (objectFile.length() == 0) {
            ErrorLogger.handleJsonException();
            return;
        } else {
            logger.info("Calculate IDs.");
            firstId = objectFile.getInt("id");
            lastId = (firstId + count) - 1;
        }
    }

    /**
     * Returns the first ID in the range.
     *
     * @return The first ID.
     */
    public static int getFirstId() {
        return firstId;
    }

    /**
     * Returns the last ID in the range.
     *
     * @return The last ID.
     */
    public static int getLastId() {
        return lastId;
    }
}