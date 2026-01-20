package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Utility class for loading configuration properties from a file.
 */
public class ConfigLoader {
    private static final Logger logger = LoggerFactory.getLogger();
    private static Properties properties = new Properties();

    static {
        logger.info("Config loader started.");
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
        	if (inputStream == null) {
        		logger.warning("Config file not found.");
        		ErrorLogger.handleFileNotFound("config.properties");
        	}
            properties.load(inputStream); // Load properties from the file
            logger.info("Config loaded successfully.");
        } catch (IOException exceptionVariable) {
            ErrorLogger.handleIOException(exceptionVariable);
        }
    }

    /**
     * Returns the API token from the configuration.
     *
     * @return The API token.
     */
    public static String getToken() {
        logger.info("Get Token.");
        return properties.getProperty("api.token");
    }

    /**
     * Returns the ID fetcher URL from the configuration.
     *
     * @return The ID fetcher URL.
     */
    public static String getIdFetcherUrl() {
        logger.info("Get ID fetcher URL.");
        return properties.getProperty("idFetcher.url");
    }

    /**
     * Returns the drone dynamics URL from the configuration.
     *
     * @return The drone dynamics URL.
     */
    public static String getDroneDynamicsUrl() {
        logger.info("Get drone dynamics URL.");
        return properties.getProperty("LinkFormatter.droneDynamicsUrl");
    }

    /**
     * Returns the default URL from the configuration.
     *
     * @return The default URL.
     */
    public static String getDefaultUrl() {
        logger.info("Get default URL.");
        return properties.getProperty("LinkFormatter.defaultsUrl");
    }

    /**
     * Returns the drones URL from the configuration.
     *
     * @return The drones URL.
     */
    public static String getDronesUrl() {
        logger.info("Get drones URL.");
        return properties.getProperty("drones.url");
    }

    /**
     * Returns the drone types URL from the configuration.
     *
     * @return The drone types URL.
     */
    public static String getDroneTypesUrl() {
        logger.info("Get drone types URL.");
        return properties.getProperty("droneTypes.url");
    }
}