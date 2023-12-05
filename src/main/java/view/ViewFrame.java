package view;

import TicketsDBController.TicketsDBController;
import register_entry.RegisterEntry;
import tickets.ITicket;
import view.panels.ListPanel;
import view.panels.RegistrationButtonPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ViewFrame extends JFrame implements PropertyChangeListener {
    private TicketsDBController t_controller;
    private ListPanel panel;
    private RegistrationButtonPanel buttons;

    public ViewFrame(TicketsDBController registrationController) {
        super("Registration");
        this.t_controller = registrationController;
    }

    public void initialize() {
        // Set a look and feel for a more modern appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setSize(600, 400); // Adjusted size for a more spacious layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use a BorderLayout for a clearer structure
        this.setLayout(new BorderLayout());

        // Create a JPanel for the content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Pass the controller to the ButtonPanel
        buttons = new RegistrationButtonPanel(t_controller);
        panel = new ListPanel();

        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.add(buttons, BorderLayout.SOUTH);

        this.add(contentPanel, BorderLayout.CENTER);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        // Make the frame visible after all components are added
        this.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ITicket value1 = (ITicket) evt.getNewValue();
        System.out.println(value1.getDescription() + " is added to the database with amount: " + value1.getTotal());
    }
}
