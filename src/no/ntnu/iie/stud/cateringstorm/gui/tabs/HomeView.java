package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;

import javax.swing.*;

/**
 * GUI for the employee home screen.
 * Displays useful information and options for the employee.
 * Created by Audun on 01.04.2016.
 */
public class HomeView extends JPanel {
    private Employee employee;

    private JPanel mainPanel;
    private JLabel welcomeLabel;

    public HomeView(Employee employee) {
        this.employee = employee;
        add(mainPanel);
        welcomeLabel.setText(welcomeLabel.getText().replace("%name%", employee.getForename()) + "\uD83D\uDE17");
    }
}
