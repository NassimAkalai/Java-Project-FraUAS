package manager;

import javax.swing.*;
import drones.DroneDataProcessor;
import java.awt.CardLayout;
import java.util.logging.Logger;
import utils.LoggerFactory;
import gui.DroneSelectionPanel;

/**
 * Implements the drone dynamics option for the application.
 * Handles the selection of drones for dynamics data.
 */
public class DroneDynamicsOption implements SelectionOptions {
    private static final Logger logger = LoggerFactory.getLogger();
    private JPanel mainPanel; // Main panel to display content
    private CardLayout cardLayout; // Layout manager for switching panels

    /**
     * Constructor to initialize the drone dynamics option.
     *
     * @param mainPanel  The main JPanel to display content.
     * @param cardLayout The CardLayout for panel navigation.
     */
    public DroneDynamicsOption(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    /**
     * Executes the drone dynamics option by initializing the drone selection panel.
     *
     * @param args Arguments passed to the execution logic (not used).
     */
    @Override
    public void execute(String[] args) {
        logger.info("Executing drone selection for drone dynamics.");

        // Create the GUI component (DroneSelectionPanel)
        DroneSelectionPanel droneSelectionPanel = new DroneSelectionPanel();

        // Create the logic component (DroneSelection) and pass the GUI component
        DroneSelection droneSelection = new DroneSelection(mainPanel, cardLayout, droneSelectionPanel);

        // Set the DroneSelection instance in DroneDataProcessor
        logger.info("Executing drone data processing for drone dynamics.");
        DroneDataProcessor.setDroneSelection(droneSelection);

        // Add the GUI component (DroneSelectionPanel) to the main panel
        mainPanel.add(droneSelectionPanel, "DroneSelection");

        // Show the DroneSelectionPanel
        cardLayout.show(mainPanel, "DroneSelection");
    }
}