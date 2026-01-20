package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel for displaying paginated data and navigation controls.
 * Contains buttons for navigating between pages and a panel for displaying data.
 */
public class PagingSystemPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel dataPanel; // Panel to display the data
    private JButton previousButton; // Button to navigate to the previous page
    private JButton nextButton; // Button to navigate to the next page
    private JButton backButton; // Button to go back to the main menu

    /**
     * Constructor to initialize the PagingSystemPanel.
     * Sets up the layout and adds components.
     */
    public PagingSystemPanel() {
        setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanelPaging = new JPanel(new BorderLayout(10, 10));

        // Data Display Panel (5 Squares)
        dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(1, 5, 10, 10));

        // Create five squared panels for later information display
        for (int i = 0; i < 5; i++) {
            JPanel square = new JPanel();
            square.setBackground(Color.LIGHT_GRAY);
            square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            dataPanel.add(square);
        }
        mainPanelPaging.add(dataPanel, BorderLayout.CENTER);

        // Navigation Buttons Panel
        JPanel navigationPanel = new JPanel(); 
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        navigationPanel.add(previousButton);
        navigationPanel.add(nextButton);
        mainPanelPaging.add(navigationPanel, BorderLayout.NORTH);

        // Back Button Panel (Bottom Right)
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back");
        backPanel.add(backButton);
        mainPanelPaging.add(backPanel, BorderLayout.SOUTH);

        add(mainPanelPaging);
    }

    /**
     * Returns the data panel.
     *
     * @return The JPanel used to display data.
     */
    public JPanel getDataPanel() {
        return dataPanel;
    }

    /**
     * Returns the previous button.
     *
     * @return The JButton for navigating to the previous page.
     */
    public JButton getPreviousButton() {
        return previousButton;
    }

    /**
     * Returns the next button.
     *
     * @return The JButton for navigating to the next page.
     */
    public JButton getNextButton() {
        return nextButton;
    }

    /**
     * Returns the back button.
     *
     * @return The JButton for going back to the main menu.
     */
    public JButton getBackButton() {
        return backButton;
    }
}