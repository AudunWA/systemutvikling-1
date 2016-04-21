package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.Subscription;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Table model for order entities
 * Created by Audun on 06.04.2016.
 */
public class SubscriptionTableModel extends EntityTableModel<Subscription> {
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_CUSTOMER_ID = 1;
    public static final int COLUMN_START_DATE = 2;
    public static final int COLUMN_END_DATE = 3;
    public static final int COLUMN_COST = 4;
    public static final int COLUMN_ACTIVE = 5;


    public SubscriptionTableModel(ArrayList<Subscription> rows) {
        super(rows);
    }

    public SubscriptionTableModel(ArrayList<Subscription> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public String getColumnName(int column) {
        int columnType = getColumnType(column);
        switch (columnType) {
            case COLUMN_ID:
                return "ID";
            case COLUMN_CUSTOMER_ID:
                return "Customer ID";
            case COLUMN_START_DATE:
                return "Start date";
            case COLUMN_END_DATE:
                return "End date";
            case COLUMN_COST:
                return "Cost";
            case COLUMN_ACTIVE:
                return "Active";
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Subscription value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_ID:
                return value.getSubscriptionId();
            case COLUMN_CUSTOMER_ID:
                return value.getCustomerId();
            case COLUMN_START_DATE:
                return value.getStartDate();
            case COLUMN_END_DATE:
                return value.getEndDate();
            case COLUMN_COST:
                return value.getCost();
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
            case COLUMN_ID:
                return Integer.class;
            case COLUMN_CUSTOMER_ID:
                return Integer.class;
            case COLUMN_START_DATE:
                return String.class;
            case COLUMN_END_DATE:
                return String.class;
            case COLUMN_COST:
                return Double.class;
            case COLUMN_ACTIVE:
                return Boolean.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
