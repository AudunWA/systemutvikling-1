package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.Properties;

/**
 * Created by EliasBrattli on 16/04/2016.
 */
public class EditTimesheetDialog extends JDialog{
    private JPanel componentPanel;
    private JPanel spinnerPanel;
    private JSpinner fromSpinner;
    private JSpinner toSpinner;
    private JLabel topLabel;
    private JLabel bottomLabel;
    private JDatePanelImpl datePanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    public EditTimesheetDialog() {
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
       /* spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                Date date = (Date) ((JSpinner) e.getSource()).getValue();
                for (int i = 0; i < labels.length; i++) {
                    labels[i].setText(formats[i].format(date));
                }
            }
        });*/
        createSpinners();
        okButton.addActionListener(e->{

        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
                              public void windowClosing (WindowEvent e){
                                  onCancel();
                              }
                          }
        );

        // call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(new ActionListener() {
                                             public void actionPerformed (ActionEvent e){
                                                 onCancel();
                                             }
                                         }
                ,KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void onCancel() {
        dispose();
    }

    public void createUIComponents() {
        // TODO: Insert UI components
        createJDatePanel();
    }
    private void createJDatePanel(){
        // Create date pickers
        UtilDateModel model = new UtilDateModel();

        // Dunno
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        datePanel = new JDatePanelImpl(model,p);

    }
    private void onOK(){
        // TODO: Implement onOK, sending hour sheet to database
    }
    private void createSpinners(){
        SpinnerModel fromModel = new SpinnerDateModel();
        SpinnerModel toModel = new SpinnerDateModel();

        fromSpinner.setModel(fromModel);
        toSpinner.setModel(toModel);
        JComponent fromEditor = new JSpinner.DateEditor(fromSpinner,"HH:mm");
        JComponent toEditor = new JSpinner.DateEditor(toSpinner, "HH:mm");
        fromSpinner.setEditor(fromEditor);
        toSpinner.setEditor(toEditor);
        /*if(value != null) {
            timeSpinner.setValue(value);
        }*/

    }
    public static void main(String[] args){
        final int HEIGHT = 400, WIDTH = 400;
        RegisterTimesheetDialog dialog = new RegisterTimesheetDialog();
        dialog.pack();
        dialog.setSize(WIDTH,HEIGHT);
        dialog.setVisible(true);
        dialog.setTitle("Register timesheet");
        dialog.setLocationRelativeTo(null);
        System.exit(0);
    }
}
