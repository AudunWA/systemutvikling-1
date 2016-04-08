package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;

import javax.swing.*;

/**
 * Created by EliasBrattli on 08/04/2016.
 */
public class EditCustomerDialog {
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

    public EditCustomerDialog(Customer customer){
        this.customer = customer;
        saveButton.addActionListener(e->{
            saveChanges();
        });
        cancelButton.addActionListener(e->{
            cancel();
        });
        choiceBox.addActionListener(e->{
            setTextField();
        });
        inputField.addActionListener(e->{
            emptyTextField();
        });

    }
    private void saveChanges(){
        // TODO: Implement a method sending the saved changes to database
    }
    private void cancel(){
        // TODO: Implement a method exiting window
    }
    private void setTextField(){
        // TODO: Implement a method setting text field based on chosen column
    }
    private int getChoice(){
        return 0;
    }
    private void emptyTextField(){
        // TODO: Implement a method emtying the text field
        inputField.setText("");
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
