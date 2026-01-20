package drones;

import java.util.logging.Logger;
import org.json.JSONObject;
import utils.DateFormatter;
import utils.LoggerFactory;

/**
 * This class extends AbstractDroneDetails and processes drone-related information.
 * It extracts and formats details about drones and their types.
 */
public class DroneDetails extends AbstractDroneDetails {
    private static final Logger logger = LoggerFactory.getLogger();
    private String finalDroneDetails = ""; // Stores formatted drone details
    private String finalFurtherDroneDetails = ""; // Stores formatted drone type details

    /**
     * Processes the drone details from the provided JSON object.
     * Extracts and formats relevant fields such as ID, serial number, and creation date.
     *
     * @param droneDetails JSON object containing drone details
     * @return Formatted string of drone details
     */
    @Override
    protected String processDroneDetails(JSONObject droneDetails) {
        logger.info("Processing drone details.");
        if (droneDetails.has("id")) {
            String furtherDroneUrl = droneDetails.getString("dronetype");
            int id = droneDetails.getInt("id");
            String serialNumber = droneDetails.getString("serialnumber");
            String carriageType = droneDetails.getString("carriage_type");
            int carriageWeight = droneDetails.getInt("carriage_weight");
            String createdDate = droneDetails.getString("created");
            String formattedDate = DateFormatter.main(createdDate);

            // Format drone details into a readable string
            finalDroneDetails = String.format(
                    "Drone ID: %d\nSerial number: %s\nCarriage type: %s\nCarriage weight: %dg\nCreated on: %s",
                    id, serialNumber, carriageType, carriageWeight, formattedDate
            );

            // Fetch and process additional drone type details
            fetchAndProcessDroneDetails(furtherDroneUrl);
        }
        return finalDroneDetails;
    }

    /**
     * Processes further drone details (drone type information) from the provided JSON object.
     * Extracts and formats fields such as manufacturer and type name.
     *
     * @param furtherDroneDetails JSON object containing drone type details
     * @return Formatted string of drone type details
     */
    @Override
    protected String processFurtherDroneDetails(JSONObject furtherDroneDetails) {
        logger.info("Processing further drone details.");
        if (furtherDroneDetails.has("id")) {
            int id = furtherDroneDetails.getInt("id");
            String manufacturer = furtherDroneDetails.getString("manufacturer");
            String typeName = furtherDroneDetails.getString("typename");

            // Format drone type details into a readable string
            finalFurtherDroneDetails = String.format(
                    "Drone Type ID: %d\nManufacturer: %s\nType name: %s",
                    id, manufacturer, typeName
            );
        }
        return finalFurtherDroneDetails;
    }

    /**
     * Returns the formatted drone details.
     *
     * @return Formatted drone details as a string
     */
    public String getFinalDroneDetails() {
        logger.info("Retrieving drone details.");
        return finalDroneDetails;
    }

    /**
     * Returns the formatted further drone details (drone type information).
     *
     * @return Formatted drone type details as a string
     */
    public String getFinalFurtherDroneDetails() {
        logger.info("Retrieving further drone details.");
        return finalFurtherDroneDetails;
    }
}