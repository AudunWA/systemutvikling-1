package no.ntnu.iie.stud.cateringstorm.entities.order;

import java.sql.Timestamp;

/**
 * Created by Audun on 11.03.2016.
 */
public class Order {
    private final int orderId;
    private int sales_id;
    private int chauffeur_id;
    private int customerId;
    private int recurringOrderId;
    private String description;
    private Timestamp deliveryDate;
    private Timestamp orderDate;
    private int portions;
    private boolean priority;
    private int status;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", sales_id=" + sales_id +
                ", chauffeur_id=" + chauffeur_id +
                ", customerId=" + customerId +
                ", recurringOrderId=" + recurringOrderId +
                ", description='" + description + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", orderDate=" + orderDate +
                ", portions=" + portions +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }

    public Order(int orderId, String description, Timestamp deliveryDate, Timestamp orderDate, int portions, boolean priority, int sales_id, int customerId, int recurringOrderId, int status, int chauffeur_id) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.recurringOrderId = recurringOrderId;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.orderDate = orderDate;
        this.portions = portions;
        this.priority = priority;
        this.status = status;
        this.chauffeur_id = chauffeur_id;
        this.sales_id = sales_id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }


    public int getRecurringOrderId() {
        return recurringOrderId;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public int getPortions() {
        return portions;
    }
    public boolean isPriority(){
        return priority;
    }

    public int getStatus(){
        return status;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public void setStatus(int status){
        this.status = status;
        OrderFactory.setOrderState(orderId,status);
    }
    public String findPriority() {
        return (isPriority())?"Priority":"Ordinary";
    }
    public String deliveryStatus(){
        if (getStatus() == 0){
            return "Ready for delivery";
        } else if (getStatus() == 1){
            return "In production";
        } else {
            return "Delivered";
        }
    }

    public String findCustomerName(){
        return OrderFactory.getCustomerName(customerId);
    }
    public String findCustomerAdress(){
        return OrderFactory.getCustomerAddress(customerId);
    }

}
