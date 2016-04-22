package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
import java.awt.*;

/**
 * GUI for the employee home screen.
 * Displays useful information and options for the employee.
 * Created by Audun on 01.04.2016.
 */
public class HomeView extends JPanel {
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JPanel image;

    public HomeView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        welcomeLabel.setText(welcomeLabel.getText().replace("%name%", GlobalStorage.getLoggedInEmployee().getForename()));
    }

    private void createImage() {

        image = new JPanel(true);
        image.add(new JLabel(new ImageIcon("CSChefTekst.png")));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createImage();
    }
}
