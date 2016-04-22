package no.ntnu.iie.stud.cateringstorm.entities.employee;

import no.ntnu.iie.stud.cateringstorm.gui.DashboardView;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;

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
    private boolean active;

    public Employee(int employeeId, String username, String forename, String surname, String address, String phoneNumber, String email, EmployeeType employeeType, boolean active) {
        this.employeeId = employeeId;
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.employeeType = employeeType;
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFullName() {
        return forename + " " + surname;
    }

    public String getEmployeeTypeString() {
        switch (getEmployeeType()) {
            case EMPLOYEE:
                return "Employee";
            case NUTRITION_EXPERT:
                return "Nutrition expert";
            case SALESPERSON:
                return "Salesperson";
            case CHAUFFEUR:
                return "Chauffeur";
            case CHEF:
                return "Chef";
            case ADMINISTRATOR:
                return "Administrator";
            default:
                return "Employee";
        }
    }

    /**
     * Called on successful login attempt.
     */
    public void onSuccessfulLogin() {
        GlobalStorage.setLoggedInEmployee(this);
        DashboardView dashboardView = new DashboardView(this);
        dashboardView.setVisible(true);
        dashboardView.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
