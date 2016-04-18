package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Displays a map with driving routes for the chauffeur.
 * Created by Audun on 19.04.2016.
 */
public class MapView extends JPanel {
    private final String MAP_PATH = "html/map.html";

    private JPanel mainPanel;
    private Browser browser;

    public static void main(String[] args) {
        JFrame frame = new JFrame("JxBrowser - Hello World");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new MapView());
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MapView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        browser = new Browser();
        BrowserView view = new BrowserView(browser);
        browser.loadHTML(getMapHTML());
        mainPanel.add(view, BorderLayout.CENTER);
    }

    private void resetZoom() {
        browser.executeJavaScript("map.setZoom(15)");
    }

    private String getMapHTML() {
        ClassLoader loader = getClass().getClassLoader();
        if (loader == null) {
            System.err.println("Could not get class loader!");
            return null;
        }

        URL resource = loader.getResource(MAP_PATH);
        if (resource == null) {
            System.err.println("Could not get resource \"" + MAP_PATH + "\"");
            return null;
        }
        try {
            return new String(Files.readAllBytes(Paths.get(resource.toURI())));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
