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
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
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
                return boolean.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
