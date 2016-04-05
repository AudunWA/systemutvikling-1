package no.ntnu.iie.stud.cateringstorm.gui.backend;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.gui.backend.EntityTableModel;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Table model for ingredient entities
 * Created by Audun on 05.04.2016.
 */
public class IngredientTableModel extends EntityTableModel<Ingredient> {
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_DESCRIPTION = 2;
    public static final int COLUMN_VEGETARIAN = 3;
    public static final int COLUMN_ARRIVAL_DATE = 4;
    public static final int COLUMN_EXPIRE_DATE = 5;
    public static final int COLUMN_AMOUNT = 6;
    public static final int COLUMN_UNIT = 7;

    public IngredientTableModel(ArrayList<Ingredient> rows) {
        super(rows);
    }

    public IngredientTableModel(ArrayList<Ingredient> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case COLUMN_ID:
                return "ID";
            case COLUMN_NAME:
                return "Name";
            case COLUMN_DESCRIPTION:
                return "Description";
            case COLUMN_VEGETARIAN:
                return "Vegetarian";
            case COLUMN_ARRIVAL_DATE:
                return "Arrival date";
            case COLUMN_EXPIRE_DATE:
                return "Expire date";
            case COLUMN_AMOUNT:
                return "Amount";
            case COLUMN_UNIT:
                return "Unit";
            default:
                throw new IndexOutOfBoundsException("columnIndex " + column + " not defined.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ingredient value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_ID:
                return value.getIngredientId();
            case COLUMN_NAME:
                return value.getName();
            case COLUMN_DESCRIPTION:
                return value.getDescription();
            case COLUMN_VEGETARIAN:
                return value.isVegetarian();
            case COLUMN_ARRIVAL_DATE:
                return value.getArrivalDate();
            case COLUMN_EXPIRE_DATE:
                return value.getExpireDate();
            case COLUMN_AMOUNT:
                return value.getAmount();
            case COLUMN_UNIT:
                return value.getUnit();
            default:
                throw new IndexOutOfBoundsException("columnIndex " + columnIndex + " not defined.");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUMN_ID:
                return int.class;
            case COLUMN_NAME:
                return String.class;
            case COLUMN_DESCRIPTION:
                return String.class;
            case COLUMN_VEGETARIAN:
                return boolean.class;
            case COLUMN_ARRIVAL_DATE:
                return Timestamp.class;
            case COLUMN_EXPIRE_DATE:
                return Date.class;
            case COLUMN_AMOUNT:
                return double.class;
            case COLUMN_UNIT:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex " + columnIndex + " not defined.");
        }
    }
}