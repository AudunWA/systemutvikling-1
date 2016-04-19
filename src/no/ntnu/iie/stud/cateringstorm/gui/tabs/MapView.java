package no.ntnu.iie.stud.cateringstorm.gui.tabs;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import no.ntnu.iie.stud.cateringstorm.Maps.MapBackend;
import no.ntnu.iie.stud.cateringstorm.gui.util.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Displays a map with driving routes for the chauffeur.
 * Created by Audun on 19.04.2016.
 */
public class MapView extends JPanel {
    private final String MAP_PATH = "html/map.html";

    private JPanel mainPanel;
    private JButton setDeliveredButton;
    private Browser browser;

    public static void main(String[] args) {
        ArrayList<Coordinate> addressList = new ArrayList<>();
        addressList.add(MapBackend.addressToPoint("Stibakken 2, Malvik, Norway"));
        addressList.add(MapBackend.addressToPoint("Valgrindvegen 12, Trondheim, Norway"));
        addressList.add(MapBackend.addressToPoint("Anders tvereggensveg 2, Trondheim, Norway"));
        addressList.add(MapBackend.addressToPoint("Eidsvolls gate 35, Trondheim, Norway"));
        addressList.add(MapBackend.addressToPoint("Venusvegen 1, Trondheim, Norway"));

        addressList = MapBackend.getShortestRoute(addressList);

        JFrame frame = new JFrame("JxBrowser - Hello World");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new MapView(addressList));
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MapView(ArrayList<Coordinate> route) {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        disableBrowserLogging();

        browser = new Browser();
        BrowserView view = new BrowserView(browser);

        // Add event listener that fires when the map is done loading
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent finishLoadingEvent) {
                // Send route creation commands to map
                String js = generateRouteJavascript(route);
                browser.executeJavaScript(js);
            }
        });

        browser.loadHTML(getMapHTML());
        mainPanel.add(view, BorderLayout.CENTER);
    }

    /**
     * Disables most, if not all logging done by JxBrowser.
     */
    private void disableBrowserLogging() {
        LoggerProvider.setLevel(Level.OFF);
    }

    /**
     * Resets the zoom level to the default value
     */
    private void resetZoom() {
        browser.executeJavaScript("map.setZoom(12)");
    }

    /**
     Loads the map HTML from the project resources
     * @return A String containing the HTML
     */
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

    /**
     Generates a Javascript snippet for rendering the driving route.
     * @param waypoints The waypoints of the route
     * @return A String containing Javascript code
     */
    private static String generateRouteJavascript(ArrayList<Coordinate> waypoints) {
        if(waypoints.size() < 3) {
            throw new IllegalArgumentException("waypoints needs to contain at least 3 coordinates.");
        }

        String origin = generateLatLng(waypoints.get(0));
        String destination = generateLatLng(waypoints.get(waypoints.size()-1));

        StringBuilder waypointsBuilder = new StringBuilder("[");

        // Add all waypoints except for first and last (which are being set above)
        for (int i = 1; i < waypoints.size() - 1; i++) {
            waypointsBuilder.append("{location:" + generateLatLng(waypoints.get(i)) + ", stopover:true},");
        }

        // Close square bracket at end
        waypointsBuilder.replace(waypointsBuilder.length() - 1, waypointsBuilder.length(), "]");

        return "var request = {" +
                "origin:" + origin + "," +
                "destination:" + destination + "," +
                "waypoints:" + waypointsBuilder.toString() + "," +
                "optimizeWaypoints:true," +
                "travelMode:google.maps.TravelMode.DRIVING" +
                "};" +
                "directionsService.route(request, function(result, status) {" +
                "if (status == google.maps.DirectionsStatus.OK) {" +
                "directionsDisplay.setDirections(result);" +
                "} else { console.log(status)}" +
                "});";
    }

    /**
     Utility function for generating google.maps.LatLng objects
     * @param coordinate The coordinate to convert
     * @return A String representation of the google.maps.LatLng object
     */
    private static String generateLatLng(Coordinate coordinate) {
        return "new google.maps.LatLng(" + coordinate.getLatitude() + "," + coordinate.getLongitude() + ")";
    }
}
