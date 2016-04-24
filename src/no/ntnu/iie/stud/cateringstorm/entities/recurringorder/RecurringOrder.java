package no.ntnu.iie.stud.cateringstorm.entities.recurringorder;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.Subscription;
import no.ntnu.iie.stud.cateringstorm.gui.util.DateUtil;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represents a recurring order, which is a part of a subscription
 * Created by Audun on 20.04.2016.
 */
public class RecurringOrder {
    private final int recurringOrderId;
    private int weekday;
    private int relativeTime;
    private int amount;

    private Subscription subscription;
    private FoodPackage foodPackage;

    public RecurringOrder(int recurringOrderId, int weekday, int relativeTime, int amount, Subscription subscription, FoodPackage foodPackage) {
        this.recurringOrderId = recurringOrderId;
        this.weekday = weekday;
        this.relativeTime = relativeTime;
        this.amount = amount;
        this.subscription = subscription;
        this.foodPackage = foodPackage;
    }

    public int getRecurringOrderId() {
        return recurringOrderId;
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

    public int getSubscriptionId() {
        return subscription.getSubscriptionId();
    }

    public int getFoodPackageId() { return foodPackage.getFoodPackageId(); }

    public String getFoodPackageName() {
        return foodPackage.getName();
    }

    public double getFoodPackageCost() {
        return foodPackage.getCost();
    }

    public int getCustomerId() { return subscription.getCustomerId(); }

    /**
     * Generates the next delivery time of this order, using today's date as ground.
     * @return The next delivery time of this order.
     */
    public Timestamp getDeliveryTime() {
        // TODO: Needs unit test
        LocalDate nextDate = DateUtil.getNextDateOfDay(DayOfWeek.of(weekday + 1)); // We store Monday = 0, not 1
        return Timestamp.valueOf(nextDate.atTime(LocalTime.ofSecondOfDay(relativeTime)));
    }
}
