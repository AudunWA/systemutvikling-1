package no.ntnu.iie.stud.cateringstorm.gui.tabs;

/*import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;*/

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddDishDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditDishDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.DishTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Give you options for administrating the menu by adding new dishes, editing existing or removing old ones
 */
public class MenuAdministratorView extends JPanel {
    private static final Integer[] COLUMNS = new Integer[]{DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION};
    private static final Integer[] ADMIN_COLUMNS = new Integer[]{DishTableModel.COLUMN_NAME, DishTableModel.COLUMN_DESCRIPTION, DishTableModel.COLUMN_ACTIVE};
    private JButton addDishButton;
    private JButton editDishButton;
    private JButton removeDishButton;
    private JTable dishTable;
    private JPanel mainPanel;
    private JButton exitButton;
    private JTextField searchField;
    private JButton refreshButton;
    private JCheckBox inactiveCheckBox;

    private ArrayList<Dish> dishList;

    private DishTableModel tableModel;

    public MenuAdministratorView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        addDishButton.addActionListener(e -> onAdd());
        editDishButton.addActionListener(e -> onEdit());
        refreshButton.addActionListener(e -> refresh());
        removeDishButton.addActionListener(e -> onRemove());
        inactiveCheckBox.addActionListener(e -> refresh());

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

                ArrayList<Dish> copy = new ArrayList<>();

                for (int i = 0; i < dishList.size(); i++) {
                    if ((dishList.get(i).getName()).toLowerCase().contains(searchField.getText().toLowerCase()) || (dishList.get(i).getDescription()).toLowerCase().contains(searchField.getText().toLowerCase())) {
                        copy.add(dishList.get(i));

                    }
                }
                tableModel.setRows(copy);

            }
        });

        //Searches on enter key press
        searchField.addActionListener(e -> search());

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setSearchField("");
            }
        });

        dishTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }
    /**
     * Removes the selected dish by changing its active status in the database
     */
    private void onRemove() {
        int selectedRow = dishTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if (dialogResult == 0) {
            Dish dish = tableModel.getValue(selectedRow);
            dish.setActive(false);
            DishFactory.updateDish(dish);

            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(null, "Row is removed.");
        }
        refresh();
    }
    /**
     * Opens an EditDishDialog GUI
     */
    private void onEdit() {
        int selectedRow = dishTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        Dish dish = tableModel.getValue(selectedRow);

        EditDishDialog dialog = new EditDishDialog(dish);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setVisible(true);

        if (dialog.getAddedNewValue()) {
            // Refresh data
            refresh();
        }
    }

    /**
     * Opens an AddDishDialog GUI
     */
    private void onAdd() {
        AddDishDialog dialog = new AddDishDialog();
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setVisible(true);
        dishList = DishFactory.getActiveDishes();
    }

    public static void main(String[] args) {
        // Window dimensions
        final int WIDTH = 700;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new MenuAdministratorView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);//Puts window in middle of screen
    }

    private void createSearchField() {
        searchField = new JTextField(20);
        setSearchField("Search for dishes...");
        add(searchField);
    }

    private void setSearchField(String text) {
        searchField.setText(text);
        searchField.setEnabled(true);
    }

    private void createUIComponents() {
        // TODO: Custom initialization of UI components here
        createTable();
    }

    private void createTable() {
        dishList = DishFactory.getActiveDishes();
        if (GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR) {
            tableModel = new DishTableModel(dishList, ADMIN_COLUMNS);
        } else {
            tableModel = new DishTableModel(dishList, COLUMNS);
        }
        dishTable = new JTable(tableModel);
        dishTable.setFillsViewportHeight(true);
    }

    /**
     * Searches through
     */
    private void search() {
        ArrayList<Dish> newRows;
        if (searchField.getText().trim().equals("")) {
            if (inactiveCheckBox.isSelected()) {
                newRows = DishFactory.getAllDishes();
            } else {
                newRows = DishFactory.getActiveDishes();
            }
        } else {
            if (inactiveCheckBox.isSelected()) {
                newRows = DishFactory.getAllDishesByQuery(searchField.getText());
            } else {
                newRows = DishFactory.getActiveDishesByQuery(searchField.getText());
            }
        }
        tableModel.setRows(newRows);
    }

    private void refresh() {
        if (inactiveCheckBox.isSelected()) {
            dishList = DishFactory.getAllDishes();
        } else {
            dishList = DishFactory.getActiveDishes();
        }
        tableModel.setRows(dishList);
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Dishes refreshed").display();
    }
}

