/*package no.ntnu.iie.stud.cateringstorm.gui.tabs;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.Timesheet;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.TimesheetFactory;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.RegisterTimesheetDialog;
import no.ntnu.iie.stud.cateringstorm.gui.dialogs.EditTimesheetDialog;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.TimesheetTableModel;
import no.ntnu.iie.stud.cateringstorm.gui.util.Toast;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import java.util.ArrayList;

/**
 * Created by Håvard

public class EmployeeView extends JPanel{
    private JPanel mainPanel;
    private JTable timesheetTable;
    private JButton refreshButton;
    private JButton registerTimesheetButton;
    private JButton editTimesheetButton;
    private JTextField searchField;
    private TimesheetTableModel timesheetModel;
    private JScrollPane timesheetPane;
    private JPanel noSelectButtonPanel;

    private ArrayList<Timesheet> timesheetList;


    public EmployeeView(){
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        refreshButton.addActionListener(e -> {
            refresh();
        });

        /*registerTimesheetButton.addActionListener(e -> {
            registerTimesheet();
        });

        editTimesheetButton.addActionListener(e -> {
            editTimesheet(getSelectedTimesheet());
        });

        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchField.setText("");
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchDocument();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchDocument();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchDocument();
            }

            public void searchDocument(){
                /*ArrayList<Timesheet> copy = new ArrayList<>();

                for(int i = 0; i < timesheetList.size(); i++){

                    }
                }



        });

    }

    /*private void registerTimesheet(Timesheet timesheet){
        RegisterTimesheetDialog rtDialog = new RegisterTimesheetDialog(timesheet);
        final int WIDTH = 300;
        final int HEIGHT = 300;
        rtDialog.pack();
        rtDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        rtDialog.setSize(WIDTH, HEIGHT);
        rtDialog.setLocationRelativeTo(rtDialog.getParent());

        rtDialog.setVisible(true);
        timesheetList = TimesheetFactory.getAllTimesheets();
    }

    private Timesheet getSelectedTimesheet(){
        return timesheetModel.getValue(timesheetTable.getSelectedRow());
    }

    private void editTimesheet(Timesheet timesheet){
        if(timesheet != null){
            EditTimesheetDialog etDialog = new EditTimesheetDialog();
            final int WIDTH = 300;
            final int HEIGHT = 200;
            etDialog.pack();
            etDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            etDialog.setSize(WIDTH, HEIGHT);
            etDialog.setLocationRelativeTo(etDialog.getParent());

            etDialog.setVisible(true);
        } else{
            JOptionPane.showMessageDialog(this, "Please select a row in the timesheet table.");
        }
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        createTable();
        createSearchField();
    }

    private void createTable(){
        timesheetList = TimesheetFactory.getAllTimesheets();
        Integer[] columns = new Integer[]{TimesheetTableModel.COLUMN_EMPLOYEE_ID, TimesheetTableModel.COLUMN_ACTIVE, TimesheetTableModel.COLUMN_FROM_TIME, TimesheetTableModel.COLUMN_TO_TIME, TimesheetTableModel.COLUMN_HOURS_ID};
        timesheetModel = new TimesheetTableModel(timesheetList, columns);
        timesheetTable.getTableHeader().setReorderingAllowed(false);
        timesheetPane = new JScrollPane(timesheetTable);
        timesheetTable.setFillsViewportHeight(true);
    }

    private void createSearchField(){
        searchField = new JTextField(20);
        setSearchField("Search by employee name");
        add(searchField);
    }

    private void setSearchField(String text){
        searchField.setText(text);
        searchField.setEnabled(true);
    }

    private void refresh(){
        timesheetModel.setRows(TimesheetFactory.getAllTimesheets());
        Toast.makeText((JFrame)SwingUtilities.getWindowAncestor(this), "Orders refreshed.").display();
        timesheetList = TimesheetFactory.getAllTimesheets();
    }

    public static void main(String[] args){
        final int WIDTH = 1300;
        final int HEIGHT = 600;
        JFrame frame = new JFrame();
        frame.add(new EmployeeView());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
    }
}
*/