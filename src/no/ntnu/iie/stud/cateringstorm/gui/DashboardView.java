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

    private Employee employee;

    private JTabbedPane tabPane;
    private JPanel mainPanel;

    public DashboardView(Employee employee) {
        super();
        this.employee = employee;

        setTitle(WINDOW_TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        fillTabPane();
        //pack();
    }

    private void fillTabPane() {
        tabPane.addTab("HomeView", new HomeView(employee));

        switch (employee.getEmployeeType()) {
            case EMPLOYEE:
                break;
            case CHEF:
                //tabPane.addTab("OrderInfoView", new OrderInfoView());
                tabPane.addTab("StorageView", new StorageView());
                tabPane.addTab("ChefOrderView", new ChefOrderView());
                break;
            case CHAUFFEUR:
                tabPane.addTab("ChauffeurOrderView", new ChauffeurOrderView());
                break;
            case NUTRITION_EXPERT:
                tabPane.addTab("MenuAdministratorView", new MenuAdministratorView());
                tabPane.addTab("StorageView", new StorageView());
                break;
            case ADMINISTRATOR:
                //tabPane.addTab("OrderInfoView", new OrderInfoView());
                tabPane.addTab("ChefOrderView", new ChefOrderView());
                tabPane.addTab("ChauffeurOrderView", new ChauffeurOrderView());
                tabPane.addTab("MenuAdministratorView", new MenuAdministratorView());
                tabPane.addTab("StorageView", new StorageView());
                tabPane.addTab("SalespersonOrderView", new SalespersonOrderView(employee));
                tabPane.addTab("SalespersonCustomerView", new SalespersonCustomerView());
                //tabPane.addTab("FoodPackageInfoview", new FoodPackageInfoView());
                break;
            case SALESPERSON:
                tabPane.addTab("SalespersonOrderView", new SalespersonOrderView(employee));
                tabPane.addTab("SalespersonCustomerView", new SalespersonCustomerView());
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
