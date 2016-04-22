package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.ingredientdish.IngredientDish;

import java.util.ArrayList;

/**
 * Created by Chris on 21.04.2016.
 */
public class IngredientDishTableModel extends EntityTableModel<IngredientDish> {
    public static final int COLUMN_INGREDIENT_ID = 0;
    public static final int COLUMN_DISH_ID = 1;
    public static final int COLUMN_QUANTITY = 2;
    public static final int COLUMN_UNIT = 3;
    public static final int COLUMN_INGREDIENT_NAME = 4;
    public static final int COLUMN_DISH_NAME = 5;

    public IngredientDishTableModel(ArrayList<IngredientDish> rows){
        super(rows);
    }

    public IngredientDishTableModel(ArrayList<IngredientDish> rows, Integer[] columns){
        super(rows, columns);
    }

    @Override
    public String getColumnName(int column) {
        int columnType = getColumnType(column);
        switch (columnType) {
            case COLUMN_INGREDIENT_ID:
                return "Ingredient id";
            case COLUMN_DISH_ID:
                return "Dish id";
            case COLUMN_QUANTITY:
                return "Amount";
            case COLUMN_UNIT:
                return "Unit";
            case COLUMN_INGREDIENT_NAME:
                return "Ingredient name";
            case COLUMN_DISH_NAME:
                return "Dish name";
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        IngredientDish value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_INGREDIENT_ID:
                return value.getIngredient().getIngredientId();
            case COLUMN_DISH_ID:
                return value.getDish().getDishId();
            case COLUMN_QUANTITY:
                return value.getQuantity();
            case COLUMN_UNIT:
                return value.getUnit();
            case COLUMN_INGREDIENT_NAME:
                return value.getIngredient().getName();
            case COLUMN_DISH_NAME:
                return value.getDish().getName();
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_INGREDIENT_ID:
                return int.class;
            case COLUMN_DISH_ID:
                return int.class;
            case COLUMN_QUANTITY:
                return int.class;
            case COLUMN_UNIT:
                return String.class;
            case COLUMN_INGREDIENT_NAME:
                return String.class;
            case COLUMN_DISH_NAME:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

}
