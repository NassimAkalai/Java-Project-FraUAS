package drones;

import org.json.JSONObject;
import utils.ConfigLoader;
import utils.ErrorLogger;
import utils.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Abstract class for fetching and processing drone details from a remote API.
 * This class provides a base implementation for retrieving drone and drone type details
 * from a remote server and processing the JSON responses.
 */
public abstract class AbstractDroneDetails {
    // Logging
    private static final Logger logger = LoggerFactory.getLogger();

    private static final String USER_AGENT = "Drone Details"; // User agent for HTTP requests
    private static final String TOKEN = ConfigLoader.getToken(); // API token for authorization

    /**
     * Fetches and processes drone details from the specified URL.
     * This method sends an HTTP GET request to the provided URL, retrieves the response
     * and processes it based on the type of data (drone or drone type).
     *
     * @param droneUrl URL to fetch drone details from
     * @return The URL used for fetching, or null if an error occurs
     */
    public String fetchAndProcessDroneDetails(String droneUrl) {
        // Check if the drone URL is null or empty
        if (droneUrl == null || droneUrl.isEmpty()) {
            logger.warning("Drone URL is null or empty.");
            ErrorLogger.handleMissingDrones(0); // Log the error
            return null;
        }

        try {
            logger.info("Fetch and process method for (further) drone details started.");

            // Create a URL object from the provided drone URL
            URL url = new URL(droneUrl);
            HttpURLConnection connection = createConnection(url);

            // Get the HTTP response code
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                // Handle the error if the response code is not HTTP_OK (200)
                ErrorLogger.handleHttpError(responseCode);
                return null;
            }

            // Read the response from the server
            String response = readResponse(connection);

            // Process the response based on the URL type
            if (droneUrl.contains("/api/drones/")) {
                logger.info("Inner JSON 'drones' found.");
                JSONObject droneDetails = new JSONObject(response);
                processDroneDetails(droneDetails); // Process drone details
            } else if (droneUrl.contains("/api/dronetypes/")) {
                logger.info("Inner JSON 'drone types' found.");
                JSONObject furtherDroneDetails = new JSONObject(response);
                processFurtherDroneDetails(furtherDroneDetails); // Process drone type details
            }
        } catch (MalformedURLException exceptionVariable) {
            // Handle MalformedURLException (invalid URL format)
            ErrorLogger.handleMalformedURLException(exceptionVariable);
        } catch (IOException exceptionVariable) {
            // Handle IOException (e.g., network issues)
            ErrorLogger.handleIOException(exceptionVariable);
        }
        return droneUrl; // Return the URL used for fetching
    }

    /**
     * Creates an HTTP connection to the specified URL.
     *
     * @param url URL to connect to
     * @return Configured HttpURLConnection instance
     * @throws IOException If an I/O error occurs
     */
    private HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", TOKEN);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        return connection;
    }

    /**
     * Reads the response from the HTTP connection.
     *
     * @param connection HttpURLConnection instance
     * @return Response as a string
     * @throws IOException If an I/O error occurs
     */
    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }

    /**
     * Abstract method to process drone details from a JSON object.
     *
     * @param droneDetails JSON object containing drone details
     * @return Formatted string of drone details
     */
    protected abstract String processDroneDetails(JSONObject droneDetails);

    /**
     * Abstract method to process further drone details (drone type information) from a JSON object.
     *
     * @param furtherDroneDetails JSON object containing drone type details
     * @return Formatted string of drone type details
     */
    protected abstract String processFurtherDroneDetails(JSONObject furtherDroneDetails);
}