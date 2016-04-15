package no.ntnu.iie.stud.cateringstorm.gui;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.gui.tabs.*;

import javax.swing.*;

/**
 * The main view of the application.
 * Displayed after successful login, and contains all main subviews for specific employees.
 * Created by Audun on 31.03.2016.
 */
public class DashboardView extends JFrame {
    private static final String WINDOW_TITLE = "Catering Storm Dashboard";

    // Window dimensions
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private Employee loggedInEmployee;

    private JTabbedPane tabPane;
    private JPanel mainPanel;

    public DashboardView(Employee loggedInEmployee) {
        super();
        this.loggedInEmployee = loggedInEmployee;

        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        fillTabPane();
        //pack();
    }

    private void fillTabPane() {
        ClassLoader loader = getClass().getClassLoader();
        if(loader == null) {
            System.err.println("Could not get class loader, aborting tab creation!");
            return;
        }
        tabPane.addTab("Home", new ImageIcon(loader.getResource("ic_home_black_24dp_1x.png")), new HomeView(loggedInEmployee));

        switch (loggedInEmployee.getEmployeeType()) {
            case EMPLOYEE:
                break;
            case CHEF:
                //tabPane.addTab("OrderInfoView", new OrderInfoView());
                tabPane.addTab("Storage", new ImageIcon(loader.getResource("ic_shopping_cart_black_24dp_1x.png")), new StorageView());
                tabPane.addTab("Orders", new ImageIcon(loader.getResource("ic_list_black_24dp_1x.png")), new ChefOrderView());
                break;
            case CHAUFFEUR:
                tabPane.addTab("Order delivery", new ImageIcon(loader.getResource("ic_assignment_black_24dp_1x.png")), new ChauffeurOrderView());
                break;
            case NUTRITION_EXPERT:
                tabPane.addTab("Menu", new ImageIcon(loader.getResource("ic_restaurant_menu_black_24dp_1x.png")), new MenuAdministratorView());
                tabPane.addTab("Storage", new ImageIcon(loader.getResource("ic_shopping_cart_black_24dp_1x.png")), new StorageView());
                break;
            case ADMINISTRATOR:
                //tabPane.addTab("OrderInfoView", new OrderInfoView());
                tabPane.addTab("Orders (chef)", new ImageIcon(loader.getResource("ic_list_black_24dp_1x.png")), new ChefOrderView());
                tabPane.addTab("Orders (salesperson)", new ImageIcon(loader.getResource("ic_list_black_24dp_1x.png")), new SalespersonOrderView(loggedInEmployee));
                tabPane.addTab("Order delivery", new ImageIcon(loader.getResource("ic_assignment_black_24dp_1x.png")), new ChauffeurOrderView());
                tabPane.addTab("Menu", new ImageIcon(loader.getResource("ic_restaurant_menu_black_24dp_1x.png")), new MenuAdministratorView());
                tabPane.addTab("Storage", new ImageIcon(loader.getResource("ic_shopping_cart_black_24dp_1x.png")), new StorageView());
                tabPane.addTab("Customers", new ImageIcon(loader.getResource("ic_account_circle_black_24dp_1x.png")), new SalespersonCustomerView());
                //tabPane.addTab("FoodPackageInfoview", new FoodPackageInfoView());
                tabPane.addTab("Statistics", new ImageIcon(loader.getResource("ic_insert_chart_black_24dp_1x.png")), new StatisticsView());
                break;
            case SALESPERSON:
                tabPane.addTab("Orders", new ImageIcon(loader.getResource("ic_list_black_24dp_1x.png")), new SalespersonOrderView(loggedInEmployee));
                tabPane.addTab("Customers", new ImageIcon(loader.getResource("ic_account_circle_black_24dp_1x.png")), new SalespersonCustomerView());
                break;
        }
    }

    public static void main(String[] args) {
        // Makes the GUI same style as current OS :)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        DashboardView view = new DashboardView(new Employee(-1, "Test", "Forename", "Surname", "Address",
                "Phone", "Email", EmployeeType.ADMINISTRATOR));
        view.setVisible(true);
    }
}
