package visualisation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    private JPanel mainPanel;
    private JButton btn_mainFrametoNextFrame;
    private JLabel titleField;
    private JLabel CreditsField;
    private JLabel Title;

    private boolean switchToOverView = false;

    public MainFrame() {
        setContentPane(mainPanel);

        //mainPanel.add(Title);

        setTitle("Welcome");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void GotoNext(){
        btn_mainFrametoNextFrame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToOverView = true;
                ShowPossibilities possibilitiesFrame = new ShowPossibilities();
            }
        });
    }

    public boolean isSwitchToOverView() {
        return switchToOverView;
    }

    public void run() {
        mainPanel.updateUI();
    }

    public void close() {
        dispose();
    }
}
