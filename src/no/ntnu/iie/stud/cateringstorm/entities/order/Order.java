package no.ntnu.iie.stud.cateringstorm.entities.order;

import java.sql.Timestamp;

/**
 * Created by Audun on 11.03.2016.
 */
public class Order {
    private final int orderId;
    private int employeeId;
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
                ", employeeId=" + employeeId +
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

    public Order(int orderId, int employeeId, int customerId, int recurringOrderId, String description, Timestamp deliveryDate, Timestamp orderDate, int portions, boolean priority, int status) {
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.recurringOrderId = recurringOrderId;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.orderDate = orderDate;
        this.portions = portions;
        this.priority = priority;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }
    public int getEmployeeId() {
        return employeeId;
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
            return "Delivered";
        } else if (getStatus() == 1){
            return "In production";
        } else {
            return "Ready for delivery";
        }
    }

    public String findCustomerName(){
        return OrderFactory.getCustomerName(customerId);
    }
    public String findCustomerAdress(){
        return OrderFactory.getCustomerAddress(customerId);
    }

}
