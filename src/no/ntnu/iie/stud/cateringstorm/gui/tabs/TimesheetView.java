package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.Timesheet;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.TimesheetFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.TimesheetTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.table.TableCellEditor;

/**
 * Created by EliasBrattli on 14/04/2016.
 * TODO: Make a checkbox to show or hide active timesheets, which is to be enabled visible only if admin is logged in
 * TODO: All work sessions must be auto-clocked out at the end of a day, if a new date as arrived
 * FIXME: Somehow, only Date, and not Date + time is displayed in table.
 */

public class TimesheetView extends JPanel{

    private JPanel mainPanel;
    private JPanel selectButtonPanel;
    private JPanel noSelectButtonPanel;
    private JTable hoursTable;
    private JButton editButton;
    private JButton clockInButton;
    private JButton clockOutButton;
    private JLabel infoLabel1;
    private JLabel infoLabel2;
    private JButton clockManuallyButton;
    private JButton removeButton;
    private JScrollPane tablePane;
    private JLabel infoLabel3;
    private JButton refreshButton;
    private TimesheetTableModel tableModel;
    private ArrayList<Timesheet> timesheetList;
    private int loggedInEmployeeId;
    private Timesheet unFinishedSheet;
    //Constructor
    public TimesheetView(int loggedInEmployeeId) {
        this.loggedInEmployeeId = loggedInEmployeeId;
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        checkUnfinishedTimesheet();
        checkLatestTimesheet();
        editButton.addActionListener(e -> {
            editTimesheet(getSelectedTimesheet());
        });
        clockInButton.addActionListener(e -> {
            clockIn();
        });
        clockOutButton.addActionListener(e -> {
            clockOut();
        });
        clockManuallyButton.addActionListener(e -> {
            registerTimesheet();
        });
        removeButton.addActionListener(e -> {
            removeTimesheet(getSelectedTimesheet());
        });
        refreshButton.addActionListener(e->{
            refresh();
        });
        hoursTable.getSelectionModel().addListSelectionListener(e -> {
            //Get index from selected row
        });


    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
    }
    private void createTable(){
        //timesheetList = TimesheetFactory.getAllTimesheets();
        System.out.println(GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR);
        Integer[] columns;
        if(GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR) {
            timesheetList = getTimesheetsByEmployeeId();
            columns = new Integer[]{TimesheetTableModel.COLUMN_HOURS_ID, TimesheetTableModel.COLUMN_FROM_TIME, TimesheetTableModel.COLUMN_TO_TIME, TimesheetTableModel.COLUMN_ACTIVE};
        }else{
            timesheetList = getActiveTimesheetsByEmployeeId();
            columns = new Integer[]{ TimesheetTableModel.COLUMN_HOURS_ID, TimesheetTableModel.COLUMN_FROM_TIME, TimesheetTableModel.COLUMN_TO_TIME};
        }
        //System.out.println(timesheetList.get(0));
        tableModel = new TimesheetTableModel(timesheetList, columns);
        hoursTable = new JTable(tableModel);
        hoursTable.getTableHeader().setReorderingAllowed(false);
        tablePane = new JScrollPane(hoursTable);
        //hoursTable.setCellEditor(editor);
        //TableColumn column = hoursTable.getColumnModel().getColumn(TimesheetTableModel.COLUMN_ACTIVE);
        //column.setCellEditor(new CellCheckboxEditor());
        hoursTable.setFillsViewportHeight(true);
    }

    private void checkUnfinishedTimesheet(){
        unFinishedSheet = TimesheetFactory.getUnfinishedTimeSheet(loggedInEmployeeId);

        //If last Clock-in hasn't been clocked out, clock out.
        if(unFinishedSheet != null){

            if(new Timestamp(System.currentTimeMillis()).after(unFinishedSheet.getFromTime())){
                LocalDateTime dateTime = unFinishedSheet.getFromTime().toLocalDateTime().toLocalDate().atTime(23,59);
                Timestamp endOfDay = Timestamp.valueOf(dateTime);
                unFinishedSheet.setToTime(endOfDay);
                clockOut(unFinishedSheet);
            }else{
                clockInButton.setEnabled(false);
                clockOutButton.setEnabled(true);
            }
        }else {
            clockOutButton.setEnabled(false);
            clockInButton.setEnabled(true);
        }
    }

    private Timesheet getSelectedTimesheet(){
        int selectedRow = hoursTable.getSelectedRow();
        if(selectedRow > -1){
            Timesheet timesheet = tableModel.getValue(selectedRow);
        }
        return null;
    }
    private void checkLatestTimesheet(){
        Timesheet sheet = TimesheetFactory.getLatestTimeSheet(loggedInEmployeeId);
        System.out.println("sheet: "+sheet);
        if(sheet!= null && sheet.getToTime()!= null && TimesheetFactory.getUnfinishedTimeSheet(loggedInEmployeeId) == null){
            if(LocalDate.now().isEqual(sheet.getToTime().toLocalDateTime().toLocalDate())){
                clockInButton.setEnabled(false);
                clockOutButton.setEnabled(false);
                clockManuallyButton.setEnabled(false);
            }else{
                clockInButton.setEnabled(true);
                clockOutButton.setEnabled(false);
                clockManuallyButton.setEnabled(true);
            }
        }
    }
    private void editTimesheet(Timesheet timesheet){
        // TODO: Open EditTimesheetDialog
        if(timesheet == null){
            JOptionPane.showMessageDialog(null,"Please select a table row");
        }
    }

    private void clockIn(){
        // TODO: Use current time, register to from-Time
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Timesheet sheet = TimesheetFactory.createTimesheet(loggedInEmployeeId,time,true);
        if(sheet != null) {
            //JOptionPane.showMessageDialog(null, sheet);
            Toast.makeText((JFrame)SwingUtilities.getWindowAncestor(this),"Clocked in.", Toast.Style.SUCCESS).display();

        }else{
            Toast.makeText((JFrame)SwingUtilities.getWindowAncestor(this),"Something went wrong", Toast.Style.ERROR).display();
        }
        refresh();
        clockOutButton.setEnabled(true);
        clockInButton.setEnabled(false);
        clockManuallyButton.setEnabled(false);
    }

    /**
     * Method clockOut updates and completes a time sheet
     * @See clockIn()
     */
    private void clockOut(){
        // TODO: Use current time, register to-time
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Timesheet sheet = TimesheetFactory.getUnfinishedTimeSheet(loggedInEmployeeId);
        if(sheet != null) {
            sheet.setToTime(time);
            int result = TimesheetFactory.updateTimesheet(sheet);
            if (result == 1) {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Clocked out.", Toast.Style.SUCCESS).display();
            } else {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Something went wrong", Toast.Style.ERROR).display();
            }
            refresh();
            checkLatestTimesheet();
        }else{
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Something went wrong", Toast.Style.ERROR).display();
        }
    }
    /**
     * Method clockOut updates and completes a time sheet
     * @See clockIn()
     */
    private void clockOut(Timesheet sheet){
        // TODO: Use current time, register to-time
        if(sheet != null) {
            int result = TimesheetFactory.updateTimesheet(sheet);
            if (result == 1) {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Clocked out.", Toast.Style.SUCCESS).display();
            } else {
                Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Something went wrong", Toast.Style.ERROR).display();
            }
            refresh();
            clockOutButton.setEnabled(false);
            clockInButton.setEnabled(true);
        }else{
            Toast.makeText((JFrame) SwingUtilities.getWindowAncestor(this), "Something went wrong", Toast.Style.ERROR).display();
        }
    }
    private void registerTimesheet(){
        // TODO: Open RegisterTimesheetDialog
    }
    private void removeTimesheet(Timesheet timesheet){
        // TODO: set Status of a time sheet to inactive. It's accessible to admin
        if(timesheet == null){
            JOptionPane.showMessageDialog(null,"Please select a table row");
        }
    }
    private ArrayList<Timesheet> getActiveTimesheetsByEmployeeId(){
        return TimesheetFactory.getActiveTimesheetsByEmployee(loggedInEmployeeId);
    }
    private ArrayList<Timesheet> getTimesheetsByEmployeeId(){
        return TimesheetFactory.getTimesheetsByEmployee(loggedInEmployeeId);
    }
    private void refresh(){
        if(GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR) {
            tableModel.setRows(getTimesheetsByEmployeeId());
            checkLatestTimesheet();
        }else{
            tableModel.setRows(getActiveTimesheetsByEmployeeId());
            checkLatestTimesheet();
        }
    }
    public int getLoggedInEmployeeId() {
        return loggedInEmployeeId;
    }

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

    /**
     * Class is intended to register changes in boolean value "active" in table, rendered by a checkbox
     */
    // TODO: Help implementing the class
    private class CellCheckboxEditor extends AbstractCellEditor implements TableCellEditor,ActionListener {
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return null;
        }

        @Override
        public Object getCellEditorValue(){
            return null;
        }
        @Override
        public void actionPerformed (ActionEvent e){

        }
    }
}
