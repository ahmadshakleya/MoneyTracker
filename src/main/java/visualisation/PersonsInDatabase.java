package visualisation;

import database.PersonDB;
import person.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class PersonsInDatabase extends JFrame{
    private javax.swing.JPanel JPanel;
    private JList<String> PersonList;
    private JButton addNewPersonButton;
    private JLabel PeopleLabel;

    public boolean addNewPersonButtonIsPressed = false;

    private DefaultListModel<String> personListModel;

    private PersonDB db;
    public PersonsInDatabase(PersonDB db) {
        setContentPane(JPanel);

        this.db = db;

        setTitle("Persons in Database");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        personListModel = new DefaultListModel<>();
        // Create JList and set its model to the DefaultListModel
        PersonList = new JList<>(personListModel);

        // Populate the list with persons from the database
        updatePersonList();


        // Set layout for the frame
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        // Add the JList to the frame
        JScrollPane scrollPane = new JScrollPane(PersonList);
        getContentPane().add(scrollPane);

        setVisible(true);

    }

    public void GotoNext(){
        addNewPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewPersonButtonIsPressed = true;
            }
        });
    }


    public void updatePersonList() {
        personListModel.clear(); // Clear the list model first

        // Get persons from the database and add their names to the list model
        for (Person personName : db.showPeople().values()) {
            personListModel.addElement(personName.getName());
        }
    }


}
