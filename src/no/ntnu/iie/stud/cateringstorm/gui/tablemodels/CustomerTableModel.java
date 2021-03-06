package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;

import java.util.ArrayList;

/**
 * Table model designed to manage contents of JTable in SalespersonCustomerView
 */
public class CustomerTableModel extends EntityTableModel<Customer> {
    public static final int COLUMN_CUSTOMER_ID = 0;
    public static final int COLUMN_SURNAME = 1;
    public static final int COLUMN_FORENAME = 2;
    public static final int COLUMN_ADDRESS = 3;
    public static final int COLUMN_PHONE = 4;
    public static final int COLUMN_EMAIL = 5;
    public static final int COLUMN_ACTIVETEXT = 6;
    public static final int COLUMN_ACTIVE = 7;

    public CustomerTableModel(ArrayList<Customer> rows) {
        super(rows);
    }

    public CustomerTableModel(ArrayList<Customer> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public String getColumnName(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_CUSTOMER_ID:
                return "Customer ID";
            case COLUMN_SURNAME:
                return "Surname";
            case COLUMN_FORENAME:
                return "Forename";
            case COLUMN_ADDRESS:
                return "Address";
            case COLUMN_ACTIVETEXT:
                return "Status";
            case COLUMN_PHONE:
                return "Phone";
            case COLUMN_ACTIVE:
                return "Active";
            case COLUMN_EMAIL:
                return "Email";
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_CUSTOMER_ID:
                return value.getCustomerId();
            case COLUMN_SURNAME:
                return value.getSurname();
            case COLUMN_FORENAME:
                return value.getForename();
            case COLUMN_ADDRESS:
                return value.getAddress();
            case COLUMN_ACTIVETEXT:
                return value.getActiveText();
            case COLUMN_PHONE:
                return value.getPhone();
            case COLUMN_ACTIVE:
                return value.isActive();
            case COLUMN_EMAIL:
                return value.getEmail();
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_CUSTOMER_ID:
                return int.class;
            case COLUMN_SURNAME:
                return String.class;
            case COLUMN_FORENAME:
                return String.class;
            case COLUMN_ADDRESS:
                return String.class;
            case COLUMN_ACTIVETEXT:
                return String.class;
            case COLUMN_PHONE:
                return String.class;
            case COLUMN_ACTIVE:
                return Boolean.class;
            case COLUMN_EMAIL:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return getColumnType(columnIndex) == COLUMN_ACTIVE;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Customer entity = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);
        // Having an open switch in case more outcomes need to be implemented
        switch (columnType) {
            case COLUMN_CUSTOMER_ID:
                break;
            case COLUMN_SURNAME:
                break;
            case COLUMN_FORENAME:
                break;
            case COLUMN_ADDRESS:
                break;
            case COLUMN_ACTIVETEXT:
                entity.setActive((Boolean) value);
                break;
            case COLUMN_PHONE:
                break;
            case COLUMN_ACTIVE:
                entity.setActive((Boolean) value);
                CustomerFactory.editCustomerStatus(entity.getCustomerId(), entity.isActive());
                fireTableCellUpdated(rowIndex, columnIndex);
                break;
            case COLUMN_EMAIL:
                break;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
