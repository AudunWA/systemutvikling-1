package no.ntnu.iie.stud.cateringstorm.entities.subscription;

import java.sql.Date;

/**
 * Java representation of the database entity "subscription".
 */
public class Subscription {
    private final int subscriptionId;
    private Date startDate;
    private Date endDate;
    private double cost;
    private int customerId;
    private boolean active;

    /**
     * Constructs and initializes a subscription with the specified details.
     * @param subscriptionId The ID of the subscription.
     * @param startDate The start date of the subscription.
     * @param endDate The end date of the subscription.
     * @param cost The cost of the subsciption.
     * @param customerId The ID of the customer subscribing.
     * @param active Whether the subscription is active or inactive.
     */
    public Subscription(int subscriptionId, Date startDate, Date endDate, double cost, int customerId, boolean active) {
        this.subscriptionId = subscriptionId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.customerId = customerId;
        this.active = active;
    }

    /**
     *  Returns the id of this subscription.
     * @return the id of this subscription.
     */
    public int getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Returns the start date of this subscription.
     * @return the start date of this subscription.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the subscription.
     * @param startDate The new start date.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     *  Returns the end date of this subscription.
     * @return the end date of this subscription.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     */
    public double getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     *
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     *
     * @return
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Subscription{" +
                "subscriptionId=" + subscriptionId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", cost=" + cost +
                ", customerId=" + customerId +
                ", active=" + active +
                '}';
    }
}
