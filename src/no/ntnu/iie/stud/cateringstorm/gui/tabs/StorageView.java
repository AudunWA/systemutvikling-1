package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddIngredientDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.IngredientTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * GUI for displaying the inventory of ingredients to chefs and nutrition experts(?).
 * Lets the user add, edit and remove ingredients.
 */
public class StorageView extends JPanel {
    private JPanel mainPanel;
    private JTable ingredientTable;
    private JButton incrementSupply;
    private JButton addIngredientButton;
    private JTextField searchField;
    private JButton refreshButton;
    private IngredientTableModel tableModel;

    private ArrayList<Ingredient> ingredientList;

    public StorageView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        addIngredientButton.addActionListener(e -> {
            AddIngredientDialog dialog = new AddIngredientDialog();
            dialog.pack();
            dialog.setVisible(true);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            if (dialog.getAddedNewValue()) {
                //FIXME toast gives nullpointer
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Ingredient added.", Toast.LENGTH_SHORT, Toast.Style.SUCCESS).display();
                ingredientList = IngredientFactory.getAllIngredients();

                // Refresh data
                tableModel.setRows(IngredientFactory.getAllIngredients());
            } else {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Ingredient add failed.", Toast.LENGTH_SHORT, Toast.Style.ERROR).display();
            }
        });

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

                ArrayList<Ingredient> copy = new ArrayList<>();

                for (Ingredient anIngredientList : ingredientList) {
                    if ((anIngredientList.getName().toLowerCase().contains(searchField.getText().toLowerCase()) || (anIngredientList.getName()).toLowerCase().contains(searchField.getText().toLowerCase()))) {
                        copy.add(anIngredientList);

                    }
                }
                tableModel.setRows(copy);

            }
        });

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchField.setText("");
            }
        });

        incrementSupply.addActionListener(e -> {
            int selectedRow = ingredientTable.getSelectedRow();
            if (selectedRow == -1) {
                return;
            }

            Ingredient ingredient = tableModel.getValue(selectedRow);
            ingredient.incrementAmount();
            int affectedRows = IngredientFactory.updateIngredientAmount(ingredient.getIngredientId(), ingredient.getAmount());

            if (affectedRows == 1) {
                tableModel.setRow(selectedRow, ingredient);
            } else {
                // TODO: Log error?
            }
        });

        refreshButton.addActionListener(e -> {
            ingredientList = IngredientFactory.getAllIngredients();
            tableModel.setRows(ingredientList);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StorageView");
        frame.setContentPane(new StorageView().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private static JTable getNewRenderedTable(final JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                if (((Date) table.getValueAt(row, 3)).before(new Date(System.currentTimeMillis() + 86400000 * 2))) {
                    setBackground(new Color(200, 100, 100));
                } else if (((Date) table.getValueAt(row, 3)).before(new Date(System.currentTimeMillis() + 86400000 * 10))) {
                    setBackground(Color.ORANGE);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }

                return this;
            }
        });
        return table;
    }

    private void createUIComponents() {
        createTable();
    }

    private void createTable() {
        ingredientList = IngredientFactory.getAllIngredients();
        Integer[] columns = new Integer[]{IngredientTableModel.COLUMN_NAME, IngredientTableModel.COLUMN_DESCRIPTION, IngredientTableModel.COLUMN_ID, IngredientTableModel.COLUMN_EXPIRE_DATE, IngredientTableModel.COLUMN_AMOUNT, IngredientTableModel.COLUMN_UNIT};

        tableModel = new IngredientTableModel(ingredientList, columns);
        ingredientTable = new JTable(tableModel);
        getNewRenderedTable(ingredientTable);
        ingredientTable.getTableHeader().setReorderingAllowed(false);
    }
}
