package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.employee.Employee;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeFactory;
import no.ntnu.iie.stud.cateringstorm.entities.employee.EmployeeType;

import java.util.ArrayList;

/**
 * Table Model designed for display of entity Employee
 */

public class EmployeeTableModel extends EntityTableModel<Employee> {
    public static final int COLUMN_USERNAME = 0;
    public static final int COLUMN_FORENAME = 1;
    public static final int COLUMN_SURNAME = 2;
    public static final int COLUMN_ADDRESS = 3;
    public static final int COLUMN_PHONE = 4;
    public static final int COLUMN_EMAIL = 5;
    public static final int COLUMN_EMPLOYEE_TYPE = 6;
    public static final int COLUMN_FULL_NAME = 7;
    public static final int COLUMN_ACTIVE = 8;

    public EmployeeTableModel(ArrayList<Employee> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public String getColumnName(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_USERNAME:
                return "Username";
            case COLUMN_FORENAME:
                return "Forename";
            case COLUMN_SURNAME:
                return "Surname";
            case COLUMN_ADDRESS:
                return "Address";
            case COLUMN_PHONE:
                return "Phone";
            case COLUMN_EMAIL:
                return "Email";
            case COLUMN_EMPLOYEE_TYPE:
                return "Employee type";
            case COLUMN_FULL_NAME:
                return "Name";
            case COLUMN_ACTIVE:
                return "Active";
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        return (columnType == COLUMN_ACTIVE) ? Boolean.class : String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_USERNAME:
                return value.getUsername();
            case COLUMN_FORENAME:
                return value.getForename();
            case COLUMN_SURNAME:
                return value.getSurname();
            case COLUMN_ADDRESS:
                return value.getAddress();
            case COLUMN_PHONE:
                return value.getPhoneNumber();
            case COLUMN_EMAIL:
                return value.getEmail();
            case COLUMN_EMPLOYEE_TYPE:
                return value.getEmployeeTypeString();
            case COLUMN_FULL_NAME:
                return value.getFullName();
            case COLUMN_ACTIVE:
                return value.isActive();
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        int columnType = getColumnType(columnIndex);
        Employee entity = getValue(rowIndex);
        switch (columnType) {
            case COLUMN_ACTIVE:
                return entity.getEmployeeType() != EmployeeType.ADMINISTRATOR; // Can't deactivate admins
        }
        return super.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Employee entity = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_USERNAME:
                break;
            case COLUMN_FORENAME:
                break;
            case COLUMN_SURNAME:
                break;
            case COLUMN_ADDRESS:
                break;
            case COLUMN_PHONE:
                break;
            case COLUMN_EMAIL:
                break;
            case COLUMN_EMPLOYEE_TYPE:
                break;
            case COLUMN_FULL_NAME:
                break;
            case COLUMN_ACTIVE:
                if (entity.getEmployeeType() != EmployeeType.ADMINISTRATOR) { // Can't deactivate admins
                    entity.setActive((Boolean) value);
                    EmployeeFactory.updateEmployee(entity);
                    fireTableCellUpdated(rowIndex, columnIndex);
                }
                break;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
