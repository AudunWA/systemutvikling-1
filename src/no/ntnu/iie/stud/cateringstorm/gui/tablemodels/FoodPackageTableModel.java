package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;

import java.util.ArrayList;

/**
 * Created by Audun on 07.04.2016.
 */
public class FoodPackageTableModel extends EntityTableModel<FoodPackage> {
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_NAME = 1;
    public static final int COLUMN_COST = 2;
    public static final int COLUMN_ACTIVE = 3;

    public FoodPackageTableModel(ArrayList<FoodPackage> rows, Integer[] columns) {
        super(rows, columns);
    }

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
                return boolean.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
