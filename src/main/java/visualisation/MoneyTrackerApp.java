package visualisation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import controller.MoneyTrackerController;
import database.PersonDB;
import exceptions.PersonAlreadyExists;
import exceptions.ticketException;
import factory.PersonFactory;
import factory.TicketFactoryEvenSplit;
import factory.TicketFactoryMaker;
import factory.TicketFactoryUnevenSplit;
import person.Person;
import tag.Tag;
import tickets.ITicket;


public class MoneyTrackerApp extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel showPossibilitiesPanel;
    private JPanel personsInDatabasePanel;
    private JPanel addPersonToDatabasePanel;
    private JPanel personTicketsPanel; // New panel for displaying person's tickets
    private JPanel ticketManagementPanel;

    private MoneyTrackerController moneyTrackerController; // Instantiate MoneyTrackerController
    private TicketFactoryMaker ticketFactoryMaker; // Instantiate TicketFactoryMaker

    public MoneyTrackerApp() {
        setTitle("Money Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Initialize MoneyTrackerController and TicketFactoryMaker
        moneyTrackerController = new MoneyTrackerController();
        ticketFactoryMaker = new TicketFactoryMaker(moneyTrackerController);

        initComponents();
        setContentPane(tabbedPane);

        setVisible(true);

        // Listen for property change events from MoneyTrackerController
        moneyTrackerController.addTicketsDBObserver(evt -> {
            // Check if the property change is related to adding a new ticket
            if ("ticketAdded".equals(evt.getPropertyName())) {
                // A new ticket was added, update the ticket list
                TicketManagementPanel ticketManagementPanel = (TicketManagementPanel) tabbedPane.getComponentAt(4); // Assuming the ticket management panel is at index 4
                ticketManagementPanel.updateTicketList(moneyTrackerController.getTicketsDB().getAllTickets());
            }
        });

        // Add window listener to handle window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Call a method to perform any necessary cleanup before closing the application
                performCleanupAndExit();
            }
        });
    }
    private void performCleanupAndExit() {
        // Perform any cleanup operations here...

        // Exit the application
        System.exit(0);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        showPossibilitiesPanel = new ShowPossibilitiesPanel();
        tabbedPane.addTab("Show Possibilities", showPossibilitiesPanel);

        personsInDatabasePanel = new PersonsInDatabasePanel();
        tabbedPane.addTab("Persons in Database", personsInDatabasePanel);

        personTicketsPanel = new PersonTicketsPanel(moneyTrackerController); // Pass controller to panel
        tabbedPane.addTab("Person Tickets", personTicketsPanel);

        addPersonToDatabasePanel = new AddPersonToDatabasePanel(moneyTrackerController);
        tabbedPane.addTab("Add Person to Database", addPersonToDatabasePanel);

        // Add a new tab for ticket management
        ticketManagementPanel = new TicketManagementPanel(ticketFactoryMaker);
        tabbedPane.addTab("Ticket Management", ticketManagementPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MoneyTrackerApp::new);
    }
}

class ShowPossibilitiesPanel extends JPanel {
    private JButton showPersonsInDatabaseButton;

    private boolean showPersons = false;

    public ShowPossibilitiesPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        showPersonsInDatabaseButton = new JButton("Show Persons in Database");
        showPersonsInDatabaseButton.addActionListener(e -> {
            showPersons = true;
            JOptionPane.showMessageDialog(this, "Displaying persons in the database.");
        });
        add(showPersonsInDatabaseButton, BorderLayout.CENTER);
    }

    public boolean isShowPersons() {
        return showPersons;
    }
}

class PersonsInDatabasePanel extends JPanel {
    private JLabel peopleLabel;
    private JList<String> personList;
    private DefaultListModel<String> personListModel;

    private PersonDB db;

    public PersonsInDatabasePanel() {
        initComponents();
        // Register a property change listener to the PersonDB
        db.addObserver(evt -> {
            // Check if the property change is related to adding a new person
            if ("people who are effected: ".equals(evt.getPropertyName())) {
                // Update the person list in the UI
                updatePersonList();
            }
        });
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        db = PersonDB.getInstance();
        personListModel = new DefaultListModel<>();
        personList = new JList<>(personListModel);
        updatePersonList();
        JScrollPane scrollPane = new JScrollPane(personList);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void updatePersonList() {
        personListModel.clear();
        for (Person person : db.getAllPeople()) {
            personListModel.addElement(person.getName());
        }
    }
}

class PersonTicketsPanel extends JPanel {
    private JList<String> ticketList;
    private DefaultListModel<String> ticketListModel;
    private JLabel totalDebtLabel; // New label to display total debt

    private MoneyTrackerController moneyTrackerController;

    public PersonTicketsPanel(MoneyTrackerController moneyTrackerController) {
        initComponents();
        this.moneyTrackerController = moneyTrackerController;

        // Listen for property change events from PersonDB
        moneyTrackerController.addPersonDBObserver(evt -> {
            if ("personAdded".equals(evt.getPropertyName())) {
                // A new person was added, update the ticket list
                Person newPerson = ((ArrayList<Person>) evt.getNewValue()).get(0);
                updateTicketList(newPerson);
            }
        });
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        ticketListModel = new DefaultListModel<>();
        ticketList = new JList<>(ticketListModel);
        JScrollPane scrollPane = new JScrollPane(ticketList);
        add(scrollPane, BorderLayout.CENTER);

        totalDebtLabel = new JLabel("Total Debt: $0.00");
        add(totalDebtLabel, BorderLayout.SOUTH);
    }

    public void updateTicketList(Person person) {
        ticketListModel.clear();
        ArrayList<ITicket> tickets = moneyTrackerController.getTicketsForPerson(person);
        double totalDebt = 0.0;
        for (ITicket ticket : tickets) {
            ticketListModel.addElement(ticket.getDescription());
            totalDebt += ticket.getTotal(); // Accumulate total debt
        }
        totalDebtLabel.setText("Total Debt: $" + String.format("%.2f", totalDebt));
    }
}



class AddPersonToDatabasePanel extends JPanel {
    private JButton addPersonButton;
    private JTextField enterNameTextField;

    private MoneyTrackerController moneyTrackerController;

    public AddPersonToDatabasePanel(MoneyTrackerController moneyTrackerController) {
        this.moneyTrackerController = moneyTrackerController;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        enterNameTextField = new JTextField(20);
        add(new JLabel("Enter Name:"), BorderLayout.WEST);
        add(enterNameTextField, BorderLayout.CENTER);

        addPersonButton = new JButton("Add Person");
        addPersonButton.addActionListener(e -> {
            try {
                addPersonToDB();
            } catch (PersonAlreadyExists ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An error occurred while adding the person to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(addPersonButton, BorderLayout.SOUTH);
    }

    private void addPersonToDB() throws PersonAlreadyExists {
        String enteredName = enterNameTextField.getText();
        if (!enteredName.isEmpty()) {
            try {
                // Call the makePerson method of MoneyTrackerController to add the person
                moneyTrackerController.makePerson(enteredName);
                JOptionPane.showMessageDialog(this, "Person added to database: " + enteredName);
                enterNameTextField.setText("");
            } catch (PersonAlreadyExists ex) {
                throw ex; // Re-throw the exception
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a name.");
        }
    }
}

class TicketManagementPanel extends JPanel {
    private TicketFactoryMaker ticketFactoryMaker;
    private DefaultListModel<ITicket> ticketListModel;
    private JList<ITicket> ticketList;
    private JTextArea ticketDetailsTextArea;

    public TicketManagementPanel(TicketFactoryMaker ticketFactoryMaker) {
        this.ticketFactoryMaker = ticketFactoryMaker;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel to display the list of tickets
        JPanel ticketListPanel = new JPanel(new BorderLayout());
        ticketListModel = new DefaultListModel<>();
        ticketList = new JList<>(ticketListModel);
        JScrollPane scrollPane = new JScrollPane(ticketList);
        ticketListPanel.add(scrollPane, BorderLayout.CENTER);
        add(ticketListPanel, BorderLayout.WEST);

        // Panel to display ticket details
        JPanel detailsPanel = new JPanel(new BorderLayout());
        JLabel detailsLabel = new JLabel("Ticket Details:");
        detailsPanel.add(detailsLabel, BorderLayout.NORTH);
        ticketDetailsTextArea = new JTextArea(10, 30);
        ticketDetailsTextArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(ticketDetailsTextArea);
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.CENTER);

        // Add a listener to update ticket details when a ticket is selected
        ticketList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                ITicket selectedTicket = ticketList.getSelectedValue();
                if (selectedTicket != null) {
                    ticketDetailsTextArea.setText(selectedTicket.toString());
                }
            }
        });

        // Button panel to create tickets
        JPanel buttonPanel = new JPanel();
        JButton createEvenTicketButton = new JButton("Create Even Ticket");
        createEvenTicketButton.addActionListener(e -> {
            try {
                // Get the list of people from the database
                ArrayList<Person> peopleList = ticketFactoryMaker.getController().getAllPeople();

                // Create an array of person names for display in the dialog
                String[] personNames = peopleList.stream().map(Person::getName).toArray(String[]::new);

                // Show a dialog to select the person who has paid
                String selectedPersonName = (String) JOptionPane.showInputDialog(
                        this,
                        "Select the person who has paid:",
                        "Select Person",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        personNames,
                        personNames[0]);

                // Find the selected person object based on the selected name
                Person whoHasPaid = peopleList.stream()
                        .filter(person -> person.getName().equals(selectedPersonName))
                        .findFirst()
                        .orElseThrow(() -> new Exception("Selected person not found in the database"));

                // Create an array of tag names for display in the dialog
                String[] tagNames = Arrays.stream(Tag.values()).map(Enum::name).toArray(String[]::new);

                // Show a dialog to select the tag
                String selectedTagName = (String) JOptionPane.showInputDialog(
                        this,
                        "Select the tag:",
                        "Select Tag",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        tagNames,
                        tagNames[0]);

                // Convert the selected tag name to the Tag enum
                Tag tag = Tag.valueOf(selectedTagName);

                // Prompt the user to input the description
                String description = JOptionPane.showInputDialog(this, "Enter the description:");

                // Prompt the user to input the total amount
                String totalInput = JOptionPane.showInputDialog(this, "Enter the total amount:");
                double total = Double.parseDouble(totalInput);

                // Get other necessary information for creating the even ticket
                // Set<Person> people = ...; // Get the set of people involved (Optional: you may add a dialog to select multiple people)
                // String description = ...; // Get the description

                // Create the TicketFactoryEvenSplit instance
                TicketFactoryEvenSplit evenSplitFactory = ticketFactoryMaker.makeEvenTicketFactory();

                // Use the evenSplitFactory to create an even ticket
                evenSplitFactory.makeEvenTicket(whoHasPaid, total, new HashSet<>(peopleList), tag, description);

                // Handle successful creation of the even ticket
                JOptionPane.showMessageDialog(this, "Even ticket created successfully.");
            } catch (NumberFormatException ex) {
                // Handle invalid number format input
                JOptionPane.showMessageDialog(this, "Invalid total amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                // Handle invalid tag selection
                JOptionPane.showMessageDialog(this, "Invalid tag selection.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ticketException ex) {
                // Handle ticket exception
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Handle other exceptions
                JOptionPane.showMessageDialog(this, "An error occurred while creating the even ticket.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        JButton createUnevenTicketButton = new JButton("Create Uneven Ticket");
        /*createUnevenTicketButton.addActionListener(e -> {
            try {
                TicketFactoryUnevenSplit unevenSplitFactory = ticketFactoryMaker.makeUnevenTicketFactory();
                // Assuming you have access to the necessary information to create an uneven ticket
                HashMap<Person, Double> repaymentMap = ...; // Get the repayment map
                double payerPersonalContribution = ...; // Get the payer's personal contribution
                String description = ...; // Get the description
                unevenSplitFactory.makeUnevenTicket(repaymentMap, payerPersonalContribution, description);
                // Handle successful creation of the uneven ticket
                JOptionPane.showMessageDialog(this, "Uneven ticket created successfully.");
            } catch (Exception ex) {
                // Handle exception
                JOptionPane.showMessageDialog(this, "An error occurred while creating the uneven ticket.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });*/

        buttonPanel.add(createEvenTicketButton);
        buttonPanel.add(createUnevenTicketButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    // Method to update ticket list in the UI
    public void updateTicketList(ArrayList<ITicket> tickets) {
        ticketListModel.clear();
        for (ITicket ticket : tickets) {
            ticketListModel.addElement(ticket);
        }
    }

}