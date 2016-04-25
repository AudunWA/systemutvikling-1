package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.Main;
import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.gui.tabs.*;
import no.ntnu.iie.stud.cateringstorm.gui.util.DynamicTabbedPane;

import javax.swing.*;
import java.net.URL;

/**
 * The main view of the application.
 * Displayed after successful login, and contains all main subviews for specific employees.
 * Created by Audun on 31.03.2016.
 * Hei
 */
public class DashboardView extends JFrame {
    private static final String WINDOW_TITLE = "Catering Storm Dashboard";

    // Window dimensions
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private Employee loggedInEmployee;

    private DynamicTabbedPane tabPane;
    private JPanel mainPanel;

    public DashboardView(Employee loggedInEmployee) {
        super();
        this.loggedInEmployee = loggedInEmployee;

        Main.setApplicationIcon(this);
        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        fillTabPane();
        //pack();
    }

    public static void main(String[] args) {
        // Makes the GUI same style as current OS :)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        DashboardView view = new DashboardView(new Employee(-1, "Test", "Forename", "Surname", "Address",
                "Phone", "Email", EmployeeType.ADMINISTRATOR, true));
        view.setVisible(true);
    }

    private void fillTabPane() {
        ClassLoader loader = getClass().getClassLoader();
        if (loader == null) {
            System.err.println("Could not get class loader, aborting tab creation!");
            return;
        }
        tabPane.addTab("Home", getIconResource("ic_home_black_24dp_1x.png"), HomeView.class);
        tabPane.addTab("Timesheet", getIconResource("timetable_1x.png"), TimesheetView.class);
        switch (loggedInEmployee.getEmployeeType()) {
            case EMPLOYEE:
                break;
            case CHEF:
                //tabPane.addTab("OrderInfoView", new OrderInfoView());
                tabPane.addTab("Storage", getIconResource("ic_shopping_cart_black_24dp_1x.png"), StorageView.class);
                tabPane.addTab("Food packages", getIconResource("package_1x.png"), FoodPackageAdminView.class);
                tabPane.addTab("Orders", getIconResource("ic_list_black_24dp_1x.png"), ChefOrderView.class);
                tabPane.addTab("Shopping", getIconResource("cart-plus_1x.png"), ChefShoppingList.class);
                break;
            case CHAUFFEUR:
                tabPane.addTab("Delivery", getIconResource("ic_assignment_black_24dp_1x.png"), ChauffeurOrderView.class);
                tabPane.addTab("Shopping", getIconResource("cart-plus_1x.png"), ChefShoppingList.class);
                break;
            case NUTRITION_EXPERT:
                tabPane.addTab("Menu", getIconResource("ic_restaurant_menu_black_24dp_1x.png"), MenuAdministratorView.class);
                tabPane.addTab("Food packages", getIconResource("package_1x.png"), FoodPackageAdminView.class);
                tabPane.addTab("Storage", getIconResource("ic_shopping_cart_black_24dp_1x.png"), StorageView.class);
                break;
            case SALESPERSON:
                tabPane.addTab("Orders", getIconResource("ic_list_black_24dp_1x.png"), SalespersonOrderView.class);
                tabPane.addTab("Subscriptions", getIconResource("ic_update_black_24dp_1x.png"), SalespersonSubscriptionView.class);
                tabPane.addTab("Customers", getIconResource("ic_account_circle_black_24dp_1x.png"), SalespersonCustomerView.class);
                tabPane.addTab("Food packages", getIconResource("package_1x.png"), FoodPackageAdminView.class);
                break;
            case ADMINISTRATOR:
                //tabPane.addTab("OrderInfoView", new OrderInfoView());
                tabPane.addTab("Orders (chef)", getIconResource("ic_list_black_24dp_1x.png"), ChefOrderView.class);
                tabPane.addTab("Orders (salesperson)", getIconResource("ic_list_black_24dp_1x.png"), SalespersonOrderView.class);
                tabPane.addTab("Subscriptions", getIconResource("ic_update_black_24dp_1x.png"), SalespersonSubscriptionView.class);
                tabPane.addTab("Delivery", getIconResource("ic_assignment_black_24dp_1x.png"), ChauffeurOrderView.class);
                tabPane.addTab("Menu", getIconResource("ic_restaurant_menu_black_24dp_1x.png"), MenuAdministratorView.class);
                tabPane.addTab("Food packages", getIconResource("package_1x.png"), FoodPackageAdminView.class);
                tabPane.addTab("Storage", getIconResource("ic_shopping_cart_black_24dp_1x.png"), StorageView.class);
                tabPane.addTab("Customers", getIconResource("ic_account_circle_black_24dp_1x.png"), SalespersonCustomerView.class);
                tabPane.addTab("Employees", getIconResource("ic_accessibility_black_24dp_1x.png"), AdminEmployeeView.class);
                //tabPane.addTab("FoodPackageInfoview", new FoodPackageInfoView());
                tabPane.addTab("Statistics", getIconResource("ic_insert_chart_black_24dp_1x.png"), StatisticsView.class);
                tabPane.addTab("Shopping", getIconResource("cart-plus_1x.png"), ChefShoppingList.class);
                break;
        }
    }

    /**
     * Helper method for creating ImageIcons from project resources
     *
     * @param path The resource path (image)
     * @return An ImageIcon initialized with the loaded resource
     */
    private ImageIcon getIconResource(String path) {
        path = "icons/" + path;

        ClassLoader loader = getClass().getClassLoader();
        if (loader == null) {
            System.err.println("Could not get class loader!");
            return new ImageIcon();
        }

        URL resource = loader.getResource(path);
        if (resource == null) {
            System.err.println("Could not get resource \"" + path + "\"");
            return new ImageIcon();
        }
        return new ImageIcon(resource);
    }
}
