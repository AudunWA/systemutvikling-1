package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.Timesheet;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.TimesheetFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.TimesheetTableModel;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.table.TableCellEditor;

/**
 * Created by EliasBrattli on 14/04/2016.
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
    //Constructor
    public TimesheetView(int loggedInEmployeeId) {
        this.loggedInEmployeeId = loggedInEmployeeId;
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        clockOutButton.setEnabled(false);
        editButton.addActionListener(e -> {
            editTimesheet(getSelectedHours());
        });
        clockInButton.addActionListener(e -> {
            clockIn();
        });
        clockOutButton.addActionListener(e -> {
            clockOut();
        });
        clockManuallyButton.addActionListener(e -> {
            registerHours();
        });
        removeButton.addActionListener(e -> {
            removeTimesheet(getSelectedHours());
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
            timesheetList = getHoursByEmployeeId();
            columns = new Integer[]{TimesheetTableModel.COLUMN_HOURS_ID, TimesheetTableModel.COLUMN_START_TIME, TimesheetTableModel.COLUMN_END_TIME, TimesheetTableModel.COLUMN_ACTIVE};
        }else{
            timesheetList = getActiveHoursByEmployeeId();
            columns = new Integer[]{ TimesheetTableModel.COLUMN_HOURS_ID, TimesheetTableModel.COLUMN_START_TIME, TimesheetTableModel.COLUMN_END_TIME};
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
    private Timesheet getSelectedHours(){
        int selectedRow = hoursTable.getSelectedRow();
        if(selectedRow > -1){
            Timesheet timesheet = tableModel.getValue(selectedRow);
        }
        return null;
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
        JOptionPane.showMessageDialog(null,sheet);
        refresh();
        clockOutButton.setEnabled(true);
        clockInButton.setEnabled(false);
    }
    private void clockOut(){
        // TODO: Use current time, register to-time
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Timesheet sheet = TimesheetFactory.getUnfinishedTimeSheet(loggedInEmployeeId);
        sheet.setEndTime(time);
        int result = TimesheetFactory.updateTimesheet(sheet);
        JOptionPane.showMessageDialog(null,result);
        refresh();
        clockOutButton.setEnabled(false);
        clockInButton.setEnabled(true);
    }
    private void registerHours(){
        // TODO: Open RegisterTimesheetDialog
    }
    private void removeTimesheet(Timesheet timesheet){
        // TODO: set Status of a time sheet to inactive. It's accessible to admin
        if(timesheet == null){
            JOptionPane.showMessageDialog(null,"Please select a table row");
        }
    }
    private ArrayList<Timesheet> getActiveHoursByEmployeeId(){
        return TimesheetFactory.getActiveTimesheetsByEmployee(loggedInEmployeeId);
    }
    private ArrayList<Timesheet> getHoursByEmployeeId(){
        return TimesheetFactory.getTimesheetsByEmployee(loggedInEmployeeId);
    }
    private void refresh(){
        if(GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR) {
            tableModel.setRows(getHoursByEmployeeId());
        }else{
            tableModel.setRows(getActiveHoursByEmployeeId());
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
