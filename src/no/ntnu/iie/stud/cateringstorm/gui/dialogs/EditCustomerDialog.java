package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.CustomerTableModel;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

/**
 * Created by EliasBrattli on 08/04/2016.
 */
public class EditCustomerDialog extends JDialog{
    private Customer customer;
    private JPanel mainPanel;
    private JPanel textPanel;
    private JPanel cbPanel;
    private JComboBox choiceBox;
    private JLabel infoLabel;
    private JTextField inputField;
    private JPanel buttonPanel;
    private JButton saveButton;
    private JButton cancelButton;
    private CustomerTableModel model;

    public EditCustomerDialog(Customer customer){
        this.customer = customer;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);
        saveButton.addActionListener(e->{
            saveChanges();
        });
        cancelButton.addActionListener(e->{
            onCancel();
        });
        choiceBox.addActionListener(e->{
            setTextField();
        });
        // FIXME: Check out how mouselistener works
        inputField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                emptyTextField();
            }
        });

    }
    private void saveChanges(){
        // TODO: Implement a method sending the saved changes to database

    }
    private void onCancel(){
        // TODO: Implement a method exiting window
        dispose();
    }
    private void setTextField(){
        // TODO: Implement a method setting text field based on chosen column
        final int COLUMN_FORENAME = 0;
        final int COLUMN_SURNAME = 1;
        final int COLUMN_ADDRESS = 2;
        final int COLUMN_PHONE = 3;
        final int COLUMN_EMAIL = 4;
        switch (getChoice()){

            case COLUMN_FORENAME :
                inputField.setText("Enter new forename");
                break;
            case COLUMN_SURNAME :
                inputField.setText("Enter new surname");
            case COLUMN_ADDRESS:
                inputField.setText("Enter new address");
                break;
            case COLUMN_PHONE:
                inputField.setText("Enter new phone number");
                break;
            case COLUMN_EMAIL:
                inputField.setText("Enter new email");
                break;
            default: inputField.setText("Enter input here");
        }
    }
    private int getChoice(){
       return choiceBox.getSelectedIndex();
    }
    private void emptyTextField(){
        // TODO: Implement a method emptying the text field
        if(inputField.getText().equals("Enter input here")) {
            inputField.setText("");
        }
    }
    private void createComboBox(){
        ArrayList<Customer> customerList = CustomerFactory.getAllCustomers();
        Integer[] columns = new Integer[]{ CustomerTableModel.COLUMN_SURNAME,CustomerTableModel.COLUMN_FORENAME, CustomerTableModel.COLUMN_ADDRESS, CustomerTableModel.COLUMN_PHONE, CustomerTableModel.COLUMN_EMAIL}; // Columns can be changed
        model = new CustomerTableModel(customerList, columns);
        Object[] choices = new Object[model.getColumnCount()];
        int ctr = 0;
        for (int i = 1; i < 8; i++) {
            if(i != 4 && i != 6) {
                choices[ctr] = model.getColumnName(i);
                ctr++;
            }
        }
        choiceBox = new JComboBox(choices);
        choiceBox.setSelectedIndex(0);
    }
    private void createTextField(){
        inputField = new JTextField(20);
        inputField.setText("Enter input here");
        add(inputField);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        createComboBox();
        createTextField();
    }

    public static void main(String[] args) {
        final int WIDTH = 300;
        final int HEIGHT = 200;
        EditCustomerDialog dialog = new EditCustomerDialog(null);
        dialog.pack();
        dialog.setSize(WIDTH,HEIGHT);
        dialog.setVisible(true);
        System.exit(0);
    }
}
