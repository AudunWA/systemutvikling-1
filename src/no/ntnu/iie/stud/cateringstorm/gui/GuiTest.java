package no.ntnu.iie.stud.cateringstorm.gui;

/**
 * Created by Audun on 09.03.2016.
 */
public class GuiTest {
    public static void main(String[] args) {
        //LoginWindow loginWindowOld = new LoginWindow();
        //Login loginWindow = new Login();
        //loginWindowOld.setVisible(true);
        //loginWindow.setVisible(true);
        ChefOrderOverview orderOverview = new ChefOrderOverview();
        orderOverview.setVisible(false);
        Employee empl = new Employee();
        empl.setVisible(false);
        OrderInfo ordInf = new OrderInfo();
        ordInf.setVisible(true);
        DishInfo dishInf = new DishInfo();
        dishInf.setVisible(true);
    }
}
