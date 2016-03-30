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
    private boolean delivered;

    public Order(int orderId, int employeeId, int customerId, int recurringOrderId, String description, Timestamp deliveryDate, Timestamp orderDate, int portions, boolean priority, boolean delivered) {
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.recurringOrderId = recurringOrderId;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.orderDate = orderDate;
        this.portions = portions;
        this.priority = priority;
        this.delivered = delivered;
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

    public boolean isPriority() {
        return priority;
    }
    public boolean isDelivered(){
        return delivered
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }
    public void setDelivered(boolean delivered){
        this.delivered = delivered;
        OrderFactory.setOrderState(orderId,delivered);
    }
    public String findCustomerName(){
        return OrderFactory.getCustomerName(customerId);
    }
    public String findCustomerAdress(){
        return OrderFactory.getCustomerAddress(customerId);
    }
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
                '}';
    }

}
