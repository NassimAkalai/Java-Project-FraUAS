package drones;

import org.json.JSONArray;
import org.json.JSONObject;
import manager.DroneSelection;
import utils.*;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.HashMap;
import java.util.Map;

/**
 * This class processes drone-related data from JSON responses.
 * It handles drone dynamics, drone details, and drone type information.
 * A caching mechanism is implemented to avoid reloading data unnecessarily.
 */
public class DroneDataProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger();
	private static DroneSelection droneSelection; // Manages drone selection logic
	private static PagingSystem pagingSystem; // Manages pagination and UI updates

	// Caches for drones and drone types to avoid redundant API calls
	private static final Map<Integer, String> droneCache = new HashMap<>();
	private static final Map<Integer, String> droneTypeCache = new HashMap<>();

	// DynamicsCalculator instance for calculating average speed and total distance
	private static final DynamicsCalculator dynamicsCalculator = new DynamicsCalculator();

	/**
	 * Sets the PagingSystem instance for UI updates.
	 *
	 * @param pagingSystem Instance of PagingSystem
	 */
	public static void setPagingSystem(PagingSystem pagingSystem) {
		DroneDataProcessor.pagingSystem = pagingSystem;
	}

	/**
	 * Sets the DroneSelection instance for drone data management.
	 *
	 * @param selection Instance of DroneSelection
	 */
	public static void setDroneSelection(DroneSelection selection) {
		droneSelection = selection;
	}

	/**
	 * Processes the input JSON string based on its content type.
	 * Handles drone dynamics, drone details, and drone type data.
	 *
	 * @param input JSON string to process
	 */
	public static void processPage(String input) {
		if (isInvalidInput(input)) {
			ErrorLogger.handleJsonException();
			return;
		}

		JSONObject wholeFile = new JSONObject(input);
		JSONArray jsonFile = wholeFile.getJSONArray("results");

		if (jsonFile.isEmpty()) {
			ErrorLogger.handleJsonException();
			return;
		}

		// Process data based on the type of JSON content
		if (input.contains("dynamic")) {
			processDroneDynamics(jsonFile);
		} else if (input.contains("/api/drones/")) {
			processDrones(jsonFile);
			LOGGER.info("Updating URLs in paging system.");
			PagingSystem.updateUrls(input);
		} else if (input.contains("/api/dronetypes/")) {
			processDroneTypes(jsonFile);
			LOGGER.info("Updating URLs in paging system.");
			PagingSystem.updateUrls(input);
		}
	}

	/**
	 * Checks if the input string is invalid (null or empty).
	 *
	 * @param input Input string to validate
	 * @return True if the input is invalid, false otherwise
	 */
	private static boolean isInvalidInput(String input) {
		return input == null || input.isEmpty();
	}

	/**
	 * Processes drone dynamics data from the JSON array.
	 *
	 * @param jsonFile JSON array containing drone dynamics data
	 */
	private static void processDroneDynamics(JSONArray jsonFile) {
		LOGGER.info("Processing drone dynamics data.");
		for (int i = 0; i < jsonFile.length(); i++) {
			JSONObject objectFile = jsonFile.getJSONObject(i);
			if (objectFile.has("drone")) {
				String droneData = extractDroneDynamicsData(objectFile);
				if (droneSelection != null) {
					droneSelection.updateDroneDynamicsData(droneData);
				} else {
					ErrorLogger.handleJsonException();
				}
			}
		}
	}

	/**
	 * Extracts and formats drone dynamics data from the JSON object.
	 *
	 * @param objectFile JSON object containing drone dynamics data
	 * @return Formatted string of drone dynamics data
	 */
	private static String extractDroneDynamicsData(JSONObject objectFile) {
		double speed = objectFile.getInt("speed");
		int batteryStatus = objectFile.getInt("battery_status");
		
		Double alignRoll = objectFile.getDouble("align_roll");
		Double alignPitch = objectFile.getDouble("align_pitch");
		Double alignYaw = objectFile.getDouble("align_yaw");
		
		String status = objectFile.getString("status");
		String formattedDate = DateFormatter.main(objectFile.getString("last_seen"));
		String formattedTime = DateFormatter.main(objectFile.getString("timestamp"));

		// Add speed to DynamicsCalculator if user did not skip
		boolean skip = DroneSelection.getSkip();
		
		if(skip == false) {
			dynamicsCalculator.addSpeed(speed);
		}

		DroneDetails droneDetails = new DroneDetails();
		droneDetails.fetchAndProcessDroneDetails(objectFile.getString("drone"));

		return String.format(
				"=================================================\n%s\n\n%s\n\nCurrent status: %s\nCurrent speed: %f km/h\nCurrent battery status: %d mAh\n\nAlign Roll: %f\nAlign Pitch: %f\nAlign Yaw: %f\n\nLongitude: %.6f\nLatitude: %.6f\n\nTimestamp: %s\nLast seen on: %s",
				droneDetails.getFinalDroneDetails(),
				droneDetails.getFinalFurtherDroneDetails(),
				status, speed, batteryStatus, alignRoll, alignPitch, alignYaw,
				objectFile.getDouble("longitude"), objectFile.getDouble("latitude"),
				formattedTime, formattedDate
				); 
	}

	/**
	 * Processes drone data from the JSON array.
	 * Uses a cache to avoid reloading data for drones that have already been processed.
	 *
	 * @param jsonFile JSON array containing drone data
	 */
	private static void processDrones(JSONArray jsonFile) {
		LOGGER.info("Processing drone data.");
		for (int i = 0; i < jsonFile.length(); i++) {
			JSONObject objectFile = jsonFile.getJSONObject(i);
			if (objectFile.has("id")) {
				int droneId = objectFile.getInt("id");

				// Check if the drone data is already in the cache
				if (droneCache.containsKey(droneId)) {
					LOGGER.info("Drone data found in cache.");
					updatePanelWithData(droneCache.get(droneId), i);
				} else {
					// Drone not in cache, load and cache the data
					String droneData = extractDroneData(objectFile);
					droneCache.put(droneId, droneData);
					updatePanelWithData(droneData, i);
				}
			} else {
				ErrorLogger.handleJsonException();
			}
		}
	}

	/**
	 * Extracts and formats drone data from the JSON object.
	 *
	 * @param objectFile JSON object containing drone data
	 * @return Formatted string of drone data
	 */
	private static String extractDroneData(JSONObject objectFile) {
		String formattedDate = DateFormatter.main(objectFile.getString("created"));
		DroneDetails droneDetails = new DroneDetails();
		droneDetails.fetchAndProcessDroneDetails(objectFile.getString("dronetype"));

		return String.format(
				"Drone ID: %d\nSerial: %s\nCarriage: %s\nWeight: %dg\nCreated: %s\n\n%s",
				objectFile.getInt("id"), objectFile.getString("serialnumber"),
				objectFile.getString("carriage_type"), objectFile.getInt("carriage_weight"),
				formattedDate, droneDetails.getFinalFurtherDroneDetails()
				);
	} 

	/**
	 * Processes drone type data from the JSON array.
	 * Uses a cache to avoid reloading data for drone types that have already been processed.
	 *
	 * @param jsonFile JSON array containing drone type data
	 */
	private static void processDroneTypes(JSONArray jsonFile) {
		LOGGER.info("Processing drone type data.");
		for (int i = 0; i < jsonFile.length(); i++) {
			JSONObject objectFile = jsonFile.getJSONObject(i);
			if (objectFile.has("id")) {
				int droneTypeId = objectFile.getInt("id");

				// Check if the drone type data is already in the cache
				if (droneTypeCache.containsKey(droneTypeId)) {
					LOGGER.info("Drone type data found in cache.");
					updatePanelWithData(droneTypeCache.get(droneTypeId), i);
				} else {
					// Drone type not in cache, load and cache the data
					String droneTypeData = extractDroneTypeData(objectFile);
					droneTypeCache.put(droneTypeId, droneTypeData);
					updatePanelWithData(droneTypeData, i);
				}
			} else {
				ErrorLogger.handleJsonException();
			}
		}
	}

	/**
	 * Extracts and formats drone type data from the JSON object.
	 *
	 * @param objectFile JSON object containing drone type data
	 * @return Formatted string of drone type data
	 */
	private static String extractDroneTypeData(JSONObject objectFile) {
		return String.format(
				"Manufacturer: %s\nType: %s\nID: %d\nWeight: %d g\nMax Speed: %d km/h\nBattery: %d mAh\nRange: %d m²\nMax Carriage: %d g",
				objectFile.getString("manufacturer"), objectFile.getString("typename"),
				objectFile.getInt("id"), objectFile.getInt("weight"),
				objectFile.getInt("max_speed"), objectFile.getInt("battery_capacity"),
				objectFile.getInt("control_range"), objectFile.getInt("max_carriage")
				);
	}

	/**
	 * Updates the UI panel with the provided data.
	 *
	 * @param data Data to display in the panel
	 * @param panelIndex Index of the panel to update
	 */
	private static void updatePanelWithData(String data, int panelIndex) {
		LOGGER.info("Updating panels in paging system with new data.");
		if (pagingSystem != null) {
			JPanel square = (JPanel) pagingSystem.getPagingSystemPanel().getDataPanel().getComponent(panelIndex);
			square.removeAll();
			square.add(new JLabel("<html>" + data.replace("\n", "<br>") + "</html>"));
			square.revalidate();
			square.repaint();
		} else {
			LOGGER.warning("PagingSystem instance is not set.");
		}
	}
}