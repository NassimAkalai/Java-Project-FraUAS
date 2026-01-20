package gui;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel for selecting a drone by ID.
 * Contains a search field, buttons for selection and navigation, and a message area.
 */
public class DroneSelectionPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField searchField;
	private JTextArea messageArea;
	private JButton selectButton;
	private JButton skipButton;
	private JButton backButton;
	private JButton firstPageButton;  // Button to go to first page
	private JButton previousButton;  // Button for previous page
	private JButton nextButton;      // Button for next page
	private JButton lastPageButton;  // Button to go to last page
	private int currentOffset = 0;   // Track current offset
	private int totalCount = 0;      // Track total number of drones

	/**
	 * Constructor to initialize the DroneSelectionPanel.
	 */
	public DroneSelectionPanel() {
		setLayout(new BorderLayout(10, 10));
		add(createInputPanel(), BorderLayout.NORTH);
		add(createMessageScrollPane(), BorderLayout.CENTER);
		add(createNavigationPanel(), BorderLayout.SOUTH);
	}

	/**
	 * Creates the input panel with the search field and selection buttons.
	 */
	private JPanel createInputPanel() {
		JPanel inputPanel = new JPanel(new FlowLayout());
		searchField = new JTextField(10);
		selectButton = new JButton("Select");
		skipButton = new JButton("Skip");

		inputPanel.add(new JLabel("Enter Drone ID:"));
		inputPanel.add(searchField);
		inputPanel.add(selectButton);
		inputPanel.add(skipButton);

		return inputPanel;
	}

	/**
	 * Creates the scroll pane for the message area.
	 */
	private JScrollPane createMessageScrollPane() {
		messageArea = new JTextArea(10, 30);
		messageArea.setEditable(false);
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		return new JScrollPane(messageArea);
	}

	/**
	 * Creates the navigation panel with Previous, Back, and Next buttons.
	 */
	private JPanel createNavigationPanel() {
		JPanel navigationPanel = new JPanel(new FlowLayout());
		firstPageButton = new JButton("First Page");
		previousButton = new JButton("Previous");
		nextButton = new JButton("Next");
		lastPageButton = new JButton("Last Page");
		backButton = new JButton("Back");

		navigationPanel.add(firstPageButton);
		navigationPanel.add(previousButton);
		navigationPanel.add(nextButton);
		navigationPanel.add(lastPageButton);
		navigationPanel.add(backButton);

		return navigationPanel;
	}

	/**
	 * Returns the search field.
	 *
	 * @return The JTextField for entering the drone ID.
	 */
	public JTextField getSearchField() {
		return searchField;
	}

	/**
	 * Returns the message area.
	 *
	 * @return The JTextArea for displaying messages.
	 */
	public JTextArea getMessageArea() {
		return messageArea;
	}

	/**
	 * Returns the select button.
	 *
	 * @return The JButton for selecting the drone.
	 */
	public JButton getSelectButton() {
		return selectButton;
	}

	/**
	 * Returns the skip button.
	 *
	 * @return The JButton for skipping the selection.
	 */
	public JButton getSkipButton() {
		return skipButton;
	}

	/**
	 * Returns the back button.
	 *
	 * @return The JButton for going back to the main menu.
	 */
	public JButton getBackButton() {
		return backButton;
	}

	/**
	 * Returns the previous button.
	 *
	 * @return The JButton for going to the previous page.
	 */
	public JButton getPreviousButton() {
		return previousButton;
	}

	/**
	 * Returns the next button.
	 *
	 * @return The JButton for going to the next page.
	 */
	public JButton getNextButton() {
		return nextButton;
	}

	/**
	 * Returns the next button.
	 *
	 * @return The JButton for going to the last page.
	 */
	public JButton getLastPageButton() { 
		return lastPageButton; }

	/**
	 * Returns the next button.
	 *
	 * @return The JButton for going to the first page.
	 */
	public JButton getFirstPageButton() { 
		return firstPageButton; }

	/**
	 * Returns the next button.
	 *
	 * @return The current offset for switching the page.
	 */
	public int getCurrentOffset() { 
		return currentOffset; }

	public void setCurrentOffset(int offset) { 
		this.currentOffset = offset; }

	/**
	 * Returns the next button.
	 *
	 * @return The total count of the existing drones.
	 */
	public int getTotalCount() { 
		return totalCount; }


	public void setTotalCount(int count) { 
		this.totalCount = count; }
}