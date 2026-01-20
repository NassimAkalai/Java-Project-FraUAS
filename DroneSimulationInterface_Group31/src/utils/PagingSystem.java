package utils;

import gui.PagingSystemPanel;
import org.json.JSONObject;
import drones.DroneDataFetcher;
import drones.DroneDataProcessor;

import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Handles pagination logic for fetching and displaying drone data.
 * Manages navigation between pages using next, previous, and back buttons.
 */
public class PagingSystem {
    // Logging
    private static final Logger logger = LoggerFactory.getLogger();

    private static String currentUrl; // Current URL for data fetching
    private static String nextUrl = null; // URL for the next page
    private static String previousUrl = null; // URL for the previous page
    private PagingSystemPanel pagingSystemPanel; // GUI panel for pagination
    private CardLayout cardLayout; // Layout manager for switching panels
    private JPanel mainPanel; // Main panel to display content

    /**
     * Constructor to initialize the PagingSystem.
     *
     * @param initialUrl The initial URL to fetch data from.
     * @param mainPanel  The main JPanel to display content.
     * @param cardLayout The CardLayout for panel navigation.
     * @throws IOException If an I/O error occurs during data fetching.
     */
    public PagingSystem(String initialUrl, JPanel mainPanel, CardLayout cardLayout) throws IOException {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.pagingSystemPanel = new PagingSystemPanel();

        currentUrl = initialUrl;

        // Set the PagingSystem instance in DroneDataProcessor for URL updates
        DroneDataProcessor.setPagingSystem(this);

        // Add ActionListener for the previous button
        pagingSystemPanel.getPreviousButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                handlePreviousButtonClick();
            }
        });

        // Add ActionListener for the next button
        pagingSystemPanel.getNextButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                handleNextButtonClick();
            }
        });

        // Add ActionListener for the back button to return to the main menu
        pagingSystemPanel.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                handleBackButtonClick();
            }
        });

        // Add the paging panel to the main panel
        mainPanel.add(pagingSystemPanel, "PagingSystem");
        fetchData(); // Fetch initial data
    }

    /**
     * Handles the click event for the previous button.
     * Loads the previous page if available.
     */
    private void handlePreviousButtonClick() {
        if (previousUrl != null) {
            logger.info("Previous page is loaded.");
            currentUrl = previousUrl;
            try {
                fetchData();
            } catch (IOException exceptionVariable) {
                ErrorLogger.handleIOException(exceptionVariable);
            }
        } else {
            logger.info("No previous URL.");
            ErrorLogger.handleNoPreviousPage();
        }
    }

    /**
     * Handles the click event for the next button.
     * Loads the next page if available.
     */
    private void handleNextButtonClick() {
        if (nextUrl != null) {
            logger.info("Next page is loaded.");
            currentUrl = nextUrl;
            try {
                fetchData();
            } catch (IOException exceptionVariable) {
                ErrorLogger.handleIOException(exceptionVariable);
            }
        } else {
            logger.info("No next URL.");
            ErrorLogger.handleNoNextPage();
        }
    }

    /**
     * Handles the click event for the back button.
     * Navigates back to the main menu.
     */
    private void handleBackButtonClick() {
        logger.info("User went back to the Menu.");
        cardLayout.show(mainPanel, "Menu");
    }

    /**
     * Fetches data from the current URL and updates the UI.
     *
     * @throws IOException If an I/O error occurs during data fetching.
     */
    private void fetchData() throws IOException {
        if (currentUrl.contains("/api/drones/") || currentUrl.contains("/api/dronetypes/")) {
            logger.info("Drone data is fetched for the corresponding URL.");
            DroneDataFetcher.fetchAndProcessDroneData(currentUrl);

            // Force an update of the panel after data has been fetched
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    pagingSystemPanel.getDataPanel().revalidate();
                    pagingSystemPanel.getDataPanel().repaint();
                }
            });
        }
    }

    /**
     * Updates the next and previous URLs based on the API response.
     *
     * @param jsonResponse The JSON response from the API.
     */
    public static void updateUrls(String jsonResponse) {
        logger.info("URLs are updated.");
        if (jsonResponse == null) return;
        JSONObject wholeFile = new JSONObject(jsonResponse);
        nextUrl = wholeFile.optString("next", null);
        previousUrl = wholeFile.optString("previous", null);
    }

    /**
     * Returns the paging system panel.
     *
     * @return The PagingSystemPanel instance.
     */
    public PagingSystemPanel getPagingSystemPanel() {
        return pagingSystemPanel;
    }
}