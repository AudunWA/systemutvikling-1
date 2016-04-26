package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.util.ResourceUtil;

import javax.swing.*;

/**
 * GUI for the employee login window
 */
public class LoginView extends JFrame {
    private static final String WINDOW_TITLE = "Healthy Catering login";

    // Window dimensions
    private static final int WIDTH = 325;
    private static final int HEIGHT = 150;

    private JPanel mainPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton loginButton;

    public LoginView() {
        ResourceUtil.setApplicationIcon(this);
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        getRootPane().setDefaultButton(loginButton);

        loginButton.addActionListener(e -> onLoginClick());
    }

    /***
     * Test program
     *
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
        loginWindow.setLocationRelativeTo(null);
    }

    /**
     * Called when login button has been pressed
     */
    private void onLoginClick() {
        // Handle login attempt
        String usernameInput = usernameField.getText();
        String passwordInput = new String(passwordField.getPassword());

        Employee employee = EmployeeFactory.getEmployee(usernameInput, passwordInput);
        if (employee == null || (!employee.isActive() && employee.getEmployeeType() != EmployeeType.ADMINISTRATOR)) {
            // Login failed
            Toast.makeText(this, "Login attempt failed!", Toast.Style.ERROR).display();
            return;
        } else {
            usernameField.setEnabled(false);
            passwordField.setEnabled(false);
            loginButton.setEnabled(false);

            JOptionPane.showMessageDialog(this, "Hello, " + employee.getFullName() + "!");
            employee.onSuccessfulLogin();
            setVisible(false);
            dispose();
        }
    }
}
