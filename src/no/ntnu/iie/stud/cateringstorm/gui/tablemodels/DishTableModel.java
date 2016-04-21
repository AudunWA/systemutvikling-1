package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by EliasBrattli on 04/04/2016.
 */
public class DishTableModel extends EntityTableModel<Dish> {
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_TYPE_ID = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_DESCRIPTION = 3;
    public static final int COLUMN_ACTIVE = 4;
    public static final int COLUMN_TYPE_TEXT = 5;
    public DishTableModel(ArrayList<Dish> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public String getColumnName(int column) {
        int columnType = getColumnType(column);
        switch (columnType) {
            case COLUMN_ID:
                return "ID";
            case COLUMN_TYPE_ID:
                return "Type ID"; // TODO: As string
            case COLUMN_NAME:
                return "Name";
            case COLUMN_DESCRIPTION:
                return "Description";
            case COLUMN_ACTIVE:
                return "Active";
            case COLUMN_TYPE_TEXT:
                return "Type";
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Dish value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_ID:
                return value.getDishId();
            case COLUMN_TYPE_ID:
                return value.getDishType(); // TODO: As string
            case COLUMN_NAME:
                return value.getName();
            case COLUMN_DESCRIPTION:
                return value.getDescription();
            case COLUMN_ACTIVE:
                return value.isActive();
            case COLUMN_TYPE_TEXT:
                return value.dishTypeText();
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column){
        Dish entity = getValue(row);
        int columnType = getColumnType(column);
        String status0 = "Appetiser", status1 = "Main course", status2 = "Dessert";
        switch (columnType){
            case COLUMN_ID:
                break;
            case COLUMN_TYPE_ID:
                break;
            case COLUMN_NAME:
                break;
            case COLUMN_DESCRIPTION:
                break;
            case COLUMN_ACTIVE:
                entity.setActive((Boolean)value);
                break;
            case COLUMN_TYPE_TEXT:
                if(value.equals(status0)) {
                    entity.setDishType(1);
                }else if(value.equals(status1)){
                    entity.setDishType(2);
                }else if(value.equals(status2)){
                    entity.setDishType(3);
                }else{
                    entity.setDishType(-1);
                }
                break;
        }
        fireTableCellUpdated(row,column);

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_ACTIVE:
                return true;
        }
        return super.isCellEditable(rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_ID:
                return int.class;
            case COLUMN_TYPE_ID:
                return int.class; // TODO: As string
            case COLUMN_NAME:
                return String.class;
            case COLUMN_DESCRIPTION:
                return String.class;
            case COLUMN_ACTIVE:
                return Boolean.class;
            case COLUMN_TYPE_TEXT:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
