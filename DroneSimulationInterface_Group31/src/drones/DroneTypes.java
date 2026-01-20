package drones;

import gui.PagingSystemPanel;
import javax.swing.*;
import java.awt.CardLayout;
import java.io.IOException;
import java.util.logging.Logger;
import utils.ConfigLoader;
import utils.LoggerFactory;
import utils.PagingSystem;
import utils.ErrorLogger;

/**
 * Handles the initialization and display of the paging system for drone types.
 */
public class DroneTypes {
    private static final Logger logger = LoggerFactory.getLogger();
    private static final String ENDPOINT_URL = ConfigLoader.getDroneTypesUrl();

    /**
     * Starts the paging system for drone types.
     *
     * @param panel  The main panel to display the paging system.
     * @param layout The CardLayout for switching views.
     */
    public static void startPagingSystem(JPanel panel, CardLayout layout) {
        try {
            logger.info("Start paging system for drone types.");

            // Create an instance of PagingSystem (logic)
            PagingSystem pagingSystem = new PagingSystem(ENDPOINT_URL, panel, layout);

            // Get the PagingSystemPanel (GUI) from the PagingSystem instance
            PagingSystemPanel pagingSystemPanel = pagingSystem.getPagingSystemPanel();

            // Check if the panel uses CardLayout
            if (!(panel.getLayout() instanceof CardLayout)) {
                throw new IllegalArgumentException("The provided panel does not use CardLayout!");
            }

            // Add the PagingSystemPanel to the main panel
            panel.add(pagingSystemPanel, "PagingSystem");

            // Show the PagingSystemPanel
            layout.show(panel, "PagingSystem");
        } catch (IOException exceptionVariable) {
            ErrorLogger.handleIOException(exceptionVariable);
        }
    }
}