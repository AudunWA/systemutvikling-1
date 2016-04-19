package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public class RegisterTimesheetDialog extends JDialog {
    private JPanel mainPanel;
    private JPanel componentPanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel spinnerPanel;
    private JSpinner toSpinner;
    private JSpinner fromSpinner;
    private JLabel topLabel;
    private JLabel bottomLabel;
    private JDatePanelImpl datePanel;

    public RegisterTimesheetDialog() {
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
        setSpinners();
        okButton.addActionListener(e->{
            onOK();
        });
        cancelButton.addActionListener(e->{
            onCancel();
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
    private Date getDate(){
        return (Date)datePanel.getModel().getValue();
    }
    private Timestamp getFromTime(){
        return new Timestamp(((Date)fromSpinner.getModel().getValue()).getTime());
    }
    private Timestamp getToTime(){
        return new Timestamp(((Date)toSpinner.getModel().getValue()).getTime());
    }

    private void onOK(){
        Date date = getDate();

        if(date == null){
            JOptionPane.showMessageDialog(this,"A date must be selected");
        } else if (date.after(new Date(System.currentTimeMillis()))) {
            JOptionPane.showMessageDialog(this, "Error, you cannot pre-write hours.");
            return;
        }
        // Convert date to LocalDate, which is easier to work with
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Timestamp fromTime = getFromTime();
        Timestamp toTime = getToTime();

        // Extract only hours, and add them to the selected date (from date panel)
        LocalDateTime localFromTime = fromTime.toLocalDateTime();
        localFromTime = localDate.atTime(localFromTime.getHour(), localFromTime.getMinute());
        LocalDateTime localToTime = toTime.toLocalDateTime();
        localToTime = localDate.atTime(localToTime.getHour(), localToTime.getMinute());

        System.out.println("localFromTime :" + localFromTime);
        System.out.println("localToTime :" + localToTime);

        if(localToTime.isBefore(localFromTime)){
            JOptionPane.showMessageDialog(this,"Negative hours registered. To-time must be higher than from time");
            return;
        }

        // TODO: Implement onOK, sending hour sheet to database
    }
    private void setSpinners(){
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
