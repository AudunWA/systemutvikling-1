package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddFoodPackageDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditFoodPackageDialog;
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
    private JButton removeFoodPackageButton;
    private JTextField searchTextField;
    private JButton searchButton;
    private JCheckBox inactiveCheckBox;
    private JButton addButton;
    private JButton editButton;

    private FoodPackageTableModel tableModel;

    public FoodPackageAdminView() {

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            AddFoodPackageDialog dialog = new AddFoodPackageDialog();
            dialog.pack();
            dialog.setVisible(true);
        });
        editButton.addActionListener(e -> {
            int selectedRow = FoodPackageTable.getSelectedRow();
            if(selectedRow == -1) {
                return;
            }

            FoodPackage foodPackage = tableModel.getValue(selectedRow);

            EditFoodPackageDialog dialog = new EditFoodPackageDialog(foodPackage);
            dialog.pack();
            dialog.setVisible(true);

            if(dialog.getAddedNewValue()) {
                // Refresh data
                refreshTable();
            }
        });

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
        searchButton.addActionListener(e -> {
            ArrayList<FoodPackage> newRows;
            if(searchTextField.getText().trim().equals("")) {
                if(inactiveCheckBox.isSelected()) {
                    newRows = FoodPackageFactory.getAllFoodPackages();
                } else {
                    newRows = FoodPackageFactory.getActiveFoodPackages();
                }
            } else {
                if(inactiveCheckBox.isSelected()) {
                    newRows = FoodPackageFactory.getAllFoodPackagesByQuery(searchTextField.getText());
                } else {
                    newRows = FoodPackageFactory.getActiveFoodPackagesByQuery(searchTextField.getText());
                }
            }
            tableModel.setRows(newRows);
        });
        removeFoodPackageButton.addActionListener(e -> {
            int selectedRow = FoodPackageTable.getSelectedRow();
            if(selectedRow == -1) {
                return;
            }

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
            if(dialogResult == 0) {
                FoodPackage foodPackage = tableModel.getValue(selectedRow);
                foodPackage.setActive(false);
                FoodPackageFactory.updateFoodPackage(foodPackage);

                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(null, "Row is removed.");
            }
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
        ArrayList<FoodPackage> foodpackageList = FoodPackageFactory.getActiveFoodPackages();
        Integer[] columns = new Integer[] { FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_NAME }; // Columns can be changed
        tableModel = new FoodPackageTableModel(foodpackageList, columns);
        FoodPackageTable = new JTable(tableModel);
        FoodPackageTable.setFillsViewportHeight(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
    }

    private void refreshTable() {
        tableModel.setRows(FoodPackageFactory.getAllFoodPackages());
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
