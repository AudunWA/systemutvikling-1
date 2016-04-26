package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.subscription.Subscription;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.SubscriptionFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.AddSubscriptionDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.SubscriptionTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Gives you options for adding, editing subscriptions
 */
public class SalespersonSubscriptionView extends JPanel {
    private JPanel mainPanel;
    private JScrollPane orderPane;
    private JButton viewButton;
    private JButton editOrderButton;
    private JComboBox<String> statusBox;
    private JTable orderTable;
    private JButton refreshButton;
    private JButton searchButton;
    private JTextField searchField;
    private JButton newSubscriptionButton;
    private JButton removeButton;
    private JCheckBox inactiveCheckBox;
    private SubscriptionTableModel tableModel;

    private ArrayList<Subscription> subscriptionList;

    public SalespersonSubscriptionView() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        refreshButton.addActionListener(e -> refresh());
        editOrderButton.addActionListener(e -> editSubscription(getSelectedSubscription()));
        newSubscriptionButton.addActionListener(e -> newSubscription());
        removeButton.addActionListener(e -> removeSubscription());
        inactiveCheckBox.addActionListener(e -> refresh() );

        statusBox.addActionListener(e -> {
            //setStatus();
        });

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchField.setText("");
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

                ArrayList<Subscription> copy = new ArrayList<>();

                // TODO
                //for (Subscription subscription : tableModel.getRowsClone()) {
                //    if (subscription.get().toLowerCase().contains(searchField.getText().toLowerCase()) || (orderList.get(i).getCustomerAddress()).toLowerCase().contains(searchField.getText().toLowerCase()))){
                //        copy.add(orderList.get(i));
                //    }
                //}
                //tableModel.setRows(copy);

            }
        });

        orderTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });
    }

    public static void main(String[] args) {
        // Window dimensions
        final int WIDTH = 1300;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new SalespersonSubscriptionView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }

    private Subscription getSelectedSubscription() {
        return tableModel.getValue(orderTable.getSelectedRow());
    }

    private void newSubscription() {
        AddSubscriptionDialog asDialog = new AddSubscriptionDialog();
        final int WIDTH = 1300;
        final int HEIGHT = 600;
        asDialog.pack();
        asDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        asDialog.setSize(WIDTH, HEIGHT);
        asDialog.setLocationRelativeTo(null);
        asDialog.setVisible(true);

        if (asDialog.getSubscription() == null) {
            // Failed
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Subscription not created.", Toast.Style.ERROR).display();
        } else {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Subscription created.", Toast.Style.SUCCESS).display();
        }
    }

    private void editSubscription(Subscription subscription) {
        // TODO?
    }
    private void removeSubscription() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure?", "", dialogButton);
        if (dialogResult == 0) {
            Subscription subscription = tableModel.getValue(selectedRow);
            subscription.setActive(false);
            SubscriptionFactory.updateSubscription(subscription);

            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(null, "Row is removed.");
        }
        refresh();
    }

    private void createUIComponents() {
        createTable();
        createComboBox();
        createSearchField();
    }

    private void createTable() {
        Integer[] columns = new Integer[]{SubscriptionTableModel.COLUMN_ID, SubscriptionTableModel.COLUMN_START_DATE, SubscriptionTableModel.COLUMN_END_DATE, SubscriptionTableModel.COLUMN_CUSTOMER_ID, SubscriptionTableModel.COLUMN_COST, SubscriptionTableModel.COLUMN_ACTIVE};
        tableModel = new SubscriptionTableModel(SubscriptionFactory.getActiveSubscriptions(), columns);
        orderTable = new JTable(tableModel);
        orderTable.getTableHeader().setReorderingAllowed(false);
        orderPane = new JScrollPane(orderTable);
        orderTable.setFillsViewportHeight(true);
    }

    private void createComboBox() {
        String[] status = {"Activate", "Remove"};

        statusBox = new JComboBox<>(status);
        statusBox.setSelectedIndex(0);
    }

    private void createSearchField() {
        searchField = new JTextField(20);
        setSearchField("Search by customer name");
        add(searchField);
    }

    private void setSearchField(String text) {
        searchField.setText(text);
        searchField.setEnabled(true);
    }
    private void search() {
        ArrayList<Subscription> newRows;
        if (searchField.getText().trim().equals("")) {
            if (inactiveCheckBox.isSelected()) {
                newRows = SubscriptionFactory.getAllSubscriptions();
            } else {
                newRows = SubscriptionFactory.getActiveSubscriptions();
            }
        } else {
            if (inactiveCheckBox.isSelected()) {
                newRows = SubscriptionFactory.getAllSubscriptionsByQuery(searchField.getText());
            } else {
                newRows = SubscriptionFactory.getActiveSubscriptionsByQuery(searchField.getText());
            }
        }
        tableModel.setRows(newRows);
    }

    private void refresh() {
        if (inactiveCheckBox.isSelected()) {
            subscriptionList = SubscriptionFactory.getActiveSubscriptions();
        } else {
            subscriptionList = SubscriptionFactory.getAllSubscriptions();
        }
        Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Customers refreshed.").display();
        tableModel.setRows(subscriptionList);
    }
}