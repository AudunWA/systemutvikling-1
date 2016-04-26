package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;

import java.util.ArrayList;

/**
 * Tablemodel class, used to manipulate contents in JTable in GUI windows FoodPackageAdminView and FoodPackageInfoView
 */
public class FoodPackageTableModel extends EntityTableModel<FoodPackage> {
    /**
     * Variables to be inserted to table model from outside
     */
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_COST = 2;
    public static final int COLUMN_ACTIVE = 3;

    public FoodPackageTableModel(ArrayList<FoodPackage> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return getColumnType(columnIndex) == COLUMN_ACTIVE;
    }

    /**
     * Gives a description for each selected column
     *
     * @param column
     * @return String
     */
    @Override
    public String getColumnName(int column) {
        int columnType = getColumnType(column);
        switch (columnType) {
            case COLUMN_ID:
                return "ID";
            case COLUMN_NAME:
                return "Name";
            case COLUMN_COST:
                return "Cost";
            case COLUMN_ACTIVE:
                return "Active";
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    /**
     * Gives selected value of an entity
     *
     * @param rowIndex    Index of the row.
     * @param columnIndex Column of the row.
     * @return Object Selected value.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FoodPackage value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_ID:
                value.getFoodPackageId();
            case COLUMN_NAME:
                return value.getName();
            case COLUMN_COST:
                return value.getCost();
            case COLUMN_ACTIVE:
                return value.isActive();
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        int columnType = getColumnType(columnIndex);
        FoodPackage entity = getValue(rowIndex);
        // Column containing status is only one to be edited.
        if (columnType == COLUMN_ACTIVE) {
            entity.setActive((Boolean) aValue);

            FoodPackageFactory.updateFoodPackage(entity);
            fireTableCellUpdated(rowIndex, columnIndex);
        } else {
            //Empty void, nothing happens
            super.setValueAt(aValue, rowIndex, columnIndex);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_ID:
                return int.class;
            case COLUMN_NAME:
                return String.class;
            case COLUMN_COST:
                return double.class;
            case COLUMN_ACTIVE:
                return Boolean.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
