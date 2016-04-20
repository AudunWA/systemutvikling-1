package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.Timesheet;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.TimesheetFactory;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private int loggedInEmployeeId;
    public RegisterTimesheetDialog(int loggedInEmployeeId) {
        this.loggedInEmployeeId = loggedInEmployeeId;
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
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
    private boolean latestTimesheetIsToday(){
        Timesheet sheet = TimesheetFactory.getLatestTimeSheet(loggedInEmployeeId);
        if(sheet!= null && sheet.getToTime()!= null && TimesheetFactory.getUnfinishedTimeSheet(loggedInEmployeeId) == null){
            if(LocalDate.now().isEqual(sheet.getToTime().toLocalDateTime().toLocalDate())){
               return true;
            }else{
               return false;
            }
        }return false;
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
    private Timestamp getSpinnerFromTime(){
        return new Timestamp(((Date)fromSpinner.getModel().getValue()).getTime());
    }
    private Timestamp getSpinnerToTime(){
        return new Timestamp(((Date)toSpinner.getModel().getValue()).getTime());
    }

    // TODO: Check if any date selected has already got a timesheet registered, as we want no double saving.
    private void onOK(){
        Date date = getDate();

        if(date == null){
            JOptionPane.showMessageDialog(this,"A date must be selected");
        } else if (date.after(new Date(System.currentTimeMillis()))) {
            JOptionPane.showMessageDialog(this, "Error, you cannot pre-write hours.");
        }else if(latestTimesheetIsToday()){
            JOptionPane.showMessageDialog(this,"Error, a timesheet has already been registered at this date");
        }
        else{
            // Convert date to LocalDate, which is easier to work with
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Timestamp spinnerFromTime = getSpinnerFromTime();
            Timestamp spinnerToTime = getSpinnerToTime();

            // Extract only hours, and add them to the selected date (from date panel)
            LocalDateTime localFromTime = spinnerFromTime.toLocalDateTime();
            LocalDateTime localToTime = spinnerToTime.toLocalDateTime();

            localFromTime = localDate.atTime(localFromTime.getHour(), localFromTime.getMinute());
            localToTime = localDate.atTime(localToTime.getHour(), localToTime.getMinute());



            if(localToTime.isBefore(localFromTime)){
                JOptionPane.showMessageDialog(this,"Negative hours registered. To-time must be higher than from time");
            }else{
                Timesheet newSheet = TimesheetFactory.createTimesheet(loggedInEmployeeId,Timestamp.valueOf(localFromTime),Timestamp.valueOf(localToTime),true);
                dispose();
            }
        }
    }

    /**
     * Method adding model and editor to JSpinners
     */
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

    /**
     * Test method
     * @param args
     */
    public static void main(String[] args){
        final int HEIGHT = 400, WIDTH = 400;
        GlobalStorage.setLoggedInEmployee(EmployeeFactory.getEmployee("chechter"));
        RegisterTimesheetDialog dialog = new RegisterTimesheetDialog(GlobalStorage.getLoggedInEmployee().getEmployeeId());
        dialog.pack();
        dialog.setSize(WIDTH,HEIGHT);
        dialog.setVisible(true);
        dialog.setTitle("Register timesheet");
        dialog.setLocationRelativeTo(null);
        System.exit(0);
    }
}
