package no.ntnu.iie.stud.cateringstorm.gui.tableModels;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;

import javax.swing.table.AbstractTableModel;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 04.04.2016.
 */
abstract class EntityTableModel extends AbstractTableModel{

    protected String[] columnNames;
    protected List<Order> orderList;

    public EntityTableModel(){orderList = new ArrayList<Order>();}

    public EntityTableModel(List<Order> orderList, String[] columnNames){
        this.orderList = orderList;
        this.columnNames = columnNames;
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

    //Unique
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
    //Unique -abstract
    @Override
    public boolean isCellEditable(int row, int column){
        switch (column){
            default: return false;
        }
    }
    //Make abstract
    public Order getOrder(int row){
        return orderList.get(row);
    }

    @Override
    public abstract Object getValueAt(int row, int column);

    //Unique
    @Override
    public abstract void setValueAt(Object value, int row, int column);


    public void addOrder(Order order){
        insertOrder(getRowCount(),order);
    }

    public void insertOrder(int row, Order order){
        orderList.add(row,order);
        fireTableRowsInserted(row,row);
    }
    public void removeOrder(int row){
        orderList.remove(row);
        fireTableRowsDeleted(row,row);
    }

}
