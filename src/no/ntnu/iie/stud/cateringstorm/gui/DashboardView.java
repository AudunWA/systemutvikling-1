package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.Main;
import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.gui.tabs.*;
import no.ntnu.iie.stud.cateringstorm.gui.util.DynamicTabbedPane;
import no.ntnu.iie.stud.cateringstorm.util.ResourceUtil;

import javax.swing.*;
import java.net.URL;

/**
 * The main view of the application.
 * Displayed after successful login, and contains all main subviews for specific employees.
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

        ResourceUtil.setApplicationIcon(this);
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
                "Phone", "Email", EmployeeType.ADMINISTRATOR, true,206.49,0)); //Admin has "secretary" salary
        view.setVisible(true);
    }

    private void fillTabPane() {
        ClassLoader loader = getClass().getClassLoader();
        if (loader == null) {
            System.err.println("Could not get class loader, aborting tab creation!");
            return;
        }
        tabPane.addTab("Home", ResourceUtil.getIconResource("icons/ic_home_black_24dp_1x.png"), HomeView.class);
        tabPane.addTab("Timesheet", ResourceUtil.getIconResource("icons/timetable_1x.png"), TimesheetView.class);
        switch (loggedInEmployee.getEmployeeType()) {
            case EMPLOYEE:
                break;
            case CHEF:
                tabPane.addTab("Storage", ResourceUtil.getIconResource("icons/icons/ic_shopping_cart_black_24dp_1x.png"), StorageView.class);
                tabPane.addTab("Food packages", ResourceUtil.getIconResource("icons/package_1x.png"), FoodPackageAdminView.class);
                tabPane.addTab("Orders", ResourceUtil.getIconResource("icons/ic_list_black_24dp_1x.png"), ChefOrderView.class);
                tabPane.addTab("Shopping", ResourceUtil.getIconResource("icons/cart-plus_1x.png"), ChefShoppingList.class);
                break;
            case CHAUFFEUR:
                tabPane.addTab("Delivery", ResourceUtil.getIconResource("icons/ic_assignment_black_24dp_1x.png"), ChauffeurOrderView.class);
                tabPane.addTab("Shopping", ResourceUtil.getIconResource("icons/cart-plus_1x.png"), ChefShoppingList.class);
                break;
            case NUTRITION_EXPERT:
                tabPane.addTab("Menu", ResourceUtil.getIconResource("icons/ic_restaurant_menu_black_24dp_1x.png"), MenuAdministratorView.class);
                tabPane.addTab("Food packages", ResourceUtil.getIconResource("icons/package_1x.png"), FoodPackageAdminView.class);
                tabPane.addTab("Storage", ResourceUtil.getIconResource("icons/ic_shopping_cart_black_24dp_1x.png"), StorageView.class);
                break;
            case SALESPERSON:
                tabPane.addTab("Orders", ResourceUtil.getIconResource("icons/ic_list_black_24dp_1x.png"), SalespersonOrderView.class);
                tabPane.addTab("Subscriptions", ResourceUtil.getIconResource("icons/ic_update_black_24dp_1x.png"), SalespersonSubscriptionView.class);
                tabPane.addTab("Customers", ResourceUtil.getIconResource("icons/ic_account_circle_black_24dp_1x.png"), SalespersonCustomerView.class);
                tabPane.addTab("Food packages", ResourceUtil.getIconResource("icons/package_1x.png"), FoodPackageAdminView.class);
                break;
            case ADMINISTRATOR:
                tabPane.addTab("Orders (chef)", ResourceUtil.getIconResource("icons/ic_list_black_24dp_1x.png"), ChefOrderView.class);
                tabPane.addTab("Orders (salesperson)", ResourceUtil.getIconResource("icons/ic_list_black_24dp_1x.png"), SalespersonOrderView.class);
                tabPane.addTab("Subscriptions", ResourceUtil.getIconResource("icons/ic_update_black_24dp_1x.png"), SalespersonSubscriptionView.class);
                tabPane.addTab("Delivery", ResourceUtil.getIconResource("icons/ic_assignment_black_24dp_1x.png"), ChauffeurOrderView.class);
                tabPane.addTab("Menu", ResourceUtil.getIconResource("icons/ic_restaurant_menu_black_24dp_1x.png"), MenuAdministratorView.class);
                tabPane.addTab("Food packages", ResourceUtil.getIconResource("icons/package_1x.png"), FoodPackageAdminView.class);
                tabPane.addTab("Storage", ResourceUtil.getIconResource("icons/ic_shopping_cart_black_24dp_1x.png"), StorageView.class);
                tabPane.addTab("Customers", ResourceUtil.getIconResource("icons/ic_account_circle_black_24dp_1x.png"), SalespersonCustomerView.class);
                tabPane.addTab("Employees", ResourceUtil.getIconResource("icons/ic_accessibility_black_24dp_1x.png"), AdminEmployeeView.class);
                tabPane.addTab("Statistics", ResourceUtil.getIconResource("icons/ic_insert_chart_black_24dp_1x.png"), StatisticsView.class);
                tabPane.addTab("Shopping", ResourceUtil.getIconResource("icons/cart-plus_1x.png"), ChefShoppingList.class);
                break;
        }
    }
}
