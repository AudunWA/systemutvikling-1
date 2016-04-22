package no.ntnu.iie.stud.cateringstorm.gui.tablemodels;

import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrder;
import no.ntnu.iie.stud.cateringstorm.gui.util.DateUtil;

import java.util.ArrayList;

/**
 * Created by Audun on 07.04.2016.
 */
public class RecurringOrderTableModel extends EntityTableModel<RecurringOrder> {
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_SUBSCRIPTION_ID = 1;
    public static final int COLUMN_FOOD_PACKAGE_ID = 2;
    public static final int COLUMN_WEEKDAY = 3;
    public static final int COLUMN_RELATIVE_TIME = 4;
    public static final int COLUMN_FOOD_PACKAGE_NAME = 5;
    public static final int COLUMN_FOOD_PACKAGE_COST = 6;
    public static final int COLUMN_AMOUNT = 7;

    public RecurringOrderTableModel(ArrayList<RecurringOrder> rows, Integer[] columns) {
        super(rows, columns);
    }

    @Override
    public String getColumnName(int column) {
        int columnType = getColumnType(column);
        switch (columnType) {
            case COLUMN_ID:
                return "ID";
            case COLUMN_SUBSCRIPTION_ID:
                return "Subscription ID";
            case COLUMN_FOOD_PACKAGE_ID:
                return "Food package ID";
            case COLUMN_WEEKDAY:
                return "Week day";
            case COLUMN_RELATIVE_TIME:
                return "Time";
            case COLUMN_FOOD_PACKAGE_NAME:
                return "Name";
            case COLUMN_FOOD_PACKAGE_COST:
                return "Cost";
            case COLUMN_AMOUNT:
                return "Amount";
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RecurringOrder value = getValue(rowIndex);
        int columnType = getColumnType(columnIndex);

        switch (columnType) {
            case COLUMN_ID:
                return value.getRecurringOrderId();
            case COLUMN_SUBSCRIPTION_ID:
                return value.getSubscriptionId();
            case COLUMN_FOOD_PACKAGE_ID:
                return value.getFoodPackageId();
            case COLUMN_WEEKDAY:
                return DateUtil.formatWeekday(value.getWeekday());
            case COLUMN_RELATIVE_TIME:
                return DateUtil.formatRelativeTime(value.getRelativeTime());
            case COLUMN_FOOD_PACKAGE_NAME:
                return value.getFoodPackageName();
            case COLUMN_FOOD_PACKAGE_COST:
                return value.getFoodPackageCost();
            case COLUMN_AMOUNT:
                return value.getAmount();
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        int columnType = getColumnType(columnIndex);
        switch (columnType) {
            case COLUMN_ID:
                return Integer.class;
            case COLUMN_SUBSCRIPTION_ID:
                return Integer.class;
            case COLUMN_FOOD_PACKAGE_ID:
                return Integer.class;
            case COLUMN_WEEKDAY:
                return String.class;
            case COLUMN_RELATIVE_TIME:
                return String.class;
            case COLUMN_FOOD_PACKAGE_NAME:
                return String.class;
            case COLUMN_FOOD_PACKAGE_COST:
                return Double.class;
            case COLUMN_AMOUNT:
                return Integer.class;
            default:
                throw new IndexOutOfBoundsException("columnType " + columnType + " not defined.");
        }
    }
}
