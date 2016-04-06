package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
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
    /* if (getStatus() == 0){
            return "Ready for delivery";
        } else if (getStatus() == 1){
            return "In production";
        } else if (getStatus() == 2) {
            return "Delivered";
        } else {
            return "Removed";*/
    @Override
    public void setValueAt(Object value, int row, int column){
        Order entity = getValue(row);
        int columnType = getColumnType(column);
        String status0 = "Ready for delivery", status1 = "In production", status2 = "Delivered", statusNeg1 = "Removed";

        switch (columnType){
            case COLUMN_DESCRIPTION:break;
            case COLUMN_DELIVERY_TIME:break;
            case COLUMN_ORDER_TIME:break;
            case COLUMN_PORTIONS:break;
            case COLUMN_PRIORITY:break;
            case COLUMN_SALESPERSON_ID:break;
            case COLUMN_CUSTOMER_ID:break;
            case COLUMN_RECURRING_ORDER_ID:break;
            case COLUMN_STATUS_ID:
                if(value.equals(status0)) {
                    entity.setStatus(0);
                }else if(value.equals(status1)){
                    entity.setStatus(1);
                }else if(value.equals(status2)){
                    entity.setStatus(2);
                }else{
                    entity.setStatus(-1);
                }
                break;
            case COLUMN_CHAUFFEUR_ID:break;
        }
        fireTableCellUpdated(row,column);

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
