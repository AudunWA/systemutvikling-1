package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;

import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * Control panel UI for all employee
 * Created by Audun on 09.03.2016.
 */

public class EmployeeView extends JFrame {
    private static final String WINDOW_TITLE = "Employee";

    // Window dimensions
    private static final int WIDTH = 500;
    private static final int HEIGHT = 250;
    private JPanel mainPanel;
    private JButton chefButton;
    private JButton chauffeurButton;
    private JButton salespersonButton;
    private JButton administratorButton;
    private JButton expertButton;
    private JLabel userTextLabel;

    // Data
    private Employee employee;

    public EmployeeView(Employee employee) {
        this.employee = employee;

        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        displayButtons();

        userTextLabel.setText("Welcome back, " + employee.getFullName());

        pack();
    }

    /**
     * Displays the appropiate access buttons for the employee
     */
    private void displayButtons() {
        // First hide everything employee specific
        chefButton.setVisible(false);
        chauffeurButton.setVisible(false);
        expertButton.setVisible(false);
        salespersonButton.setVisible(false);
        administratorButton.setVisible(false);

        switch (employee.getEmployeeType()) {
            case EMPLOYEE:
                break;
            case CHEF:
                chefButton.setVisible(true);
                break;
            case CHAUFFEUR:
                chauffeurButton.setVisible(true);
                break;
            case NUTRITION_EXPERT:
                expertButton.setVisible(true);
                break;
            case ADMINISTRATOR:
                chefButton.setVisible(true);
                chauffeurButton.setVisible(true);
                expertButton.setVisible(true);
                salespersonButton.setVisible(true);
                administratorButton.setVisible(true);
                break;
            case SALESPERSON:
                salespersonButton.setVisible(true);
                break;
        }
    }

    /***
     * Test program
     * @param args
     */
    public static void main(String[] args) {
        EmployeeView view = new EmployeeView(new Employee(-1, "Test", "Forename", "Surname", "Address",
                "Phone", "Email", EmployeeType.ADMINISTRATOR));
        view.setVisible(true);
    }
}
