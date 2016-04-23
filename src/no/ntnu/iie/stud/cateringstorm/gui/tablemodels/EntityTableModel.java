package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract base class for custom table models
 * Contains methods designed for dynamic contents, and recycling code lines
 * Created by Audun on 05.04.2016.
 */
public abstract class EntityTableModel<T> extends AbstractTableModel {
    private ArrayList<T> rows;
    private ArrayList<Integer> columns;

    /**
     * Constructor for single column tables
     *
     * @param rows Rows in JTable
     */
    public EntityTableModel(ArrayList<T> rows) {
        this.rows = rows;
        this.columns = new ArrayList<>();
    }

    /**
     * Constructor for multi column tables
     *
     * @param rows    Rows in JTable
     * @param columns Column ID's in JTable
     */
    public EntityTableModel(ArrayList<T> rows, Integer[] columns) {
        this.rows = rows;
        this.columns = new ArrayList<>(Arrays.asList(columns));
    }

    /**
     * Returning the row selected in GUI JTable
     *
     * @param rowIndex Which row to return
     * @return Any value
     */
    public T getValue(int rowIndex) {
        return rows.get(rowIndex);
    }

    /**
     * Method gives external program access to column type, thus deciding which value to extract / insert.
     *
     * @param columnIndex Selected column index
     * @return int Type of column, implemented by public static integers in each model
     */
    protected int getColumnType(int columnIndex) {
        return columns.get(columnIndex);
    }

    /**
     * Method for updating table contents
     *
     * @param rows New ArrayList to replace current one
     */
    public void setRows(ArrayList<T> rows) {
        this.rows = rows;
        fireTableDataChanged();
    }

    /**
     * Replaces a row in the table, then refreshes the GUI.
     *
     * @param rowIndex Which row index to replace
     * @param value    The new value
     */
    public void setRow(int rowIndex, T value) {
        if (rowIndex < 0 || rowIndex >= rows.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }

        rows.set(rowIndex, value);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    /**
     * Updates the GUI values for the selected row.
     *
     * @param rowIndex Which row index to update
     */
    public void updateRow(int rowIndex) {
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    /**
     * Removes a row from the table, then refreshes the GUI.
     *
     * @param rowIndex Which row index to remove
     */
    public void removeRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        rows.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    /**
     * Adds a row to the table, then refreshes the GUI.
     *
     * @param value The new value to add
     */
    public void addRow(T value) {
        rows.add(value);
        fireTableRowsInserted(rows.size(), rows.size());
    }
    // TODO: Add comment
    public ArrayList<T> getRowsClone() {
        return (ArrayList<T>) rows.clone();
    }

    /**
     * Giving program access to amount of rows in table
     *
     * @return int
     */
    @Override
    public final int getRowCount() {
        return rows.size();
    }

    /**
     * Giving program access to amount of columns in table
     *
     * @return int
     */
    @Override
    public final int getColumnCount() {
        return columns.size();
    }

    /**
     * Forced implement as each table probably will have different columns
     * Gives each column a desired "headline" or description, as desired in used implementaion of method
     *
     * @param column Selected column
     * @return String
     */
    @Override
    public abstract String getColumnName(int column);

    /**
     * Has forced implement, as each table probably will have different contents
     * Dynamic method to implement different contents in each column, as desired in used implementation of method
     *
     * @param columnIndex Selected column
     * @return Any desired class in cell
     */
    @Override
    public abstract Class<?> getColumnClass(int columnIndex);
}
