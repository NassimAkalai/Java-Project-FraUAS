package manager;

import drones.Drones;
import utils.LoggerFactory;
import java.awt.*;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Option to handle the selection of drones.
 */
public class DronesOption implements SelectionOptions {
    private static final Logger logger = LoggerFactory.getLogger();
    private JPanel mainPanel;
    private CardLayout cardLayout;
 
    /**
     * Constructor to initialize the DronesOption.
     *
     * @param mainPanel  The main panel to display content.
     * @param cardLayout The CardLayout for panel navigation.
     */
    public DronesOption(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    }

    /**
     * Executes the logic to start the paging system for drones.
     *
     * @param args Arguments passed to the execution logic (not used).
     */
    @Override
    public void execute(String[] args) {
        logger.info("Executing paging system for drones.");
        Drones.startPagingSystem(mainPanel, cardLayout);
    }
}