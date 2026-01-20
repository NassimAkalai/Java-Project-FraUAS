package manager;

import gui.DroneSelectionPanel;
import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Logger;

import drones.DroneDataFetcher;
import drones.DroneDataProcessor;
import drones.DroneDynamics;
import drones.DynamicsCalculator;
import utils.IdFetcher;
import utils.LinkFormatter;
import utils.LoggerFactory;
import utils.ErrorLogger;

/**
 * Handles the drone selection logic for the application.
 * Manages user input for selecting a drone and updating the interface.
 */
public class DroneSelection {
	private static final Logger logger = LoggerFactory.getLogger();

	private DroneSelectionPanel droneSelectionPanel; // Reference to the GUI component 
	private CardLayout cardLayout; // Layout manager for switching panels
	private JPanel mainPanel; // Main panel to display content
	private int firstId; // First ID in the range
	private int lastId; // Last ID in the range
	private static boolean skip = false;

	/**
	 * Constructor to initialize the drone selection logic.
	 *
	 * @param mainPanel           The main JPanel to display content.
	 * @param cardLayout          The CardLayout for panel navigation.
	 * @param droneSelectionPanel The GUI component for drone selection.
	 */
	public DroneSelection(JPanel mainPanel, CardLayout cardLayout, DroneSelectionPanel droneSelectionPanel) {
		this.mainPanel = mainPanel;
		this.cardLayout = cardLayout;
		this.droneSelectionPanel = droneSelectionPanel;

		initializeIds(); 
		setupButtonListeners();
	}

	/**
	 * Initializes the ID range by fetching data from the API.
	 */
	private void initializeIds() {
		IdFetcher.main(null); // Fetch ID range
		this.firstId = IdFetcher.getFirstId(); // Get the first ID
		this.lastId = IdFetcher.getLastId(); // Get the last ID
		updateSearchFieldLabel(); // Update the search field label
	}

	/**
	 * Updates the search field label with the valid ID range.
	 */
	private void updateSearchFieldLabel() {
		JLabel label = (JLabel) ((JPanel) droneSelectionPanel.getSearchField().getParent()).getComponent(0);
		label.setText("Enter Drone ID between " + firstId + " and " + lastId + ":"); // Update the label text
	}

	/**
	 * Sets up action listeners for all buttons including navigation buttons.
	 */
	private void setupButtonListeners() {
		droneSelectionPanel.getSelectButton().addActionListener(this::handleSelectButton);
		droneSelectionPanel.getSkipButton().addActionListener(this::handleSkipButton);
		droneSelectionPanel.getBackButton().addActionListener(this::handleBackButton);

		// Add listeners for navigation buttons
		droneSelectionPanel.getFirstPageButton().addActionListener(this::handleFirstPageButton);
		droneSelectionPanel.getPreviousButton().addActionListener(this::handlePreviousButton);
		droneSelectionPanel.getNextButton().addActionListener(this::handleNextButton);
		droneSelectionPanel.getLastPageButton().addActionListener(this::handleLastPageButton);
	}

	/**
	 * Handles the select button click event.
	 *
	 * @param event The action event.
	 */
	private void handleSelectButton(ActionEvent event) {
		droneSelectionPanel.getMessageArea().setText(""); // Clear the message area
		DynamicsCalculator.reset();
		selectDrone(); // Process drone selection
	}

	/**
	 * Handles the skip button click event.
	 *
	 * @param event The action event.
	 */
	private void handleSkipButton(ActionEvent event) {
		droneSelectionPanel.getMessageArea().setText(""); // Clear the message area
		logger.info("Selection skipped. Newest information has been requested.");
		handleSkipSelection(); // Handle skip selection
	}

	/**
	 * Handles the back button click event.
	 *
	 * @param event The action event.
	 */
	private void handleBackButton(ActionEvent event) {
		logger.info("User went back to the Menu.");
		DroneDynamics.stopTimer(); // Stop the timer
		cardLayout.show(mainPanel, "Menu"); // Navigate back to the menu
	}

	/**
	 * Handles the previous button click event.
	 *
	 * @param event The action event.
	 */
	private void handlePreviousButton(ActionEvent event) {
		int currentOffset = droneSelectionPanel.getCurrentOffset();
		if (currentOffset >= 10) {  // Only go back if we're not at the start
			droneSelectionPanel.getMessageArea().setText(""); // Clear the message area
			currentOffset -= 10;
			droneSelectionPanel.setCurrentOffset(currentOffset);
			DynamicsCalculator.reset();
			loadDronesAtOffset(currentOffset);
		} else {
			logger.info("Already at the first page");
			ErrorLogger.handleNoPreviousPage();
		}
	}

	/**
	 * Handles the next button click event.
	 *
	 * @param event The action event.
	 */
	private void handleNextButton(ActionEvent event) {
		int currentOffset = droneSelectionPanel.getCurrentOffset();
		if(currentOffset <=currentOffset) {
			droneSelectionPanel.getMessageArea().setText(""); // Clear the message area

			currentOffset += 10;
			droneSelectionPanel.setCurrentOffset(currentOffset);
			DynamicsCalculator.reset();
			loadDronesAtOffset(currentOffset);
		}
		else {
			logger.info("Already at the last page");
			ErrorLogger.handleNoNextPage();
		}
	}

	/**
	 * Handles the first page button click event.
	 * 
	 * @param event The action event.
	 */
	private void handleFirstPageButton(ActionEvent event) {
		droneSelectionPanel.getMessageArea().setText(""); // Clear the message area
		logger.info("Navigating to first page");
		droneSelectionPanel.setCurrentOffset(0);
		DynamicsCalculator.reset();
		loadDronesAtOffset(0);
	}

	/**
	 * Handles the last page button click event.
	 * 
	 * @param event The action event.
	 */
	private void handleLastPageButton(ActionEvent event) {
		droneSelectionPanel.getMessageArea().setText(""); // Clear the message area
		// Get total count from the base URL to calculate last page
		String baseUrl = LinkFormatter.getProcessedLink();
		String countResponse = DroneDataFetcher.fetchAndProcessDroneData(baseUrl);
		if (countResponse != null) {
			int totalCount = new org.json.JSONObject(countResponse).getInt("count");
			droneSelectionPanel.setTotalCount(totalCount);

			// Calculate last page offset (ensure it's a multiple of 10)
			int lastPageOffset = Math.max(0, (totalCount - 10) * 10 / 10);
			droneSelectionPanel.setCurrentOffset(lastPageOffset);
			DynamicsCalculator.reset();
			loadDronesAtOffset(lastPageOffset);

			logger.info("Navigating to last page at offset: " + lastPageOffset);
		}
	}

	/**
	 * Loads drones at the specified offset.
	 */
	private void loadDronesAtOffset(int offset) {
		try {
			logger.info("Loading drones at offset: " + offset);
			String url = LinkFormatter.offsetCalculator(offset);
			String responseData = DroneDataFetcher.fetchAndProcessDroneData(url);

			if (responseData != null && !responseData.isEmpty()) {
				appendMessage("Loading drones starting from offset " + offset + "...\n");
				DroneDataProcessor.processPage(responseData);

				// Update total count if needed
				org.json.JSONObject jsonResponse = new org.json.JSONObject(responseData);
				if (jsonResponse.has("count")) {
					droneSelectionPanel.setTotalCount(jsonResponse.getInt("count"));
				}
			} else {
				logger.warning("No data received for offset: " + offset);
				appendMessage("No more drones available at this offset.\n");

				// If user did not skip, calculate average speed and total distance
				if (skip == false) {
					DynamicsCalculator.calculateAverageSpeed();
					double avgSpeed = DynamicsCalculator.getAvgSpeed();
					double totalDistance = DynamicsCalculator.getTotalDistance();

					appendMessage("Average speed of the last ten instances: " + avgSpeed + "km/h" + "\nTotal distance of the last ten instances: "  + totalDistance + "m");
				}
			}
		} catch (IOException exceptionVariable) {
			logger.severe("Error loading drones: " + exceptionVariable.getMessage());
			ErrorLogger.handleIOException(exceptionVariable);
		}
	}

	/**
	 * Processes the drone selection based on user input.
	 */
	private void selectDrone() {
		try {
			int droneID = Integer.parseInt(droneSelectionPanel.getSearchField().getText().trim()); // Parse the input
			if (droneID >= firstId && droneID <= lastId) {
				handleValidDroneSelection(droneID); // Handle valid selection
			} else {
				handleInvalidDroneSelection(); // Handle invalid selection
			}
		} catch (NumberFormatException exceptionVariable) {
			handleNonNumericInput(); // Handle non-numeric input
		}
	}

	/**
	 * Handles the skip selection logic.
	 * @return 
	 */
	private boolean handleSkipSelection() {
		logger.info("Selection skipped. Newest information has been requested.");
		appendMessage("Selection skipped. Newest information will be shown.\n"); // Add message to the GUI
		processDroneSelection(0); // Process drone selection with ID 0
		DynamicsCalculator.reset();

		// Calculate correct offset
		String baseUrl = LinkFormatter.getProcessedLink();
		String countResponse = DroneDataFetcher.fetchAndProcessDroneData(baseUrl);
		int totalCount = new org.json.JSONObject(countResponse).getInt("count");

		skip = true;

		int offset = Math.max(0, (totalCount - 1) / 10 * 10); // Calculate the offset for the page containing the selected drone
		droneSelectionPanel.setCurrentOffset(offset);

		return skip;
	}

	public static boolean getSkip() {
		return skip;
	}

	/**
	 * Handles the valid drone selection logic.
	 *
	 * @param droneID The selected drone ID.
	 */
	private void handleValidDroneSelection(int droneID) {
		logger.info("User selected the Drone: " + droneID);
		appendMessage("You selected Drone ID: " + droneID + ". Now proceeding to the main interface...\n"); // Add message to the GUI
		processDroneSelection(droneID); // Process drone selection

		// Calculate correct offset
		String baseUrl = LinkFormatter.getProcessedLink();
		String countResponse = DroneDataFetcher.fetchAndProcessDroneData(baseUrl);
		int totalCount = new org.json.JSONObject(countResponse).getInt("count");

		int offset = Math.max(0, (totalCount - 1) / 10 * 10); // Calculate the offset for the page containing the selected drone
		droneSelectionPanel.setCurrentOffset(offset);
	}

	/**
	 * Handles the invalid drone selection logic.
	 */
	private void handleInvalidDroneSelection() {
		logger.info("User selected an invalid drone ID.");
		ErrorLogger.invalidIDException(); // Log and display error
	}

	/**
	 * Handles the non-numeric input logic.
	 */
	private void handleNonNumericInput() {
		logger.info("User selected a non-numeric input.");
		ErrorLogger.nonNumericValueException(); // Log and display error
	}

	/**
	 * Processes the drone selection by formatting the URL and running the interface.
	 *
	 * @param droneID The selected drone ID.
	 */
	private void processDroneSelection(int droneID) {
		LinkFormatter.processDrone(droneID); // Format the URL
		runInterface(droneID); // Run the interface
	}

	/**
	 * Appends a message to the message area in the GUI.
	 *
	 * @param message The message to append.
	 */
	/**
	 * Appends a message to the message area.
	 */
	private void appendMessage(String message) {
		droneSelectionPanel.getMessageArea().append(message);
		droneSelectionPanel.getMessageArea().setCaretPosition(
				droneSelectionPanel.getMessageArea().getDocument().getLength()
				);
	}

	/**
	 * Runs the interface for the selected drone.
	 *
	 * @param droneID The selected drone ID.
	 */
	private void runInterface(int droneID) {
		logger.info("Run interface with the selected drone ID.");
		appendMessage("Run interface for Drone ID: " + droneID + "\n"); // Add message to the GUI
		DroneDynamics.startTimer(); // Start the timer
	}

	/**
	 * Updates the drone dynamics data in the GUI.
	 *
	 * @param data The data to display.
	 */
	public void updateDroneDynamicsData(String data) {
		logger.info("Drone dynamics data has been updated.");
		droneSelectionPanel.getMessageArea().append(data + "\n\n"); // Append the data
		droneSelectionPanel.getMessageArea().setCaretPosition(droneSelectionPanel.getMessageArea().getDocument().getLength()); // Scroll to the bottom
	}
}