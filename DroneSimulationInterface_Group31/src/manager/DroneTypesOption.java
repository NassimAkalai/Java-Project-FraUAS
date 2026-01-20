package manager;

import drones.DroneTypes;
import utils.LoggerFactory;
import java.awt.*;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Option to handle the selection of drone types.
 */
public class DroneTypesOption implements SelectionOptions {
    private static final Logger logger = LoggerFactory.getLogger();
    private JPanel mainPanel;
    private CardLayout cardLayout;

    /** 
     * Constructor to initialize the DroneTypesOption.
     *
     * @param mainPanel  The main panel to display content.
     * @param cardLayout The CardLayout for panel navigation.
     */
    public DroneTypesOption(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    /**
     * Executes the logic to start the paging system for drone types.
     *
     * @param args Arguments passed to the execution logic (not used).
     */
    @Override
    public void execute(String[] args) {
        logger.info("Executing paging system for drone types.");
        DroneTypes.startPagingSystem(mainPanel, cardLayout);
    }
}