package no.ntnu.iie.stud.cateringstorm.gui.backend;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.gui.backend.EntityTableModel;

import java.util.ArrayList;

/**
 * Table model for ingredient entities
 * Created by Audun on 05.04.2016.
 */
public class IngredientTableModel extends EntityTableModel<Ingredient> {
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_DESCRIPTION = 2;

    public IngredientTableModel(ArrayList<Ingredient> rows) {
        super(rows);
    }

    public IngredientTableModel(ArrayList<Ingredient> rows, ArrayList<Integer> columns) {
        super(rows, columns);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ingredient value = getValue(rowIndex);

        switch (columnIndex) {
            case COLUMN_ID:
                return value.getIngredientId();
            case COLUMN_NAME:
                return value.getName();
            case COLUMN_DESCRIPTION:
                return value.getDescription();
            default:
                throw new IndexOutOfBoundsException("columnIndex " + columnIndex + " not defined.");
        }
    }
}
