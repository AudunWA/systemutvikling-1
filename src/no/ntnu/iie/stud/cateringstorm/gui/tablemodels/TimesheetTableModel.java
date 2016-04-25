package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.timesheet.Timesheet;
import no.ntnu.iie.stud.cateringstorm.entities.timesheet.TimesheetFactory;

import java.util.ArrayList;

/**
 * Table model for manipulating contents of GUI JTable for Timesheet entity
 */
public class TimesheetTableModel extends EntityTableModel<Timesheet> {

    public static final int COLUMN_HOURS_ID = 0;
    public static final int COLUMN_EMPLOYEE_ID = 1;
    public static final int COLUMN_FROM_TIME = 2;
    public static final int COLUMN_TO_TIME = 3;
    public static final int COLUMN_ACTIVE = 4;

    public TimesheetTableModel(ArrayList<Timesheet> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public String getColumnName(int column) {
        int columnType = getColumnType(column);
        switch (columnType) {
            case COLUMN_HOURS_ID:
                return "Timesheet ID";
            case COLUMN_EMPLOYEE_ID:
                return "Customer ID";
            case COLUMN_FROM_TIME:
                return "Start time";
            case COLUMN_TO_TIME:
                return "End time";
            case COLUMN_ACTIVE:
                return "Active";
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Timesheet value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_HOURS_ID:
                return value.getTimesheetId();
            case COLUMN_EMPLOYEE_ID:
                return value.getEmployeeId();
            case COLUMN_FROM_TIME:
                return value.getFromTimeString();
            case COLUMN_TO_TIME:
                return value.getToTimeString();
            case COLUMN_ACTIVE:
                return value.isActive();
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_HOURS_ID:
                return int.class;
            case COLUMN_EMPLOYEE_ID:
                return int.class;
            case COLUMN_FROM_TIME:
                return String.class;
            case COLUMN_TO_TIME:
                return String.class;
            case COLUMN_ACTIVE:
                return Boolean.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        int columnType = getColumnType(columnIndex);
        return columnType == COLUMN_ACTIVE;
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        Timesheet entity = getValue(row);
        int columnType = getColumnType(column);
        switch (columnType) {
            case COLUMN_HOURS_ID:
                break;
            case COLUMN_EMPLOYEE_ID:
                break;
            case COLUMN_FROM_TIME:
                break;
            case COLUMN_TO_TIME:
                break;
            case COLUMN_ACTIVE:
                entity.setActive((Boolean) value);
                TimesheetFactory.editTimesheetStatus(entity.getTimesheetId(), entity.getEmployeeId(), entity.isActive());
                break;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
        fireTableCellUpdated(row, column);
    }
}
