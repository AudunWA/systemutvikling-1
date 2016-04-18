package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;
import no.ntnu.iie.stud.cateringstorm.entities.hours.Hours;
import no.ntnu.iie.stud.cateringstorm.entities.hours.HoursFactory;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.HoursTableModel;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Objects;
import javax.swing.event.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public class HoursView extends JPanel{

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
    private HoursTableModel tableModel;
    private ArrayList<Hours> hoursList;
    private int loggedInEmployeeId;
    //Constructor
    public HoursView(int loggedInEmployeeId) {
        this.loggedInEmployeeId = loggedInEmployeeId;
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        System.out.printf(loggedInEmployeeId+"\n");
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
        //hoursList = HoursFactory.getAllHours();
        System.out.println(GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR);
        Integer[] columns;
        if(GlobalStorage.getLoggedInEmployee().getEmployeeType() == EmployeeType.ADMINISTRATOR) {
            hoursList = getHoursByEmployeeId();
            columns = new Integer[]{HoursTableModel.COLUMN_HOURS_ID, HoursTableModel.COLUMN_START_TIME, HoursTableModel.COLUMN_END_TIME, HoursTableModel.COLUMN_ACTIVE};
        }else{
            hoursList = getActiveHoursByEmployeeId();
            columns = new Integer[]{ HoursTableModel.COLUMN_HOURS_ID,HoursTableModel.COLUMN_START_TIME, HoursTableModel.COLUMN_END_TIME};
        }
        //System.out.println(hoursList.get(0));
        tableModel = new HoursTableModel(hoursList, columns);
        hoursTable = new JTable(tableModel);
        hoursTable.getTableHeader().setReorderingAllowed(false);
        tablePane = new JScrollPane(hoursTable);
        //hoursTable.setCellEditor(editor);
        //TableColumn column = hoursTable.getColumnModel().getColumn(HoursTableModel.COLUMN_ACTIVE);
        //column.setCellEditor(new CellCheckboxEditor());
        hoursTable.setFillsViewportHeight(true);
    }
    private Hours getSelectedHours(){
        int selectedRow = hoursTable.getSelectedRow();
        if(selectedRow > -1){
            Hours hours = tableModel.getValue(selectedRow);
        }
        return null;
    }
    private void editTimesheet(Hours hours){
        // TODO: Open EditHoursDialog
        if(hours == null){
            JOptionPane.showMessageDialog(null,"Please select a table row");
        }
    }

    private void clockIn(){
        // TODO: Use current time, register to from-Time
    }
    private void clockOut(){
        // TODO: Use current time, register to-time
    }
    private void registerHours(){
        // TODO: Open RegisterHoursDialog
    }
    private void removeTimesheet(Hours hours){
        // TODO: set Status of a time sheet to inactive. It's accessible to admin
        if(hours == null){
            JOptionPane.showMessageDialog(null,"Please select a table row");
        }
    }
    private ArrayList<Hours> getActiveHoursByEmployeeId(){
        return HoursFactory.getActiveHoursByEmployee(loggedInEmployeeId);
    }
    private ArrayList<Hours> getHoursByEmployeeId(){
        return HoursFactory.getHoursByEmployee(loggedInEmployeeId);
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
        frame.add(new HoursView(GlobalStorage.getLoggedInEmployee().getEmployeeId()));
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
