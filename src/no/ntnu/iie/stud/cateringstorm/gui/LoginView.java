package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;

import javax.swing.*;

/**
 * GUI for the employee login window
 * Created by Audun on 09.03.2016.
 */
public class LoginView extends JFrame {
    private static final String WINDOW_TITLE = "LoginView";

    // Window dimensions
    private static final int WIDTH = 300;
    private static final int HEIGHT = 250;

    private JPanel mainPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton loginButton;

    public LoginView() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(e -> {
            // Handle login attempt
            String usernameInput = usernameField.getText();
            String passwordInput = new String(passwordField.getPassword());

            Employee employee = EmployeeFactory.newEmployee(usernameInput, passwordInput);
            if (employee == null) {
                // Login failed
                JOptionPane.showMessageDialog(this, "Login attempt failed!");
                return;
            } else {
                JOptionPane.showMessageDialog(this, "Hello, " + employee.getFullName() + "!");
                employee.onSuccessfulLogin();
                setVisible(false);
                dispose();
            }
        });
        pack();
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
    }

    /***
     * Test program
     * @param args
     */
    public static void main(String[] args) {
        // Makes the GUI same style as current OS :)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Failed to set OS GUI style, now using default style.");
        }
        LoginView loginWindow = new LoginView();
        loginWindow.setVisible(true);
    }
}
