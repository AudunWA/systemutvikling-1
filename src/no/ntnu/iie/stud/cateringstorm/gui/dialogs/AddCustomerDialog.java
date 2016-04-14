package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by EliasBrattli on 06/04/2016.
 */
public class AddCustomerDialog extends JDialog{
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JPanel textPanel;
    private JTextField forenameField;
    private JButton saveButton;
    private JTextField surnameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JButton cancelButton;
    private JTextField emailField;
    private JLabel forenameLabel;
    private JLabel surnameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private boolean addedNewValue;
    public AddCustomerDialog(){
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);// Consider not using this for safety reasons
        setLocationRelativeTo(getParent());
        /*setComponentOrientation(((parent == null) ?
                getRootPane() : parent).getComponentOrientation());*/
        addedNewValue = false;
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
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


    private void onOK() {
        String forename = forenameField.getText();
        String surname = surnameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (forename.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in a forename");
            return;
        }
        if(surname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill in a surname");
            return;
        }
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in an address.");
            return;
        }

        if(phone.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill in a phone number.");
            return;
        }
        if(email.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill in an email.");
            return;
        }
        Customer customer = CustomerFactory.createCustomer(surname, forename,address,true,phone,email);
        addedNewValue = true;
        if (customer == null) {
            JOptionPane.showMessageDialog(this, "An error occurred, please try again later.");
        } else {
            // Debug code
            JOptionPane.showMessageDialog(this, customer);
        }
        dispose();
    }
    public boolean hasAddedNewValue(){
        return addedNewValue;
    }
    private void onCancel() {
        dispose();
    }
    public static void main(String[] args) {
        final int WIDTH = 320;
        final int HEIGHT = 400;
        AddCustomerDialog dialog = new AddCustomerDialog();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(dialog.getParent());
        System.exit(0);

    }
}
