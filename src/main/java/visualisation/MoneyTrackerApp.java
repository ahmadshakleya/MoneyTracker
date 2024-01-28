package visualisation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

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
    private JPanel personsInDatabasePanel;
    private JPanel addPersonToDatabasePanel;
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
                // Find the index of the "Ticket Management" tab
                int index = findTabIndex("Ticket Management");
                if (index != -1) {
                    // Get the TicketManagementPanel and update the ticket list
                    TicketManagementPanel ticketManagementPanel = (TicketManagementPanel) tabbedPane.getComponentAt(index);
                    ticketManagementPanel.updateTicketList(moneyTrackerController.getTicketsDB().getAllTickets());
                } else {
                    System.err.println("Ticket Management tab not found.");
                }
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

    // Method to find the index of a tab by its title
    private int findTabIndex(String title) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(title)) {
                return i;
            }
        }
        return -1; // Tab not found
    }
    private void performCleanupAndExit() {
        // Perform any cleanup operations here...

        // Exit the application
        System.exit(0);
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        addPersonToDatabasePanel = new AddPersonToDatabasePanel(moneyTrackerController);
        tabbedPane.addTab("Add Person to Database", addPersonToDatabasePanel);

        personsInDatabasePanel = new PersonsInDatabasePanel();
        tabbedPane.addTab("Persons in Database", personsInDatabasePanel);

        // Add a new tab for ticket management
        ticketManagementPanel = new TicketManagementPanel(ticketFactoryMaker);
        tabbedPane.addTab("Ticket Management", ticketManagementPanel);

        // Add the new tab for person debts
        PersonDebtsPanel personDebtsPanel = new PersonDebtsPanel(moneyTrackerController);
        tabbedPane.addTab("Person Debts", personDebtsPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MoneyTrackerApp::new);
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

        // Add a key listener to the text field
        enterNameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        addPersonToDB();
                    } catch (PersonAlreadyExists ex) {
                        JOptionPane.showMessageDialog(AddPersonToDatabasePanel.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(AddPersonToDatabasePanel.this, "An error occurred while adding the person to the database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

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
                //throw new RuntimeException(e);
                JOptionPane.showMessageDialog(this, "Person added to database: " + enteredName);
                enterNameTextField.setText("");
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
    private MoneyTrackerController moneyTrackerController;

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
                    ticketDetailsTextArea.setText(selectedTicket.toOwnString());
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
        createUnevenTicketButton.addActionListener(e -> {
            try {
                // Get the list of people from the database
                ArrayList<Person> peopleList = ticketFactoryMaker.getController().getAllPeople();

                // Create an array of person names for display in the dialog
                String[] personNames = peopleList.stream().map(Person::getName).toArray(String[]::new);

                // Show a dialog to select the tag
                String[] tagNames = Arrays.stream(Tag.values()).map(Enum::name).toArray(String[]::new);
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

                // Prompt the user to input the payer's personal contribution
                String payerContributionInput = JOptionPane.showInputDialog(this, "Enter the payer's personal contribution:");
                double payerPersonalContribution = Double.parseDouble(payerContributionInput);

                // Validate payer's personal contribution
                if (payerPersonalContribution < 0) {
                    throw new IllegalArgumentException("Payer's personal contribution cannot be negative.");
                }

                // Create a HashMap to hold the repayment map
                HashMap<Person, Double> repaymentMap = new HashMap<>();

                // Loop through each person to input their repayment amount
                for (Person person : peopleList) {
                    String repaymentInput = JOptionPane.showInputDialog(this, "Enter the repayment amount for " + person.getName() + ":");
                    double repaymentAmount = Double.parseDouble(repaymentInput);
                    if (repaymentAmount < 0) {
                        throw new IllegalArgumentException("Repayment amount for " + person.getName() + " cannot be negative.");
                    }
                    repaymentMap.put(person, repaymentAmount);
                }

                // Create the TicketFactoryUnevenSplit instance
                TicketFactoryUnevenSplit unevenSplitFactory = ticketFactoryMaker.makeUnevenTicketFactory();

                // Use the unevenSplitFactory to create an uneven ticket
                unevenSplitFactory.makeUnevenTicket(peopleList.get(0), payerPersonalContribution, repaymentMap, tag, description);

                // Handle successful creation of the uneven ticket
                JOptionPane.showMessageDialog(this, "Uneven ticket created successfully.");
            } catch (NumberFormatException ex) {
                // Handle invalid number format input
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                // Handle invalid tag selection or negative amounts
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Handle other exceptions
                ex.printStackTrace(); // Print the stack trace to identify the specific exception
                JOptionPane.showMessageDialog(this, "An error occurred while creating the uneven ticket.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



        buttonPanel.add(createEvenTicketButton);
        buttonPanel.add(createUnevenTicketButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load tickets from ticketsDB when the panel is created
        updateTicketList(ticketFactoryMaker.getController().getTicketsDB().getAllTickets());
    }
    // Method to update ticket list in the UI
    public void updateTicketList(ArrayList<ITicket> tickets) {
        ticketListModel.clear();
        for (ITicket ticket : tickets) {
            ticketListModel.addElement(ticket);
        }
    }

}

class PersonDebtsPanel extends JPanel {
    private JLabel titleLabel;
    private JList<String> personDebtsList;
    private DefaultListModel<String> personDebtsListModel;

    private MoneyTrackerController moneyTrackerController;

    public PersonDebtsPanel(MoneyTrackerController moneyTrackerController) {
        this.moneyTrackerController = moneyTrackerController;
        initComponents();
        moneyTrackerController.addTicketsDBObserver(evt -> updatePersonDebtsList());
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        titleLabel = new JLabel("Person Debts:");
        add(titleLabel, BorderLayout.NORTH);

        personDebtsListModel = new DefaultListModel<>();
        personDebtsList = new JList<>(personDebtsListModel);
        JScrollPane scrollPane = new JScrollPane(personDebtsList);
        add(scrollPane, BorderLayout.CENTER);

        updatePersonDebtsList();
    }

    private void updatePersonDebtsList() {
        personDebtsListModel.clear();
        // Get the global bill
        HashMap<Person, HashMap<Person, Double>> globalBill = moneyTrackerController.getGlobalBill();
        // Iterate over each person and their debts
        for (Map.Entry<Person, HashMap<Person, Double>> entry : globalBill.entrySet()) {
            Person person = entry.getKey();
            HashMap<Person, Double> debts = entry.getValue();
            StringBuilder debtString = new StringBuilder(person.getName() + " owes: ");
            // Append each creditor and the amount owed
            for (Map.Entry<Person, Double> debtEntry : debts.entrySet()) {
                Person creditor = debtEntry.getKey();
                Double amountOwed = debtEntry.getValue();
                debtString.append(creditor.getName()).append(" - ").append(amountOwed).append(", ");
            }
            personDebtsListModel.addElement(debtString.toString());
        }
    }
}