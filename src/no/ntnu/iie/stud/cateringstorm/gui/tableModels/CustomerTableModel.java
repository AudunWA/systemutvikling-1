package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.gui.tablemodels.EntityTableModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by EliasBrattli on 06/04/2016.
 */
public class CustomerTableModel extends EntityTableModel<Customer>{
    private static final int COLUMN_CUSTOMER_ID = 0;
    private static final int COLUMN_NAME = 1;
    private static final int COLUMN_ADDRESS = 2;
    private static final int COLUMN_ACTIVETEXT = 3;
    private static final int COLUMN_PHONE = 4;
    private static final int COLUMN_ACTIVE = 5;
    private static final int COLUMN_EMAIL = 6;
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
            case COLUMN_NAME :
                return "Name";
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
                throw new IndexOutOfBoundsException("columnIndex " + columnIndex + " not defined.");
        }
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_CUSTOMER_ID :
                return value.getCustomerId();
            case COLUMN_NAME :
                return value.getForename()+" "+value.getSurname();
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
                throw new IndexOutOfBoundsException("columnIndex " + columnIndex + " not defined.");
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex){
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_CUSTOMER_ID :
                return int.class;
            case COLUMN_NAME :
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
                throw new IndexOutOfBoundsException("columnIndex " + columnIndex + " not defined.");
        }
    }
    @Override
    public void setValueAt(Object value, int row, int column) {
        Customer entity = getValue(row);
        int columnType = getColumnType(column);
    }
}
