package no.ntnu.iie.stud.cateringstorm.entities.employee;

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

    public int getEmployeeId() {
        return employeeId;
    }

    @Override
    public String toString() {
        return "Employee ID: " + employeeId + "\nUsername: " + username + "\nForename: " + forename
                + "\nSurname: " + surname + "\nAddress: " + address + "\nPhone number: " + phoneNumber + "\nEmail: " + email
                + "\nType: " + employeeType;
    }
}
