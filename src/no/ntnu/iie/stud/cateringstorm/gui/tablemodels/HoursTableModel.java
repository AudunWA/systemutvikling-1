package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.hours.Hours;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by EliasBrattli on 14/04/2016.
 */
public class HoursTableModel extends EntityTableModel<Hours> {

    public static final int COLUMN_HOURS_ID = 0;
    public static final int COLUMN_EMPLOYEE_ID = 1;
    public static final int COLUMN_START_TIME = 2;
    public static final int COLUMN_END_TIME = 3;

    public HoursTableModel(ArrayList<Hours> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public String getColumnName(int column) {
        int columnType = getColumnType(column);
        switch (columnType) {
            case COLUMN_HOURS_ID :
                return "Hours ID";
            case COLUMN_EMPLOYEE_ID:
                return "Customer ID";
            case COLUMN_START_TIME:
                return "Start time";
            case COLUMN_END_TIME:
                return "End time";

            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Hours value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_HOURS_ID:
                return value.getHoursId();
            case COLUMN_EMPLOYEE_ID:
                return value.getEmployeeId();
            case COLUMN_START_TIME:
                return value.getStartTime();
            case COLUMN_END_TIME:
                return value.getStartTime();

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
            case COLUMN_START_TIME:
                return Timestamp.class;
            case COLUMN_END_TIME:
                return Timestamp.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
