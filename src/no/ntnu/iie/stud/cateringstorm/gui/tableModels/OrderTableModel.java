package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Table model for order entities
 * Created by Audun on 06.04.2016.
 */
public class OrderTableModel extends EntityTableModel<Order> {
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_DESCRIPTION = 1;
    public static final int COLUMN_DELIVERY_TIME = 2;
    public static final int COLUMN_ORDER_TIME = 3;
    public static final int COLUMN_PORTIONS = 4;
    public static final int COLUMN_PRIORITY = 5;
    public static final int COLUMN_SALESPERSON_ID = 6;
    public static final int COLUMN_CUSTOMER_ID = 7;
    public static final int COLUMN_RECURRING_ORDER_ID = 8;
    public static final int COLUMN_STATUS_ID = 9;
    public static final int COLUMN_CHAUFFEUR_ID = 10;
    public static final int COLUMN_STATUS_TEXT = 11;

    public OrderTableModel(ArrayList<Order> rows) {
        super(rows);
    }

    public OrderTableModel(ArrayList<Order> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public String getColumnName(int column) {
        int columnType = getColumnType(column);
        switch (columnType) {
            case COLUMN_ID:
                return "ID";
            case COLUMN_DESCRIPTION:
                return "Description";
            case COLUMN_DELIVERY_TIME:
                return "Delivery time";
            case COLUMN_ORDER_TIME:
                return "Order time";
            case COLUMN_PORTIONS:
                return "Portions";
            case COLUMN_PRIORITY:
                return "Priority";
            case COLUMN_SALESPERSON_ID:
                return "Salesperson ID";
            case COLUMN_CUSTOMER_ID:
                return "Customer ID";
            case COLUMN_RECURRING_ORDER_ID:
                return "Recurring order ID";
            case COLUMN_STATUS_ID:
                return "Status ID";
            case COLUMN_CHAUFFEUR_ID:
                return "Chauffeur ID";
            case COLUMN_STATUS_TEXT:
                return "Status";
            default:
                throw new IndexOutOfBoundsException("columnIndex " + column + " not defined.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_ID:
                return value.getOrderId();
            case COLUMN_DESCRIPTION:
                return value.getDescription();
            case COLUMN_DELIVERY_TIME:
                return value.getDeliveryDate();
            case COLUMN_ORDER_TIME:
                return value.getOrderDate();
            case COLUMN_PORTIONS:
                return value.getPortions();
            case COLUMN_PRIORITY:
                return value.isPriority();
            case COLUMN_SALESPERSON_ID:
                return value.getSalespersonId();
            case COLUMN_CUSTOMER_ID:
                return value.getCustomerId();
            case COLUMN_RECURRING_ORDER_ID:
                return value.getRecurringOrderId();
            case COLUMN_STATUS_ID:
                return value.getStatus();
            case COLUMN_CHAUFFEUR_ID:
                return value.getChauffeurId();
            case COLUMN_STATUS_TEXT:
                return value.deliveryStatus();
            default:
                throw new IndexOutOfBoundsException("columnIndex " + columnIndex + " not defined.");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_ID:
                return int.class;
            case COLUMN_DESCRIPTION:
                return String.class;
            case COLUMN_DELIVERY_TIME:
                return Timestamp.class;
            case COLUMN_ORDER_TIME:
                return Timestamp.class;
            case COLUMN_PORTIONS:
                return int.class;
            case COLUMN_PRIORITY:
                return boolean.class;
            case COLUMN_SALESPERSON_ID:
                return int.class;
            case COLUMN_CUSTOMER_ID:
                return int.class;
            case COLUMN_RECURRING_ORDER_ID:
                return int.class;
            case COLUMN_STATUS_ID:
                return int.class;
            case COLUMN_CHAUFFEUR_ID:
                return int.class;
            case COLUMN_STATUS_TEXT:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex " + columnIndex + " not defined.");
        }
    }
}
