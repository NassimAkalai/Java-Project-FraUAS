package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The main window of the application.
 * Contains a menu panel with options to execute different functionalities or exit the application.
 */
public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel mainPanel; // Main panel to hold different views
    private CardLayout cardLayout; // Layout manager for switching between views

    /**
     * Constructor to initialize the MainWindow.
     * Sets up the layout and adds the menu panel.
     */
    public MainWindow() {
        setTitle("Drone Simulation Interface");
        setSize(1100, 550);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Creates the menu panel with a combo box, execute button, and exit button.
     *
     * @return The JPanel representing the menu.
     */
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout(10, 10));
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> optionComboBox = new JComboBox<>(new String[]{"Drones", "Drone Types", "Drone Dynamics"});
        JButton executeButton = new JButton("Execute"); 
        comboBoxPanel.add(new JLabel("Select Option:"));
        comboBoxPanel.add(optionComboBox);
        comboBoxPanel.add(executeButton);

        JButton exitButton = new JButton("Exit");
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.add(exitButton);

        menuPanel.add(comboBoxPanel, BorderLayout.NORTH);
        menuPanel.add(exitPanel, BorderLayout.SOUTH);

        return menuPanel;
    }

    /**
     * Adds an action listener to the execute button.
     *
     * @param listener The ActionListener to be added.
     */
    public void addActionListenerToExecuteButton(ActionListener listener) {
        JPanel menuPanel = (JPanel) mainPanel.getComponent(0);
        JPanel comboBoxPanel = (JPanel) menuPanel.getComponent(0);
        JButton executeButton = (JButton) comboBoxPanel.getComponent(2);
        executeButton.addActionListener(listener);
    }

    /**
     * Adds an action listener to the exit button.
     *
     * @param listener The ActionListener to be added.
     */
    public void addActionListenerToExitButton(ActionListener listener) {
        JPanel menuPanel = (JPanel) mainPanel.getComponent(0);
        JPanel exitPanel = (JPanel) menuPanel.getComponent(1);
        JButton exitButton = (JButton) exitPanel.getComponent(0);
        exitButton.addActionListener(listener);
    }

    /**
     * Returns the selected option from the combo box.
     *
     * @return The selected option as a String.
     */
    public String getSelectedOption() {
        JPanel menuPanel = (JPanel) mainPanel.getComponent(0);
        JPanel comboBoxPanel = (JPanel) menuPanel.getComponent(0);
        JComboBox<String> optionComboBox = (JComboBox<String>) comboBoxPanel.getComponent(1);
        return (String) optionComboBox.getSelectedItem();
    }
    
    /**
     * Returns the main panel.
     *
     * @return The JPanel used as the main container.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Returns the card layout.
     *
     * @return The CardLayout used for switching views.
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }

}