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
    protected List<Object> entityList;

    public EntityTableModel(){
        entityList = new ArrayList<Object>();}

    public EntityTableModel(List<Object> entityList, String[] columnNames){
        this.entityList = entityList;
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
        return entityList.size();
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

    public abstract Object getEntity(int row);

    @Override
    public abstract Object getValueAt(int row, int column);

    //Unique
    @Override
    public abstract void setValueAt(Object value, int row, int column);


    public void addEntity(Order order){
        insertEntity(getRowCount(),order);
    }

    public void insertEntity(int row, Order order){
        entityList.add(row,order);
        fireTableRowsInserted(row,row);
    }
    public void removeEntity(int row){
        entityList.remove(row);
        fireTableRowsDeleted(row,row);
    }

}