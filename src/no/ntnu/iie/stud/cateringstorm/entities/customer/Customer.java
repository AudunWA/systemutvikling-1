package no.ntnu.iie.stud.cateringstorm.entities.customer;

/**
 * A customer entity from the MySQL database.
 */
public class Customer {
    /**
     * The ID of this customer.
     */
    private int customerId;

    /**
     * The forename of this customer.
     */
    private String forename;

    /**
     * The surname of this customer.
     */
    private String surname;

    /**
     * The address of this customer.
     */
    private String address;

    /**
     * Whether this customer is active or inactive.
     */
    private boolean active;

    /**
     * The phone number of this customer.
     */
    private String phone;

    /**
     * The email address of this customer.
     */
    private String email;

    /**
     * Constructs and initializes a customer with the specified details.
     * @param customerId The ID of the customer
     * @param forename The forename of the customer
     * @param surname The surname of the customer
     * @param address The address of the customer
     * @param active Whether the customer is active of inactive
     * @param phone The phone number of the customer
     * @param email The email address of the customer
     */
    public Customer(int customerId, String forename, String surname, String address, boolean active, String phone, String email) {
        this.customerId = customerId;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
        this.active = active;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Returns the ID of this customer.
     * @return the ID of this customer.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     Returns the forename of this customer.
     * @return the forename of this customer.
     */
    public String getForename() {
        return forename;
    }

    /**
     * Sets the forename of this customer.
     * @param forename The new forename.
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * Returns the surname of this customer.
     * @return the surname of this customer.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of this customer.
     * @param surname The new surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the address of this customer.
     * @return the address of this customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of this customer.
     * @param address The new address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Determines if the customer is active. An inactive customer is in practise a deleted one.
     * @return true if the customer is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Activates or inactivates this customer. An inactive customer is in practise a deleted one.
     * @param active If true, this customer is active, otherwise this customer is inactive.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the phone number of this customer.
     * @return the phone number of this customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of this customer.
     * @param phone The new phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the email address of this customer.
     * @return the email address of this customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of this customer.
     * @param email The new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a text representation of the activeness of this customer.
     * @return "Active" if active, otherwise "Not active".
     */
    public String getActiveText() {
        return (isActive()) ? "Active" : "Not active";
    }

    /**
     * Returns a string representation of this customer and its values.
     * @return a string representation of this customer.
     */
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", active=" + active + '\'' +
                ", phone=" + phone + '\'' +
                ", email=" + email
                + '}';
    }
}
