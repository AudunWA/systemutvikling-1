package no.ntnu.iie.stud.cateringstorm.entities.dish;

import javax.swing.table.AbstractTableModel;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Audun on 01.04.2016.
 */
public class DishTableModel extends AbstractTableModel {
    private String[] columnNames ={
            "Dish ID", "Customer", "Portions", "Delivery date","Location","Status"
    };
    private List<Dish> dishList;
    public DishTableModel(){
        dishList = new ArrayList<Dish>();
    }
    public DishTableModel(List<Dish> orderList){
        this.dishList = orderList;
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
        return dishList.size();
    }
    @Override
    public Class getColumnClass(int column){
        switch (column){
            case 0: return Integer.class;
            case 2: return Integer.class;
            case 3: return Timestamp.class;
            default: return String.class;
        }
    }
    @Override
    public boolean isCellEditable(int row, int column){
        final int editableRow = 5;//columnNames.length-1
        switch (column){
            case editableRow: return true;
            default: return false;
        }
    }
    public Dish getDish(int row){
        return dishList.get(row);
    }
    @Override
    public Object getValueAt(int row, int column){
        Dish dish = getDish(row);
        switch (column){
            case 0: return dish.getDishId();
            case 1: return order.findCustomerName();
            case 2: return order.getPortions();
            case 3: return order.getDeliveryDate();
            case 4: return order.findCustomerAdress();
            case 5: return order.isDelivered();
            default: return null;
        }
    }
    @Override
    public void setValueAt(Object value, int row, int column){
        Order order = getOrder(row);
        switch (column){
            case 5:
                if(value instanceof Boolean)order.setDelivered((boolean)value); break;
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