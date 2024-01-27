package visualisation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPossibilities extends JFrame {
    private JButton addNewPersonToButton;
    private JButton showsPersonsInDatabaseButton;
    private JButton addNewTicketButton;
    private javax.swing.JPanel PossibilitiesPanel;

    private boolean showPersons = false;

    public ShowPossibilities() {
        setContentPane(PossibilitiesPanel);

        //PossibilitiesPanel.add(Title);

        setTitle("Welcome");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void showPersons() {
        showsPersonsInDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPersons = true;
                System.out.println("This are the people in the Database");
            }
        });
    }

    public Boolean getShowPersons() {
        return showPersons;
    }


}
