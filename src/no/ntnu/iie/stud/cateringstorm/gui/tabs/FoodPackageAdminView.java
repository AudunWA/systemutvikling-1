package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddFoodPackageDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditFoodPackageDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.FoodPackageTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Gives you options for editing, adding or removing foodpackages
 */
public class FoodPackageAdminView extends JPanel {
    private JPanel mainPanel;
    private JTable foodPackageTable;
    private JButton viewFoodPackage;
    private JButton removeFoodPackageButton;
    private JTextField searchField;
    private JButton refreshButton;
    private JCheckBox inactiveCheckBox;
    private JButton addButton;
    private JButton editButton;

    private ArrayList<FoodPackage> foodPackageList;

    private FoodPackageTableModel tableModel;

    public FoodPackageAdminView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        addActionListeners();
        addDocumentListener();
    }

    // Test method
    public static void main(String[] args) {
        final int WIDTH = 700;
        final int HEIGHT = 600;

        JFrame frame = new JFrame("FoodPackageAdminView");
        frame.setContentPane(new FoodPackageAdminView());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);//Puts window in middle of screen
    }

    private void addActionListeners() {
        addButton.addActionListener(e -> onAdd());
        editButton.addActionListener(e -> onEdit());
        viewFoodPackage.addActionListener(e -> onView());
        refreshButton.addActionListener(e -> refresh());
        removeFoodPackageButton.addActionListener(e -> onRemove());
        inactiveCheckBox.addActionListener(e -> refresh());
        foodPackageTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setSearchField("");
                //refreshButton.setEnabled(true);
            }
        });

        foodPackageTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }

    private void addDocumentListener() {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchDocument();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchDocument();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchDocument();
            }

            public void searchDocument() {

                ArrayList<FoodPackage> copy = new ArrayList<>();

                for (int i = 0; i < foodPackageList.size(); i++) {
                    if ((foodPackageList.get(i).getName()).toLowerCase().contains(searchField.getText().toLowerCase())) {
                        copy.add(foodPackageList.get(i));
                    }
                }
                tableModel.setRows(copy);
            }
        });
    }

    private void setSearchField(String text) {
        searchField.setText(text);
        searchField.setEnabled(true);
    }

    /**
     * Opens the AddFoodPackage GUI Dialog
     */
    private void onAdd() {
        AddFoodPackageDialog dialog = new AddFoodPackageDialog();
        dialog.pack();
        dialog.setVisible(true);
        refresh();
    }

    /**
     * Opens the EditFoodPackage GUI Dialog
     */
    private void onEdit() {
        int selectedRow = foodPackageTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        FoodPackage foodPackage = tableModel.getValue(selectedRow);

        EditFoodPackageDialog dialog = new EditFoodPackageDialog(foodPackage);
        dialog.pack();
        dialog.setVisible(true);

        if (dialog.getAddedNewValue()) {
            // Refresh data
            refresh();
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Food package updated.", Toast.Style.SUCCESS).display();

        }
    }

    /**
     * Opens the FoodPackageInfoView GUI
     */
    private void onView() {
        int selectedRow = foodPackageTable.getSelectedRow();
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

    }

    /**
     * Removes the selected row by change its active status in the database
     */
    private void onRemove() {
        int selectedRow = foodPackageTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if (dialogResult == 0) {
            FoodPackage foodPackage = tableModel.getValue(selectedRow);
            foodPackage.setActive(false);
            FoodPackageFactory.updateFoodPackage(foodPackage);

            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(null, "Row is removed.");
            refresh();
        }
    }

    /**
     * Searches through a list of foodpackages within database.
     */
    private void search() {
        ArrayList<FoodPackage> newRows;
        if (searchField.getText().trim().equals("")) {
            if (inactiveCheckBox.isSelected()) {
                newRows = FoodPackageFactory.getAllFoodPackages();
            } else {
                newRows = FoodPackageFactory.getActiveFoodPackages();
            }
        } else {
            if (inactiveCheckBox.isSelected()) {
                newRows = FoodPackageFactory.getAllFoodPackagesByQuery(searchField.getText());
            } else {
                newRows = FoodPackageFactory.getActiveFoodPackagesByQuery(searchField.getText());
            }
        }
        tableModel.setRows(newRows);
    }

    private void createTable() {
        foodPackageList = FoodPackageFactory.getActiveFoodPackages();
        Integer[] columns = new Integer[]{FoodPackageTableModel.COLUMN_NAME, FoodPackageTableModel.COLUMN_COST, FoodPackageTableModel.COLUMN_ACTIVE}; // Columns can be changed
        tableModel = new FoodPackageTableModel(foodPackageList, columns);
        foodPackageTable = new JTable(tableModel);
        foodPackageTable.setFillsViewportHeight(true);
    }

    private void createUIComponents() {
        createTable();
    }

    private void refresh() {
        if (inactiveCheckBox.isSelected()) {
            foodPackageList = FoodPackageFactory.getAllFoodPackages();
        } else {
            foodPackageList = FoodPackageFactory.getActiveFoodPackages();
        }
        tableModel.setRows(foodPackageList);
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Dishes refreshed").display();
        //setSearchField("Search customer names.");
    }

}
