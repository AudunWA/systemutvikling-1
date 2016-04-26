package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.Timesheet;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.TimesheetFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditTimesheetDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.RegisterTimesheetDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.TimesheetTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Gives you options for adding, removing or editing "Timesheets"
 */

public class TimesheetView extends JPanel {

    private JPanel mainPanel;
    private JTable hoursTable;
    private JButton editButton;
    private JButton clockInButton;
    private JButton clockOutButton;
    private JButton clockManuallyButton;
    private JButton removeButton;
    private JButton refreshButton;
    private JCheckBox showInactiveCB;
    private TimesheetTableModel tableModel;
    private ArrayList<Timesheet> timesheetList;
    private int loggedInEmployeeId;
    private Timesheet unFinishedSheet;

    /**
     * Constructor used when initializing as a tab on the dashboard. Invoked with reflection.
     */
    @SuppressWarnings("unused")
    public TimesheetView() {
        this.loggedInEmployeeId = GlobalStorage.getLoggedInEmployee().getEmployeeId();

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        checkUnfinishedTimesheet();
        checkLatestTimesheet();
        showInactiveCB.setVisible(false);
        refresh();
        addActionListeners();
    }

    /**
     Constructor used for admins, creates the view for a specified employee.
     * @param loggedInEmployeeId THe ID of the specified employee.
     */
    public TimesheetView(int loggedInEmployeeId) {
        this.loggedInEmployeeId = loggedInEmployeeId;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        checkUnfinishedTimesheet();
        checkLatestTimesheet();
        showInactiveCB.setVisible(false);
        refresh();
        addActionListeners();
    }

    /**
     * Adds action listeners to our components.
     */
    private void addActionListeners() {
        editButton.addActionListener(e -> editTimesheet(getSelectedTimesheet()));
        clockInButton.addActionListener(e -> clockIn());
        clockOutButton.addActionListener(e -> clockOut());
        clockManuallyButton.addActionListener(e -> registerTimesheet());
        removeButton.addActionListener(e -> removeTimesheet(getSelectedTimesheet()));
        refreshButton.addActionListener(e -> refresh());
        showInactiveCB.addActionListener(e -> refresh());
    }

    /**
     * Entry point for isolated testing of this view.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Window dimensions
        final int WIDTH = 550, HEIGHT = 550;
        GlobalStorage.setLoggedInEmployee(EmployeeFactory.getEmployee("chechter"));
        JFrame frame = new JFrame();
        frame.add(new TimesheetView(GlobalStorage.getLoggedInEmployee().getEmployeeId()));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        createTable();
    }

    private void createTable() {
        Integer[] columns;
        if (GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR) {
            timesheetList = getTimesheetsByEmployeeId();
            columns = new Integer[]{TimesheetTableModel.COLUMN_HOURS_ID, TimesheetTableModel.COLUMN_FROM_TIME, TimesheetTableModel.COLUMN_TO_TIME, TimesheetTableModel.COLUMN_ACTIVE};
        } else {
            timesheetList = getActiveTimesheetsByEmployeeId();
            columns = new Integer[]{TimesheetTableModel.COLUMN_HOURS_ID, TimesheetTableModel.COLUMN_FROM_TIME, TimesheetTableModel.COLUMN_TO_TIME};
        }
        tableModel = new TimesheetTableModel(timesheetList, columns);
        hoursTable = new JTable(tableModel);
        hoursTable.getTableHeader().setReorderingAllowed(false);
        //hoursTable.setCellEditor(editor);
        //TableColumn column = hoursTable.getColumnModel().getColumn(TimesheetTableModel.COLUMN_ACTIVE);
        //column.setCellEditor(new CellCheckboxEditor());
        hoursTable.setFillsViewportHeight(true);
    }

    /**
     * When clocked in, an unfinished time sheet is sent to database, consisting  null column(End-time)
     * This method searches and gets access to given time sheet, completing it should its date have passed
     */
    private void checkUnfinishedTimesheet() {
        unFinishedSheet = TimesheetFactory.getLatestTimeSheet(loggedInEmployeeId);

        //If last Clock-in hasn't been clocked out, clock out. This means that "To-time" has the semantic value null
        if (unFinishedSheet != null && unFinishedSheet.getToTime() == null) {

            if (new Timestamp(System.currentTimeMillis()).after(unFinishedSheet.getFromTime())) {
                LocalDateTime dateTime = unFinishedSheet.getFromTime().toLocalDateTime().toLocalDate().atTime(23, 59);
                Timestamp endOfDay = Timestamp.valueOf(dateTime);
                unFinishedSheet.setToTime(endOfDay);
                clockOut(unFinishedSheet); // Clock out automatically to the end of previous day, should not the user have clocked out himself.
            } else {
                clockInButton.setEnabled(false);
                clockOutButton.setEnabled(true);
            }
        } else {
            clockOutButton.setEnabled(false);
            clockInButton.setEnabled(true);
        }
    }

    /**
     * Gives access to user selection. If no object is selected, a null value is returned
     * @return Timesheet Selected object in table
     */
    private Timesheet getSelectedTimesheet() {
        int selectedRow = hoursTable.getSelectedRow();
        if (selectedRow > -1) {
            Timesheet timesheet = tableModel.getValue(selectedRow);
            return timesheet;
        }
        return null;
    }

    /**
     * Checks the latest registered time sheet, sorted on date.
     * Controls that user is unable to register hours any more than once a day.
     */
    private void checkLatestTimesheet() {
        Timesheet sheet = TimesheetFactory.getLatestTimeSheet(loggedInEmployeeId);
        if (sheet != null && sheet.getToTime() != null) {
            clockManuallyButton.setEnabled(true);
            if (LocalDate.now().isEqual(sheet.getToTime().toLocalDateTime().toLocalDate())) {
                clockInButton.setEnabled(false);
                clockOutButton.setEnabled(false);
            } else {
                clockInButton.setEnabled(true);
                clockOutButton.setEnabled(false);
            }
        }
    }

    /**
     * Opens a dialog enabling user to edit certain values in a Timesheet object, thus changing value in a table row
     * @param timesheet Selected time sheet from table
     */
    private void editTimesheet(Timesheet timesheet) {
        if (timesheet == null) {
            JOptionPane.showMessageDialog(null, "Please select a table row");
        } else {
            final int HEIGHT = 400, WIDTH = 400;
            GlobalStorage.setLoggedInEmployee(EmployeeFactory.getEmployee("chechter"));
            EditTimesheetDialog dialog = new EditTimesheetDialog(loggedInEmployeeId, timesheet);
            dialog.pack();
            dialog.setSize(WIDTH, HEIGHT);
            dialog.setLocationRelativeTo(dialog.getParent());
            dialog.setVisible(true);
            dialog.setTitle("Edit timesheet");
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            refresh();
        }
    }

    /**
     * Clocks user in on current system time and date. Sends a Timesheet containing an empty "to_time" column.
     * User is at any time after able to clock out with a clockout button
     * @See clockOut()
     */
    private void clockIn() {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Timesheet sheet = TimesheetFactory.createTimesheet(loggedInEmployeeId, time, true);
        if (sheet != null) {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Clocked in.", Toast.Style.SUCCESS).display();

        } else {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Something went wrong", Toast.Style.ERROR).display();
        }
        refresh();
        clockOutButton.setEnabled(true);
        clockInButton.setEnabled(false);
        clockManuallyButton.setEnabled(false);
    }

    /**
     * Method clockOut updates and completes a time sheet
     *
     * @See clockIn()
     */
    private void clockOut() {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Timesheet sheet = TimesheetFactory.getLatestTimeSheet(loggedInEmployeeId);
        if (sheet != null && sheet.getToTime() == null) {
            sheet.setToTime(time);
            int result = TimesheetFactory.updateTimesheet(sheet);
            //Result gives back affected rows, which will be 1 when dealing with a single object
            if (result == 1) {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Clocked out.", Toast.Style.SUCCESS).display();
            } else {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Something went wrong", Toast.Style.ERROR).display();
            }
            refresh();
            checkLatestTimesheet();
        } else {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Something went wrong", Toast.Style.ERROR).display();
        }
    }

    /**
     * Method clockOut updates and completes a time sheet
     *
     * @See clockIn()
     */
    private void clockOut(Timesheet sheet) {
        // TODO: Use current time, register to-time
        if (sheet != null) {
            int result = TimesheetFactory.updateTimesheet(sheet);
            if (result == 1) {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Clocked out.", Toast.Style.SUCCESS).display();
            } else {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Something went wrong", Toast.Style.ERROR).display();
            }
            refresh();
            clockOutButton.setEnabled(false);
            clockInButton.setEnabled(true);
        } else {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Something went wrong", Toast.Style.ERROR).display();
        }
    }

    /**
     * Adding a new row to table, from user input.
     */
    private void registerTimesheet() {
        final int HEIGHT = 400, WIDTH = 400;
        RegisterTimesheetDialog dialog = new RegisterTimesheetDialog(loggedInEmployeeId);
        dialog.pack();
        dialog.setSize(WIDTH, HEIGHT);
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setTitle("Register timesheet");
        dialog.setLocationRelativeTo(null);
        if (dialog.isRegistered()) {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "New timesheet registered", Toast.Style.SUCCESS).display();
        } else {
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Registration failed", Toast.Style.ERROR).display();
        }
        refresh();
    }

    /**
     * Sets boolean active in status column in selected row to false, thus hiding it from the user.
     * @param timesheet Selected row from table
     */
    private void removeTimesheet(Timesheet timesheet) {
        if (timesheet == null) {
            JOptionPane.showMessageDialog(null, "Please select a table row");
        } else {
            JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this row?");
            TimesheetFactory.editTimesheetStatus(timesheet.getTimesheetId(), timesheet.getEmployeeId(), false);
            refresh();
        }
    }

    /**
     * Shows user active time sheets, which would be time sheet that aren't deleted
     * @return ArrayList<Timesheet> Containing all objects with value true in column "active"
     */
    private ArrayList<Timesheet> getActiveTimesheetsByEmployeeId() {
        return TimesheetFactory.getActiveTimesheetsByEmployee(loggedInEmployeeId);
    }

    /**
     * Shows admin user inactive(deleted) should he choose to view them
     * @return ArrayList<Timesheet>
     */
    private ArrayList<Timesheet> getTimesheetsByEmployeeId() {
        return TimesheetFactory.getTimesheetsByEmployee(loggedInEmployeeId);
    }

    /**
     * Updates components, reloading table contents from database
     */
    private void refresh() {
        checkLatestTimesheet();
        if (GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR) {
            showInactiveCB.setVisible(true);
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Customers refreshed.").display();
            if (showInactiveCB.isSelected()) {
                tableModel.setRows(getTimesheetsByEmployeeId());
            } else {
                tableModel.setRows(getActiveTimesheetsByEmployeeId());
            }

        } else {
            showInactiveCB.setVisible(false);
            tableModel.setRows(getActiveTimesheetsByEmployeeId());
        }
    }
}
