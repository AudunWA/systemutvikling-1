package no.ntnu.iie.stud.cateringstorm.gui;

import javax.swing.*;

/**
 * GUI for the employee login window
 * Created by Audun on 09.03.2016.
 */
public class Login extends JFrame {
    private static final String WINDOW_TITLE = "Login";

    // Window dimensions
    private static final int WIDTH = 300;
    private static final int HEIGHT = 250;

    private JPanel mainPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JButton loginButton;

    public Login() {
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        getRootPane().setDefaultButton(loginButton);
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
    }
}
