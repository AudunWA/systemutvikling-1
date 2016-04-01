package no.ntnu.iie.stud.cateringstorm.entities.order;

import javax.swing.table.AbstractTableModel;
import java.security.Timestamp;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

/**
 * Table model for use in GUI for chauffeurs
 * Created by EliasBrattli on 30/03/2016.
 */
//int orderId, int employeeId, int customerId, int recurringOrderId, String description, Timestamp deliveryDate, Timestamp orderDate, int portions, boolean priority, boolean delivered
public class ChauffeurOrderTableModel extends AbstractTableModel {
    private String[] columnNames ={
            "Order ID", "Customer", "Portions", "Delivery date","Location","Status"
    };
    private List<Order> orderList;
    public ChauffeurOrderTableModel(){
        orderList = new ArrayList<Order>();
    }
    public ChauffeurOrderTableModel(List<Order> orderList){
        this.orderList = orderList;
    }

    //Parent methods must be overridden as we use Abstract table model
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }
    @Override
    public int getRowCount(){
        return orderList.size();
    }

    @Override
    public Class getColumnClass(int column){
        switch (column){
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return Integer.class;
            case 3: return Timestamp.class;
            case 4: return String.class;
            case 5: return String.class;
            default: return String.class;
        }
    }
    @Override
    public boolean isCellEditable(int row, int column){
        switch (column){
            default: return false;
        }
    }
    public Order getOrder(int row){
        return orderList.get(row);
    }
    @Override
    public Object getValueAt(int row, int column){
        Order order = getOrder(row);
        switch (column){
            case 0: return order.getOrderId();
            case 1: return order.findCustomerName();
            case 2: return order.getPortions();
            case 3: return order.getDeliveryDate();
            case 4: return order.findCustomerAdress();
            case 5: return order.deliveryStatus();
            default: return null;
        }
    }
    @Override
    public void setValueAt(Object value, int row, int column){
        Order order = getOrder(row);
        switch (column){
            case 5:
                if(value instanceof String){
                    order.setDelivered(value.equals("Delivered"));
                }
                break;
        }
        fireTableCellUpdated(row, column);
    }
    public void addOrder(Order order){
        insertOrdert(getRowCount(),order);
    }

    public void insertOrdert(int row, Order order){
        orderList.add(row,order);
        fireTableRowsInserted(row,row);
    }
    public void removeOrder(int row){
        orderList.remove(row);
        fireTableRowsDeleted(row,row);
    }
}
