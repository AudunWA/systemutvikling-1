package no.ntnu.iie.stud.cateringstorm.entities.customer;

/**
 * Created by Audun on 11.03.2016.
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

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public boolean isActive() {
        return active;
    }


    public String getPhone(){
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getActiveText(){
        return (isActive())?"Active":"Not active";
    }
    public void setStatus(boolean active){
        this.active = active;
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
                ", email="+email
                + '}';
    }
}
