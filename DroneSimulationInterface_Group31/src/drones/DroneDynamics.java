package drones;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import org.json.JSONObject;
import utils.ErrorLogger;
import utils.LinkFormatter;
import utils.LoggerFactory;

/**
 * Handles the periodic fetching and processing of drone dynamics data.
 */
public class DroneDynamics {
    private static final Logger logger = LoggerFactory.getLogger();
    private static final int REFRESH_INTERVAL_MS = 30000; // Refresh interval in milliseconds
    private static Timer timer; // Timer for periodic updates
    private static int lastCount = 0; // Last count of drone dynamics data

    /**
     * Starts the timer for periodic updates of drone dynamics data.
     */
    public static void startTimer() {
        logger.info("Start timer for updates.");
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = new Timer();
        lastCount = 0;

        String currentUrl = LinkFormatter.getProcessedLink();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    logger.info("Loading newest information...");
                    String responseString = DroneDataFetcher.fetchAndProcessDroneData(currentUrl);
                    JSONObject countFile = new JSONObject(responseString);
                    int count = countFile.getInt("count") - 1;

                    if (lastCount != count) {
                        logger.info("New information found.");
                        lastCount = count;
                        String newLink = LinkFormatter.offsetCalculator(count);
                        String responseStringNew = DroneDataFetcher.fetchAndProcessDroneData(newLink);
                        DroneDataProcessor.processPage(responseStringNew);
                    }
                } catch (MalformedURLException exceptionVariable) {
                    ErrorLogger.handleMalformedURLException(exceptionVariable);
                } catch (IOException exceptionVariable) {
                    ErrorLogger.handleIOException(exceptionVariable);
                }
            }
        }, 0, REFRESH_INTERVAL_MS);
    }

    /**
     * Stops the timer for periodic updates.
     */
    public static void stopTimer() {
        logger.info("Stop timer.");
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
}