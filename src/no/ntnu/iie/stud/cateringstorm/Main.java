package no.ntnu.iie.stud.cateringstorm;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.gui.LoginView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        // Makes the GUI same style as current OS :)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Failed to set OS GUI style, now using default style.");
        }

        LoginView loginVIew = new LoginView();
        loginVIew.setVisible(true);
        loginVIew.setLocationRelativeTo(null);
    }
}
