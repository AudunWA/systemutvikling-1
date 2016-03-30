package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;

import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * Control panel UI for all employee
 * Created by Audun on 09.03.2016.
 */

public class EmployeeView extends JFrame {
    private static final String WINDOW_TITLE = "Employee";

    // Window dimensions
    private static final int WIDTH = 300;
    private static final int HEIGHT = 250;
    private JPanel mainPanel;
    private JButton absenceButton;
    private JTable timeTable;

    // Data
    private Employee employee;

    public EmployeeView(Employee employee) {
        this.employee = employee;

        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
    }
    private void createUIComponents(){

    }
    private void fillTimetable() {

    }
}
