package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;

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

    public EntityTableModel(ArrayList<T> rows, Integer[] columns) {
        this.rows = rows;
        this.columns = new ArrayList<>(Arrays.asList(columns));
    }

    public T getValue(int rowIndex) {
        return rows.get(rowIndex);
    }

    protected int getColumnType(int index) { return columns.get(index); }

    public void setRows(ArrayList<T> rows) {
        this.rows = rows;
        fireTableDataChanged();
    }

    /**
     Replaces a row in the table, then refreshes the GUI.
     * @param rowIndex Which row index to replace
     * @param value The new value
     */
    public void setRow(int rowIndex, T value) {
        if(rowIndex < 0 || rowIndex >= rows.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        rows.set(rowIndex, value);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    /**
     Removes a row from the table, then refreshes the GUI.
     * @param rowIndex Which row index to remove
     */
    public void removeRow(int rowIndex) {
        if(rowIndex < 0 || rowIndex >= rows.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        rows.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public final int getRowCount() {
        return rows.size();
    }

    @Override
    public final int getColumnCount() {
        return columns.size();
    }

    // Force some methods to be implemented
    @Override
    public abstract String getColumnName(int column);

    @Override
    public abstract Class<?> getColumnClass(int columnIndex);
}
