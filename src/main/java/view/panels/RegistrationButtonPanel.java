package view.panels;

import TicketsDBController.TicketsDBController;
import person.Person;
import factory.PersonFactory;

import javax.swing.*;

public class RegistrationButtonPanel extends JPanel {

    private JButton addTicket;
    private JButton removeTicket;

    // Get your controller in this private field
    private TicketsDBController t_controller;

    // For now, just make a new person in this class via your factory.
    // You can change this later on to a more unified way
    private Person person;

    // Get your controller in this class via the constructor
    public RegistrationButtonPanel(TicketsDBController t_controller)
    {
        this.t_controller = t_controller;
        JLabel label = new JLabel("Registration buttons");
        this.addTicket = new JButton("Add ticket");
        this.removeTicket = new JButton("Remove ticket"); // Misschien gaan we dit automatisch doen, als een ticket is betaald.

        // Create your temporary person here
        PersonFactory personFactory = new PersonFactory();
        //this.person = personFactory.getPerson(1, "Test", "Programmer");
        addTicketButtonActionListener();
        addCheckOutButtonActionListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(label);
        this.add(this.addTicket);
        this.add(this.removeTicket);
    }

    public void addTicketButtonActionListener()
    {
        this.addTicket.addActionListener(listener ->
        {
            // Insert here your controller functionality
            t_controller.addTicket(person, null);
        });
    }

    public void addCheckOutButtonActionListener()
    {
        this.removeTicket.addActionListener(listener ->
        {
            // Insert here your controller functionality
            t_controller.addTicket(person, null);
        });
    }





}
