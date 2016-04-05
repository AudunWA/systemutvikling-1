package no.ntnu.iie.stud.cateringstorm;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.gui.LoginView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        LoginView loginVIew = new LoginView();
        loginVIew.setVisible(true);
    }
}
