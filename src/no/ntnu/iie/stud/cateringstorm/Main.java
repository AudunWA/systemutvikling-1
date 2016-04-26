package no.ntnu.iie.stud.cateringstorm;

import com.alee.laf.WebLookAndFeel;
import no.ntnu.iie.stud.cateringstorm.gui.LoginView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class Main {

    /**
     * The main entry point of the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Makes the GUI same style as current OS :)
        //try {
        //    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //} catch (Exception e) {
        //    System.out.println("Failed to set OS GUI style, now using default style.");
        //}

        WebLookAndFeel.install();

        LoginView loginVIew = new LoginView();
        loginVIew.setVisible(true);
        loginVIew.setLocationRelativeTo(null);
    }
}
