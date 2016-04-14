package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by kenan on 14.04.2016.
 */
public class FoodPackageAdminView extends JFrame {
    private JPanel mainPanel;
    private JTable FoodPackageTable;
    private JButton viewFoodPackage;
    private JButton button2;
    private JTextField searchField;
    private JButton refreshTableButton;

    private FoodPackageTableModel tableModel;

    public FoodPackageAdminView() {

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        viewFoodPackage.addActionListener(e -> {

            int selectedRow = FoodPackageTable.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            FoodPackage foodPackage = tableModel.getValue(selectedRow);


            final int WIDTH = 700;
            final int HEIGHT = 600;


            FoodPackageInfoView dialog = new FoodPackageInfoView(foodPackage);
            dialog.pack();
            dialog.setVisible(true);
            dialog.setSize(WIDTH, HEIGHT);
            dialog.setLocationRelativeTo(null);//Puts window in middle of screen


        });

        FoodPackageTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }
    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createTable(){
        ArrayList<FoodPackage> foodpackageList = FoodPackageFactory.getAllFoodPackages();
        Integer[] columns = new Integer[] { FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_NAME }; // Columns can be changed
        tableModel = new FoodPackageTableModel(foodpackageList, columns);
        FoodPackageTable = new JTable(tableModel);
        FoodPackageTable.setFillsViewportHeight(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
    }

    public static void main(String[] args) {
        final int WIDTH = 700;
        final int HEIGHT = 600;

        JFrame frame = new JFrame("FoodPackageAdminView");
        frame.setContentPane(new FoodPackageAdminView().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);//Puts window in middle of screen
    }
}
