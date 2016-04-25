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
 * Java representation of the database entity "recurring_order", which is a part of a subscription.
 */
public class RecurringOrder {
    private final int recurringOrderId;
    private int weekday;
    private int relativeTime;
    private int amount;

    private Subscription subscription;
    private FoodPackage foodPackage;

    /**
     * Constructs and initializes a recurring order with the specified details.
     * @param recurringOrderId The ID of the recurring order.
     * @param weekday The week day number from 0-6(Monday to Sunday).
     * @param relativeTime The amount of seconds after midnight, defines delivery time of day.
     * @param amount The amount of food packages.
     * @param subscription The subscription connected to the recurring order.
     * @param foodPackage The foodpackage connected to the recurring order.
     */
    public RecurringOrder(int recurringOrderId, int weekday, int relativeTime, int amount, Subscription subscription, FoodPackage foodPackage) {
        this.recurringOrderId = recurringOrderId;
        this.weekday = weekday;
        this.relativeTime = relativeTime;
        this.amount = amount;
        this.subscription = subscription;
        this.foodPackage = foodPackage;
    }

    /**
     * Returns the id of the recurring order.
     * @return the id of the recurring order.
     */
    public int getRecurringOrderId() {
        return recurringOrderId;
    }

    /**
     * Returns the week day number.
     * @return the week day number.
     */
    public int getWeekday() {
        return weekday;
    }

    /**
     * Sets the week day number.
     * @param weekday The new week day number.
     */
    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    /**
     * Returns the amount of seconds after midnight, thus representing time of the day.
     * @return the amount of seconds after midnight.
     */
    public int getRelativeTime() {
        return relativeTime;
    }

    /**
     * Sets the amount of seconds after midnight, thus changing delivery time of day.
     * @param relativeTime The new amount of seconds after midnight.
     */
    public void setRelativeTime(int relativeTime) {
        this.relativeTime = relativeTime;
    }

    /**
     * Returns the amount of food packages.
     * @return the amount of food packages.
     */
    public int getAmount() {
        return amount;
    }

    /**
     *  Sets the amount of food packages.
     * @param amount The new amount of food packages.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Increments the amount of food packages.
     */
    public void incrementAmount() {
        this.amount++;
    }

    /**
     * Decrements the amount of food packages.
     */
    public void decrementAmount() {
        this.amount--;
    }

    /**
     * Return the id of the connected subscription.
     * @return the id of the connected subscription.
     */
    public int getSubscriptionId() {
        return subscription.getSubscriptionId();
    }

    /**
     * Return the id of the connected food package.
     * @return the id of the connected food package.
     */
    public int getFoodPackageId() { return foodPackage.getFoodPackageId(); }

    /**
     * Returns the name of the connected food package.
     * @return the name of the connected food package.
     */
    public String getFoodPackageName() {
        return foodPackage.getName();
    }

    /**
     * Returns the cost of the connected food package.
     * @return the cost of the connected food package.
     */
    public double getFoodPackageCost() {
        return foodPackage.getCost();
    }

    /**
     * Returns the id of the connected customer, from the connected subscription.
     * @return the id of the connected customer, from the connected subscription.
     */
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

    /**
     * Returns a string representation of this recurring order.
     * @return a string representation of this recurring order.
     */
    @Override
    public String toString() {
        return "RecurringOrder{" +
                "recurringOrderId=" + recurringOrderId +
                ", weekday=" + weekday +
                ", relativeTime=" + relativeTime +
                ", amount=" + amount +
                ", subscription=" + subscription +
                ", foodPackage=" + foodPackage +
                '}';
    }
}
