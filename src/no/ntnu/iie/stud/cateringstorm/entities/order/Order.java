package no.ntnu.iie.stud.cateringstorm.entities.order;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;

import java.sql.Timestamp;

/**
 * Created by Audun on 11.03.2016.
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

    public int getSalespersonId() {
        return salespersonId;
    }

    public int getChauffeurId() {
        return chauffeurId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customer.getCustomerId();
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

    public String getCustomerName() {
        return customer.getForename() + " " + customer.getSurname();
    }

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
