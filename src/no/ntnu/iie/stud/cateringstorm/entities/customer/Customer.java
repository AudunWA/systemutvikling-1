package no.ntnu.iie.stud.cateringstorm.entities.customer;

/**
 * Created by Audun on 11.03.2016.
 * Class for entity customer in MySQL Database
 */
public class Customer {
    private int customerId;
    private String forename;
    private String surname;
    private String address;
    private boolean active;
    private String phone;
    private String email;

    public Customer(int customerId, String surname, String forename, String address, boolean active, String phone, String email) {
        this.customerId = customerId;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
        this.active = active;
        this.phone = phone;
        this.email = email;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method is intended to produce a more user friendly value
     * as an alternative value in GUI JTable.
     *
     * @return String
     */
    public String getActiveText() {
        return (isActive()) ? "Active" : "Not active";
    }

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
