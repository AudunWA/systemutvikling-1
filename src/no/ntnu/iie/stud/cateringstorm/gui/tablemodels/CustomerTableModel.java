package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Table model designed to manage contents of JTable in SalespersonCustomerView
 * Created by EliasBrattli on 06/04/2016.
 */
public class CustomerTableModel extends EntityTableModel<Customer>{
    public static final int COLUMN_CUSTOMER_ID = 0;
    public static final int COLUMN_SURNAME = 1;
    public static final int COLUMN_FORENAME = 2;
    public static final int COLUMN_ADDRESS = 3;
    public static final int COLUMN_ACTIVETEXT = 4;
    public static final int COLUMN_PHONE = 5;
    public static final int COLUMN_ACTIVE = 6;
    public static final int COLUMN_EMAIL = 7;
    public CustomerTableModel(ArrayList<Customer> rows) {
        super(rows);
    }

    public CustomerTableModel(ArrayList<Customer> rows, Integer[] columns) {
        super(rows, columns);
    }
    @Override
    public String getColumnName(int columnIndex){
        int columnType = columnIndex;
        switch (columnType) {
            case COLUMN_CUSTOMER_ID :
                return "Customer ID";
            case COLUMN_SURNAME :
                return "Surname";
            case COLUMN_FORENAME :
                return "Forename";
            case COLUMN_ADDRESS:
                return "Address";
            case COLUMN_ACTIVETEXT:
                return "Status";
            case COLUMN_PHONE:
                return "Phone";
            case COLUMN_ACTIVE:
                return "Status ID";
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
            case COLUMN_CUSTOMER_ID :
                return value.getCustomerId();
            case COLUMN_SURNAME :
                return value.getSurname();
            case COLUMN_FORENAME :
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
    public Class<?> getColumnClass(int columnIndex){
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_CUSTOMER_ID :
                return int.class;
            case COLUMN_SURNAME :
                return String.class;
            case COLUMN_FORENAME :
                return String.class;
            case COLUMN_ADDRESS:
                return String.class;
            case COLUMN_ACTIVETEXT:
                return String.class;
            case COLUMN_PHONE:
                return String.class;
            case COLUMN_ACTIVE:
                return boolean.class;
            case COLUMN_EMAIL:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
    @Override
    public void setValueAt(Object value, int row, int column) {
        Customer entity = getValue(row);
        int columnType = getColumnType(column);
        switch(columnType){
            case COLUMN_CUSTOMER_ID :
                break;
            case COLUMN_SURNAME :
                break;
            case COLUMN_FORENAME :
                break;
            case COLUMN_ADDRESS:
                break;
            case COLUMN_ACTIVETEXT:
                entity.setActive(value.equals("Active"));
                break;
            case COLUMN_PHONE:
                break;
            case COLUMN_ACTIVE:
                break;
            case COLUMN_EMAIL:
                break;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
