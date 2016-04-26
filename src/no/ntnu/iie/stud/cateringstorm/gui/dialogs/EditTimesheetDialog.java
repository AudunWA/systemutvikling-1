package no.ntnu.iie.stud.cateringstorm.gui.dialogs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.Timesheet;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.TimesheetFactory;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * GUI Dialog for editing an existing timesheet in the database
 */
public class EditTimesheetDialog extends JDialog {
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
    private int loggedInEmployeeId;
    private Timesheet selectedTimesheet;
    private ArrayList<Timesheet> timesheets;

    public EditTimesheetDialog(int loggedInEmployeeId, Timesheet selectedTimesheet) {
        this.loggedInEmployeeId = loggedInEmployeeId;
        this.selectedTimesheet = selectedTimesheet;
        setTitle("Edit time sheet");
        timesheets = TimesheetFactory.getActiveTimesheetsByEmployee(loggedInEmployeeId);
        setContentPane(mainPanel);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        setSpinners();
        addActionListeners();
    }

    public static void main(String[] args) {
        final int HEIGHT = 400, WIDTH = 400;
        GlobalStorage.setLoggedInEmployee(EmployeeFactory.getEmployee("chechter"));
        int id = GlobalStorage.getLoggedInEmployee().getEmployeeId();
        EditTimesheetDialog dialog = new EditTimesheetDialog(id, TimesheetFactory.getLatestTimeSheet(id));
        dialog.pack();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setVisible(true);
        dialog.setTitle("Register timesheet");
        dialog.setLocationRelativeTo(null);
        System.exit(0);
    }

    private void addActionListeners() {
        okButton.addActionListener(e -> onOK());

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

    public int getLoggedInEmployeeId() {
        return loggedInEmployeeId;
    }

    public Timesheet getSelectedTimesheet() {
        return selectedTimesheet;
    }

    /**
     * Called when cancel button, escape or the cross is pressed
     */
    private void onCancel() {
        dispose();
    }

    public void createUIComponents() {
        createJDatePanel();
    }

    private void createJDatePanel() {
        // Create date pickers
        UtilDateModel model = new UtilDateModel();
        Date date = selectedTimesheet.getFromTime();
        model.setValue(date);
        // Dunno
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        datePanel = new JDatePanelImpl(model, p);

    }

    private boolean sheetHasBeenRegistered(Timesheet sheet) {
        Date selectedDate = getDate();
        if (sheet != null && sheet.getToTime() != null && selectedDate != null) {
            LocalDate localSelectedDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate sheetDate = sheet.getFromTime().toLocalDateTime().toLocalDate();
            return localSelectedDate.isEqual(sheetDate);
        }
        return false;
    }

    private boolean selectedDateIsTaken() {
        for (int i = 0; i < timesheets.size(); i++) {
            if (sheetHasBeenRegistered(timesheets.get(i))) {
                return true;
            }
        }
        return false;
    }

    private Date getDate() {
        return (Date) datePanel.getModel().getValue();
    }

    private Timestamp getSpinnerFromTime() {
        return new Timestamp(((Date) fromSpinner.getModel().getValue()).getTime());
    }

    private Timestamp getSpinnerToTime() {
        return new Timestamp(((Date) toSpinner.getModel().getValue()).getTime());
    }

    /**
     * Called when ok buttons is pressed
     * Updates and saves the changes to the existing Timesheet
     */
    private void onOK() {
        Date date = getDate();
        Date selectedDate = selectedTimesheet.getFromTime();
        if (date == null) {
            JOptionPane.showMessageDialog(this, "A date must be selected");
        } else {
            // Convert date to LocalDate, which is easier to work with
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate selectedLocaldate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Timestamp spinnerFromTime = getSpinnerFromTime();
            Timestamp spinnerToTime = getSpinnerToTime();

            // Extract only hours, and add them to the selected date (from date panel)
            LocalDateTime localFromTime = spinnerFromTime.toLocalDateTime();
            LocalDateTime localToTime = spinnerToTime.toLocalDateTime();

            localFromTime = localDate.atTime(localFromTime.getHour(), localFromTime.getMinute());
            localToTime = localDate.atTime(localToTime.getHour(), localToTime.getMinute());

            if (selectedDateIsTaken() && (!selectedLocaldate.isEqual(localDate))) {
                JOptionPane.showMessageDialog(this, "Error, a timesheet has already been registered at selected date");
            } else if (localToTime.isBefore(localFromTime)) {
                JOptionPane.showMessageDialog(this, "Negative hours registered. To-time must be higher than from time");
            } else if (localFromTime.isAfter(LocalDateTime.now()) || localToTime.isAfter(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(this, "Error, you cannot pre-write hours.");
            } else {
                selectedTimesheet.setFromTime(Timestamp.valueOf(localFromTime));
                selectedTimesheet.setToTime(Timestamp.valueOf(localToTime));
                int result = TimesheetFactory.updateTimesheet(selectedTimesheet);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, selectedTimesheet);
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong");
                }
                dispose();
            }
        }
    }

    private void setSpinners() {
        SpinnerModel fromModel = new SpinnerDateModel();
        SpinnerModel toModel = new SpinnerDateModel();
        Date fromDate = selectedTimesheet.getFromTime();
        Date toDate = selectedTimesheet.getToTime();
        if (toDate != null) {
            fromModel.setValue(fromDate);
            toModel.setValue(toDate);
        } else {
            fromModel.setValue(fromDate);
            toModel.setValue(new Date(System.currentTimeMillis()));
        }
        fromSpinner.setModel(fromModel);
        toSpinner.setModel(toModel);
        JComponent fromEditor = new JSpinner.DateEditor(fromSpinner, "HH:mm");
        JComponent toEditor = new JSpinner.DateEditor(toSpinner, "HH:mm");
        fromSpinner.setEditor(fromEditor);
        toSpinner.setEditor(toEditor);
        /*if(value != null) {
            timeSpinner.setValue(value);
        }*/

    }
}
