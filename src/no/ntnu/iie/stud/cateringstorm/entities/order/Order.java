package no.ntnu.iie.stud.cateringstorm.entities.order;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;

import java.sql.Timestamp;

/**
 * Java representation of the database entity "_order".
 */
public class Order {
    private final int orderId;
    private int salespersonId;
    private int chauffeurId;
    private Customer customer;
    private int recurringOrderId;
    private String description;
    private Timestamp deliveryDate;
    private Timestamp orderDate;
    private int portions;
    private boolean priority;
    private int status;

    /**
     * Constructs and initializes a FoodPackage with the specified details.
     * @param orderId
     * @param description
     * @param deliveryDate
     * @param orderDate
     * @param portions
     * @param priority
     * @param salespersonId
     * @param customer
     * @param recurringOrderId
     * @param status
     * @param chauffeurId
     */
    public Order(int orderId, String description, Timestamp deliveryDate, Timestamp orderDate, int portions, boolean priority, int salespersonId, Customer customer, int recurringOrderId, int status, int chauffeurId) {
        this.orderId = orderId;
        this.customer = customer;
        this.recurringOrderId = recurringOrderId;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.orderDate = orderDate;
        this.portions = portions;
        this.priority = priority;
        this.status = status;
        this.chauffeurId = chauffeurId;
        this.salespersonId = salespersonId;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", salespersonId=" + salespersonId +
                ", chauffeurId=" + chauffeurId +
                ", customer=" + customer +
                ", recurringOrderId=" + recurringOrderId +
                ", description='" + description + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", orderDate=" + orderDate +
                ", portions=" + portions +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }

    /**
     *
     * @return
     */
    public int getSalespersonId() {
        return salespersonId;
    }

    /**
     *
     * @return
     */
    public int getChauffeurId() {
        return chauffeurId;
    }

    /**
     *
     * @return
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     *
     * @return
     */
    public int getCustomerId() {
        return customer.getCustomerId();
    }

    /**
     *
     * @return
     */
    public int getRecurringOrderId() {
        return recurringOrderId;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    /**
     *
     * @param deliveryDate
     */
    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     *
     * @return
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     *
     * @return
     */
    public int getPortions() {
        return portions;
    }

    /**
     *
     * @param portions
     */
    public void setPortions(int portions) {
        this.portions = portions;
    }

    /**
     *
     * @return
     */
    public boolean isPriority() {
        return priority;
    }

    /**
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
        OrderFactory.setOrderState(orderId, status);
    }

    /**
     *
     * @return
     */
    public String priorityAsString() {
        return (isPriority()) ? "Priority" : "Ordinary";
    }

    /**
     *
     * @return
     */
    public String deliveryStatus() {
        if (getStatus() == 0) {
            return "Ready for delivery";
        } else if (getStatus() == 1) {
            return "Ready for production";
        } else if (getStatus() == 2) {
            return "Delivered";
        } else if (getStatus() == 3) {
            return "In production";
        } else if (getStatus() == 4) {
            return "Being delivered";
        } else {
            return "Removed";
        }
    }

    /**
     *
     * @return
     */
    public String getCustomerName() {
        return customer.getForename() + " " + customer.getSurname();
    }

    /**
     *
     * @return
     */
    public String getCustomerAddress() {
        return customer.getAddress();
    }

    //public String findCustomerName(){
    //    return OrderFactory.getCustomerName(customerId);
    //}
    //public String findCustomerAdress(){
    //    return OrderFactory.getCustomerAddress(customerId);
    //}
}
