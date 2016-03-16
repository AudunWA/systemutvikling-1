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

    public Order(int orderId, int employeeId, int customerId, int recurringOrderId, String description, Timestamp deliveryDate, Timestamp orderDate, int portions, boolean priority) {
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.recurringOrderId = recurringOrderId;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.orderDate = orderDate;
        this.portions = portions;
        this.priority = priority;
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
