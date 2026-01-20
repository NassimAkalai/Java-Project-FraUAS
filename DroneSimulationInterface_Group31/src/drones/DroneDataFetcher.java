package drones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import utils.ConfigLoader;
import utils.ErrorLogger;
import utils.IdFetcher;
import utils.LoggerFactory;
import utils.PagingSystem;

/**
 * This class fetches drone-related data from a remote API and processes the response.
 */
public class DroneDataFetcher {
    private static final Logger logger = LoggerFactory.getLogger();
    private static final String USER_AGENT = "Drones"; // User agent for HTTP requests
    private static final String TOKEN = ConfigLoader.getToken(); // API token for authorization

    /**
     * Fetches and processes drone data from the specified URL.
     *
     * @param currentUrl URL to fetch data from
     * @return Processed response as a string, or null if an error occurs
     */
    public static String fetchAndProcessDroneData(String currentUrl) {
        try {
            logger.info("Fetching and processing drone data.");
            @SuppressWarnings("deprecation")
			URL url = new URL(currentUrl);
            HttpURLConnection connection = createConnection(url);

            int responseCode = connection.getResponseCode();
            logger.info("Response Code: " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                ErrorLogger.handleHttpError(responseCode);
                return null;
            }

            String responseString = readResponse(connection);
            return processResponse(currentUrl, responseString);
        } catch (MalformedURLException exceptionVariable) {
            ErrorLogger.handleMalformedURLException(exceptionVariable);
        } catch (IOException exceptionVariable) {
            ErrorLogger.handleIOException(exceptionVariable);
        }
        return null;
    }

    /**
     * Creates an HTTP connection to the specified URL.
     *
     * @param url URL to connect to
     * @return Configured HttpURLConnection instance
     * @throws IOException If an I/O error occurs
     */
    private static HttpURLConnection createConnection(URL url) throws IOException {
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
    private static String readResponse(HttpURLConnection connection) throws IOException {
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
     * Processes the API response based on the URL type.
     *
     * @param url URL used for the request
     * @param response Response string to process
     * @return Processed response or null
     */
    private static String processResponse(String url, String response) {
        if (url.contains("%d")) {
            logger.info("Returning formatted response.");
            return response;
        } else if (url.contains("limit=1/")) {
            logger.info("Processing ID range.");
            IdFetcher.processId(response);
        } else {
            logger.info("Updating paging system URLs.");
            PagingSystem.updateUrls(response);
            DroneDataProcessor.processPage(response);
        }
        return null;
    }
}