package no.ntnu.iie.stud.cateringstorm.gui.tableModels;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;

import java.util.List;

/**
 * Created by EliasBrattli on 04/04/2016.
 */
public class DishTableModel extends EntityTableModel {

    public DishTableModel(List<Object> EntityList, String[] columnNames){
        super(EntityList,columnNames);
    }
    
    //TODO: implement the rest of the methods

    @Override
    public boolean isCellEditable(int row, int column){
        switch (column){
            case 1: return true;
            case 2: return true;
            case 4: return true;
            default: return false;
        }
    }

    @Override
    public Object getEntity(int row) {
        return null;
    }

    @Override
    public Object getValueAt(int row, int column){
        Dish dish = (Dish)getEntity(row);
        switch (column){
            case 0: return dish.getDishId();
            case 1: return dish.getName();
            case 2: return dish.getDescription();
            case 3: return dish.getDishType();
            case 4: return dish.isActive();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column){
        Dish dish = (Dish)getEntity(row);
        switch (column){
            case 1:
                if(value instanceof String){
                    dish.setName((String)value);
                }else{
                    return;
                }
                break;
            case 2:
                if(value instanceof  String){
                    dish.setDescription((String)value);
                } else {
                    return;
                }
                break;
            case 4:
                if(value instanceof Boolean){
                    dish.setActive((Boolean)value);
                } else {
                    return;
                }
                break;
            default: return;
        }
        fireTableCellUpdated(row, column);
    }

}
