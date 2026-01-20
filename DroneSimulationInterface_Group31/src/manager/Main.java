package manager;

import gui.MainWindow;
import utils.LoggerFactory;
import utils.ErrorLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * The main entry point for the application.
 * Initializes the GUI and sets up action listeners for user interactions.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger();

    /**
     * The main method to start the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Use SwingUtilities to ensure GUI updates are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 
                MainWindow mainWindow = new MainWindow();
                new Main().initialize(mainWindow);
            }
        });
    }

    /**
     * Initializes the main window and sets up action listeners for buttons.
     *
     * @param mainWindow The main window of the application.
     */
    public void initialize(MainWindow mainWindow) {
        logger.info("Program has started.");

        // Add action listener for the execute button
        mainWindow.addActionListenerToExecuteButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String selectedOption = mainWindow.getSelectedOption();
                if ("Drone Dynamics".equalsIgnoreCase(selectedOption)) {
                    logger.info("Drones Dynamics has been selected.");
                    DroneDynamicsOption droneDynamicsOption = new DroneDynamicsOption(mainWindow.getMainPanel(), mainWindow.getCardLayout());
                    droneDynamicsOption.execute(null);
                } else if ("Drones".equalsIgnoreCase(selectedOption)) {
                    logger.info("Drones has been selected.");
                    DronesOption dronesOption = new DronesOption(mainWindow.getMainPanel(), mainWindow.getCardLayout());
                    dronesOption.execute(null);
                } else if ("Drone Types".equalsIgnoreCase(selectedOption)) {
                    logger.info("Drones Types has been selected.");
                    DroneTypesOption droneTypesOption = new DroneTypesOption(mainWindow.getMainPanel(), mainWindow.getCardLayout());
                    droneTypesOption.execute(null);
                } else {
                    ErrorLogger.invalidInputException();
                }
            }
        });

        // Add action listener for the exit button
        mainWindow.addActionListenerToExitButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                ExitOption exitOption = new ExitOption();
                exitOption.execute(null);
            }
        });
    }
}