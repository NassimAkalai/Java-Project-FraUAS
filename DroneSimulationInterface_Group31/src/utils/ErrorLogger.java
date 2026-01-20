package utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Utility class for handling and logging errors.
 */
public class ErrorLogger {
	private static final Logger logger = LoggerFactory.getLogger();

	/**
	 * Handles MalformedURLException by logging and displaying an error message.
	 *
	 * @param exceptionVariable The exception to handle.
	 */
	public static void handleMalformedURLException(MalformedURLException exceptionVariable) {
		logger.severe("Malformed URL Exception: " + exceptionVariable.getMessage());
		JOptionPane.showMessageDialog(null, "Malformed URL: " + exceptionVariable.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles IOException by logging and displaying an error message.
	 *
	 * @param exceptionVariable The exception to handle.
	 */
	public static void handleIOException(IOException exceptionVariable) {
		logger.severe("IO Exception: " + exceptionVariable.getMessage());
		JOptionPane.showMessageDialog(null, "IO Exception: " + exceptionVariable.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles general exceptions by logging and displaying an error message.
	 *
	 * @param exceptionVariable The exception to handle.
	 */
	protected static void handleGeneralException(Exception exceptionVariable) {
		logger.severe("General Exception: " + exceptionVariable.getMessage());
		JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + exceptionVariable.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles HTTP errors by logging and displaying an error message.
	 *
	 * @param responseCode The HTTP response code.
	 */
	public static void handleHttpError(int responseCode) {
		logger.severe("HTTP Error: Response Code " + responseCode);
		JOptionPane.showMessageDialog(null, "Unable to fetch data, response code: " + responseCode, "HTTP Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles missing drones error by logging and displaying an error message.
	 *
	 * @param responseCode The HTTP response code.
	 */
	public static void handleMissingDrones(int responseCode) {
		logger.severe("Error: Unable to fetch drone details, response code: " + responseCode);
		JOptionPane.showMessageDialog(null, "Unable to fetch drone details, response code: " + responseCode, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles JSON parsing errors by logging and displaying an error message.
	 */
	public static void handleJsonException() {
		logger.severe("Error: Unable to find more data.");
		JOptionPane.showMessageDialog(null, "Unable to find more data.", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles invalid ID errors by logging and displaying an error message.
	 */
	public static void invalidIDException() {
		logger.severe("Error: Invalid ID.");
		JOptionPane.showMessageDialog(null, "Invalid ID.", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles invalid input errors by logging and displaying an error message.
	 */
	public static void invalidInputException() {
		logger.severe("Error: Invalid input.");
		JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles non-numeric input errors by logging and displaying an error message.
	 */
	public static void nonNumericValueException() {
		logger.severe("Error: User input was non numeric.");
		JOptionPane.showMessageDialog(null, "Non numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles errors where files are not found by logging and displaying an error message.
	 */
	public static void handleFileNotFound(String CONFIG_PATH) {
		logger.severe("Error: File not found." + CONFIG_PATH);
		JOptionPane.showMessageDialog(null, "File not found: " + CONFIG_PATH, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles paging errors by logging and displaying an error message.
	 */
	public static void handleNoPreviousPage() {
		logger.severe("Error: Already at first page.");
		JOptionPane.showMessageDialog(null, "Already at first page.", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	* Handles paging errors by logging and displaying an error message.
	*/
	public static void handleNoNextPage() {
		logger.severe("Error: Already at last page.");
		JOptionPane.showMessageDialog(null, "Already at last page.", "Error", JOptionPane.ERROR_MESSAGE);
	}
}