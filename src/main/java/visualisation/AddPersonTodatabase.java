package visualisation;

import database.PersonDB;
import exceptions.PersonAlreadyExists;
import person.Person;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPersonTodatabase extends JFrame{
    private JButton addPersonButton;
    private JTextField enterNameTextField;
    private JPanel panel;

    private PersonDB db;

    public boolean AddPersonButtonIsPressed = false;

    public AddPersonTodatabase(PersonDB db) {
        setContentPane(panel);
        this.db = db;

        setTitle("Add Person to Database");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addPersonToDB();
                } catch (PersonAlreadyExists ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        setVisible(true);
    }

    private void addPersonToDB() throws PersonAlreadyExists {
        String enteredName = enterNameTextField.getText();
        if (!enteredName.isEmpty()) {
            Person newPerson = new Person(enteredName);
            db.addEntry(newPerson);
            JOptionPane.showMessageDialog(this, "Person added to database: " + enteredName);
            enterNameTextField.setText(""); // Clear the text field after adding
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a name.");
        }
    }

    public void GoToNext() {
        addPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPersonButtonIsPressed = true;
            }
        });
    }
}
