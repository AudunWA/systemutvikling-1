package no.ntnu.iie.stud.cateringstorm.entities.recurringorder;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;

/**
 * Represents a recurring order, which is a part of a subscription
 * Created by Audun on 20.04.2016.
 */
public class RecurringOrder {
    private final int recurringOrderId;
    private final int subscriptionId;
    private int foodPackageId;
    private int weekday;
    private int relativeTime;
    private int amount;

    private FoodPackage foodPackage;

    public RecurringOrder(int recurringOrderId, int subscriptionId, int foodPackageId, int weekday, int relativeTime, int amount, FoodPackage foodPackage) {
        this.recurringOrderId = recurringOrderId;
        this.subscriptionId = subscriptionId;
        this.foodPackageId = foodPackageId;
        this.weekday = weekday;
        this.relativeTime = relativeTime;
        this.amount = amount;
        this.foodPackage = foodPackage;
    }

    public int getRecurringOrderId() {
        return recurringOrderId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public int getFoodPackageId() {
        return foodPackageId;
    }

    public void setFoodPackageId(int foodPackageId) {
        this.foodPackageId = foodPackageId;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(int relativeTime) {
        this.relativeTime = relativeTime;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void incrementAmount() {
        this.amount++;
    }

    public void decrementAmount() {
        this.amount--;
    }

    public String getFoodPackageName() {
        return foodPackage.getName();
    }

    public double getFoodPackageCost() {
        return foodPackage.getCost();
    }
}
