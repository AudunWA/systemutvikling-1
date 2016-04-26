package no.ntnu.iie.stud.cateringstorm.entities.customer;

import no.ntnu.iie.stud.cateringstorm.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Factory class handling loading and generating of Customer entity objects.
 */
public final class CustomerFactory {


    /**
     * Creates a customer from a result set.
     *
     * @param result The result set.
     * @return the created customer.
     * @throws SQLException
     */
    public static Customer createCustomerFromResultSet(ResultSet result) throws SQLException {

        int customer_id = result.getInt("customer_id");
        String surname = result.getString("surname");
        String forename = result.getString("forename");
        String address = result.getString("address");
        Boolean active = result.getBoolean("active");
        String phone = result.getString("phone");
        String email = result.getString("email");

        return new Customer(customer_id, forename, surname, address, active, phone, email);
    }

    /**
     * Gets a single customer by ID.
     *
     * @param customerId The ID of the customer.
     * @return the selected customer.
     */
    public static Customer getCustomer(int customerId) {
        try (Connection connection = Database.getConnection()) {
            return getCustomer(customerId, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a single customer by ID.
     *
     * @param customerId The ID of the customer.
     * @param connection The connection to use.
     * @return the selected customer.
     */
    public static Customer getCustomer(int customerId, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE customer_id = ?")) {
            statement.setInt(1, customerId);
            statement.executeQuery();

            try (ResultSet result = statement.getResultSet()) {
                if (result.next()) {
                    return createCustomerFromResultSet(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates an Arraylist containing all customers.
     *
     * @return an ArrayList with all customers in database table.
     */
    public static ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {

                        customers.add(createCustomerFromResultSet(result));
                    }
                    return customers;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Returns customers with column "active" = true.
     *
     * @return an ArrayList with all active customers.
     */
    public static ArrayList<Customer> getActiveCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE active LIKE true")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {

                        customers.add(createCustomerFromResultSet(result));
                    }
                    return customers;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds customers by name.
     *
     * @return An ArrayList containing all customers matching search.
     */
    public static ArrayList<Customer> getCustomersByQuery(String searchQuery) {

        ArrayList<Customer> temp = new ArrayList<>();
        String input = searchQuery.trim();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * from customer where concat_ws(' ',forename,surname) like ?;")) {
                statement.setString(1, '%' + input + '%');
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        temp.add(createCustomerFromResultSet(result));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static int getIdFromName(String forename, String surname) {

        for (int i = 0; i < getAllCustomers().size(); i++) {
            if (getCustomer(i + 1).getSurname().equals(surname) && getCustomer(i + 1).getForename().equals(forename)) {
                return getCustomer(i + 1).getCustomerId();
            }
        }
        return -1;

    }

    /**
     * Inserts a customer into the SQL table customer. Takes a Customer object as arguement
     *
     * @param surname
     * @param forename
     * @param address
     * @param active
     * @param phone
     * @param email
     * @return Customer
     */
    public static Customer createCustomer(String forename, String surname, String address, boolean active, String phone, String email) {

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO customer VALUES(DEFAULT,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, surname);
                statement.setString(2, forename);
                statement.setString(3, address);
                statement.setBoolean(4, active);
                statement.setString(5, phone);
                statement.setString(6, email);

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    return null; // No rows inserted
                }

                int generatedId;
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        generatedId = result.getInt(1);
                    } else {
                        return null; // No ID?
                    }
                }

                Customer customer = new Customer(generatedId, forename, surname, address, active, phone, email);
                //statement.execute();
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Edits the status of a customer (if the customer is active or not)
     * With status "inactive", the customer is essentially counted as deleted in the system
     *
     * @param customerId
     * @param active
     * @return int
     */
    public static int editCustomerStatus(int customerId, boolean active) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE customer SET active = ? WHERE customer.customer_id = ?")) {

                statement.setBoolean(1, active);
                statement.setInt(2, customerId);

                statement.execute();
                return customerId;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Method for editing a customer's details
     *
     * @param customer
     * @return int
     */
    public static int updateCustomer(Customer customer) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE customer SET  surname = ?,forename = ?, address = ?, active = ?, phone = ?, email = ? WHERE customer_id = ?")) {
                statement.setString(1, customer.getSurname());
                statement.setString(2, customer.getForename());
                statement.setString(3, customer.getAddress());
                statement.setBoolean(4, customer.isActive());
                statement.setString(5, customer.getPhone());
                statement.setString(6, customer.getEmail());
                statement.setInt(7, customer.getCustomerId());
                return statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
