package no.ntnu.iie.stud.cateringstorm.entities.employee;

import no.ntnu.iie.stud.cateringstorm.gui.DashboardView;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

/**
 * Contains the basic information about an employee
 * Created by Audun on 10.03.2016.
 */
public class Employee {

    private final int employeeId;
    private String username;
    private String forename;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private EmployeeType employeeType;

    public Employee(int employeeId, String username, String forename, String surname, String address, String phoneNumber, String email, EmployeeType employeeType) {
        this.employeeId = employeeId;
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.employeeType = employeeType;
    }

    public String getUsername() {
        return username;
    }

    public String getSurname() {
        return surname;
    }

    public String getForename() {
        return forename;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public String getEmail() {
        return email;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return forename + " " + surname;
    }

    /**
     * Called on successful login attempt.
     */
    public void onSuccessfulLogin() {
        DashboardView dashboardView = new DashboardView(this);
        GlobalStorage.setLoggedInEmployee(this);
        dashboardView.setVisible(true);
        dashboardView.setLocationRelativeTo(dashboardView.getParent());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", username='" + username + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", employeeType=" + employeeType +
                '}';
    }

    // Old toString (manually made)
    /*@Override
    public String toString() {
        return "Employee ID: " + employeeId + "\nUsername: " + username + "\nForename: " + forename
                + "\nSurname: " + surname + "\nAddress: " + address + "\nPhone number: " + phoneNumber + "\nEmail: " + email
                + "\nType: " + employeeType;
    }*/
}
