package no.ntnu.iie.stud.cateringstorm.gui.backend;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Abstract base class for our custom table models
 * Created by Audun on 05.04.2016.
 */
public abstract class EntityTableModel<T> extends AbstractTableModel {
    private ArrayList<T> rows;
    private ArrayList<Integer> columns;

    public EntityTableModel(ArrayList<T> rows) {
        this.rows = rows;
        this.columns = new ArrayList<>();
    }

    public EntityTableModel(ArrayList<T> rows, ArrayList<Integer> columns) {
        this.rows = rows;
        this.columns = columns;
    }

    protected T getValue(int rowIndex) {
        return rows.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }
}
