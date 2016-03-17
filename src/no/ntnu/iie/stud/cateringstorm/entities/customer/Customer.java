package no.ntnu.iie.stud.cateringstorm.entities.customer;

/**
 * Created by Audun on 11.03.2016.
 */
public class Customer {
    private final int customerId;
    private int areaId;
    private String forename;
    private String surname;
    private String address;
    private boolean active;

    public Customer(int customerId, String forename, String surname, String address, boolean active, int areaId) {
        this.customerId = customerId;
        this.areaId = areaId;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", areaId=" + areaId +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", active=" + active +
                '}';
    }
}
