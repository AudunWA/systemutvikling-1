package no.ntnu.iie.stud.cateringstorm.entities.subscription;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Audun on 21.04.2016.
 */
public class Subscription {
    private final int subscriptionId;
    private Date startDate;
    private Date endDate;
    private double cost;
    private int customerId;
    private boolean active;

    public Subscription(int subscriptionId, Date startDate, Date endDate, double cost, int customerId, boolean active) {
        this.subscriptionId = subscriptionId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.customerId = customerId;
        this.active = active;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

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
