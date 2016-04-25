package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;

/**
 * GUI for the employee home screen.
 * Displays useful information and options for the employee.
 */
public class HomeView extends JPanel {
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JPanel image;
    private JLabel salaryLabel;

    public HomeView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        welcomeLabel.setText(welcomeLabel.getText().replace("%name%", GlobalStorage.getLoggedInEmployee().getForename()));
        salaryLabel.setText("Salary so far (2016): " + EmployeeFactory.getSalarySoFar(GlobalStorage.getLoggedInEmployee().getEmployeeId(), Date.valueOf(LocalDate.now())) + ",- NOK");
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
