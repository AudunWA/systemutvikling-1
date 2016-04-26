package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.util.Coordinate;
import no.ntnu.iie.stud.cateringstorm.maps.MapBackend;
import no.ntnu.iie.stud.cateringstorm.util.ResourceUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Displays a map with driving routes for the chauffeur.
 */
public class MapView extends JFrame {
    private JPanel mainPanel;
    private JButton setDeliveredButton;
    private Browser browser;

    private ArrayList<Order> orders;
    private boolean success;

    public MapView(ArrayList<Coordinate> route, ArrayList<Order> orders) {
        this.orders = orders;
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

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

        setDeliveredButton.addActionListener(e -> setDelivered());

        browser.loadHTML(ResourceUtil.getMapHTML());
        mainPanel.add(view, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ArrayList<Coordinate> addressList = new ArrayList<>();
        addressList.add(MapBackend.addressToPoint("Stibakken 2, Malvik, Norway"));
        addressList.add(MapBackend.addressToPoint("Valgrindvegen 12, Trondheim, Norway"));
        addressList.add(MapBackend.addressToPoint("Anders tvereggensveg 2, Trondheim, Norway"));
        addressList.add(MapBackend.addressToPoint("Eidsvolls gate 35, Trondheim, Norway"));
        addressList.add(MapBackend.addressToPoint("Venusvegen 1, Trondheim, Norway"));

        addressList = MapBackend.getShortestRoute(addressList);

        JFrame frame = new JFrame("Map");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new MapView(addressList, OrderFactory.getAllAvailableOrdersChauffeur()));
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Generates a Javascript snippet for rendering the driving route.
     *
     * @param waypoints The waypoints of the route
     * @return A String containing Javascript code
     */
    private static String generateRouteJavascript(ArrayList<Coordinate> waypoints) {
        if (waypoints.size() < 3) {
            throw new IllegalArgumentException("waypoints needs to contain at least 3 coordinates. Has " + waypoints.size());
        }

        String origin = generateLatLng(waypoints.get(0));
        String destination = generateLatLng(waypoints.get(waypoints.size() - 1));

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
     * Utility function for generating google.maps.LatLng objects
     *
     * @param coordinate The coordinate to convert
     * @return A String representation of the google.maps.LatLng object
     */
    private static String generateLatLng(Coordinate coordinate) {
        return "new google.maps.LatLng(" + coordinate.getLatitude() + "," + coordinate.getLongitude() + ")";
    }

    private void setDelivered() {

        for (Order order : orders) {
            OrderFactory.setOrderState(order.getOrderId(), 2);
        }

        orders = OrderFactory.getAllAvailableOrdersForChauffeurTable();
        success = true;

        setVisible(false);
        dispose();
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

    public boolean isSuccess() {
        return success;
    }
}
