package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;
import no.ntnu.iie.stud.cateringstorm.util.ResourceUtil;

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
    private JLabel employeeTypeLabel;

    public HomeView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        welcomeLabel.setText(welcomeLabel.getText().replace("%name%", GlobalStorage.getLoggedInEmployee().getForename()));
        employeeTypeLabel.setText("Your are " + getEmployeeTypeString() + ".");
        salaryLabel.setText("Salary so far (2016): " + EmployeeFactory.getSalarySoFar(GlobalStorage.getLoggedInEmployee().getEmployeeId(), Date.valueOf(LocalDate.now())) + ",- NOK");
    }

    /**
     Returns a string representation of the logged in employee's employee type. Used for displaying it for the employee.
     * @return a string representation of the logged in employee's employee type.
     */
    private String getEmployeeTypeString() {
        switch (GlobalStorage.getLoggedInEmployee().getEmployeeType()) {

            case EMPLOYEE:
                return "an employee";
            case CHEF:
                return "a chef";
            case CHAUFFEUR:
                return "a chauffeur";
            case NUTRITION_EXPERT:
                return "a nutrition expert";
            case ADMINISTRATOR:
                return "an administrator";
            case SALESPERSON:
                return "a salesperson";
            default:
                return "nothing, sorry.";
        }
    }

    private void createImage() {
        image = new JPanel(true);
        image.add(new JLabel(ResourceUtil.getIconResource("CSChefTekst.png")));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createImage();
    }
}
