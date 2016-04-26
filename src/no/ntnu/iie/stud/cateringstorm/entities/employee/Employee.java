package no.ntnu.iie.stud.cateringstorm.entities.employee;

import no.ntnu.iie.stud.cateringstorm.gui.DashboardView;
import no.ntnu.iie.stud.cateringstorm.util.GlobalStorage;

import javax.swing.*;
/* Expected salaries:
* One year is 1695 hrs
* CEO: 277.29 /hr | 470 000 /yr
* Secretary: 206.49 /hr | 350 000 /yr
* Salesperson: 106.19 /hr | 180 000 + 11% comm / yr
* Chef : 235.99 /hr | 400 000 / yr
* */

/**
 * Java representation of database entity "employee".
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
    private double salary;
    private int commission;


    /**
     * Constructs and initializes an Employee with the specified details.
     *
     * @param employeeId   The ID of the employee.
     * @param username     The user name of the employee.
     * @param forename     The forename of the employee.
     * @param surname      The surname of the employee.
     * @param address      The address of the employee.
     * @param phoneNumber  The phone number of the employee.
     * @param email        The email address of the employee.
     * @param employeeType The employee type of the employee.
     * @param active       Whether the employee is active or inactive.
     */
    public Employee(int employeeId, String username, String forename, String surname, String address, String phoneNumber, String email, EmployeeType employeeType, boolean active, double salary, int commission) {
        this.employeeId = employeeId;
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.employeeType = employeeType;
        this.active = active;
        this.salary = salary;
        this.commission = commission;

    }

    /**
     * Returns the username of this employee.
     *
     * @return the username of this employee.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of this employee.
     *
     * @param username The new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the surname of this employee.
     *
     * @return the surname of this employee.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of this employee.
     *
     * @param surname The new surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the forename of this employee.
     *
     * @return the forename of this employee.
     */
    public String getForename() {
        return forename;
    }

    /**
     * Sets the forename of this employee.
     *
     * @param forename The new forename.
     */
    public void setForename(String forename) {
        this.forename = forename;
    }

    /**
     * Returns the address of this employee.
     *
     * @return the address of this employee.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of this employee.
     *
     * @param address The new address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the phone number of this employee.
     *
     * @return the phone number of this employee.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of this employee.
     *
     * @param phoneNumber The new phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the employee type of this employee.
     *
     * @return The employee type of this employee.
     */
    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    /**
     * Sets the employee type of this employee.
     *
     * @param employeeType The new employee type.
     */
    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    /**
     * Returns the email address of this employee
     *
     * @return the email address of this employee.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of this employee.
     *
     * @param email The new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the id of this employee.
     *
     * @return the id of this employee.
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Determines whether the employee is active. An inactive employee is practically considered a deleted one.
     *
     * @return true if the employee is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Activates or inactivates this employee. An inactive employee is practically considered a deleted one.
     *
     * @param active If true, this employee is active, otherwise this employee is inactive.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns a string containing both the forename and surname of this employee.
     *
     * @return a string containing both the forename and surname of this employee.
     */
    public String getFullName() {
        return forename + " " + surname;
    }

    /**
     * Returns a string representation of the employee type.
     *
     * @return a string representation of the employee type.
     */
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
     * Returns the salary of this employee.
     *
     * @return the salary of this employee.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary of this employee.
     *
     * @param salary The new salary.
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Returns the commission of this employee.
     *
     * @return the commission of this employee.
     */
    public int getCommission() {
        return commission;
    }

    /**
     * Sets the commission of this employee.
     *
     * @param commission The new commission.
     */
    public void setCommission(int commission) {
        this.commission = commission;
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

    /**
     * Returns a string representation of this employee.
     *
     * @return a string representation of this employee.
     */
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

}
