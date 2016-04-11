package no.ntnu.iie.stud.cateringstorm.entities.customer;

import no.ntnu.iie.stud.cateringstorm.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Chris on 17.03.2016.
 */
public final class CustomerFactory {


    /**
     * Creates customer from result.
     * @param result
     * @return Customer
     * @throws SQLException
     */
    private static Customer createCustomerFromResultSet(ResultSet result) throws SQLException{

        int customer_id = result.getInt("customer_id");
        String surname = result.getString("surname");
        String forename = result.getString("forename");
        String address = result.getString("address");
        Boolean active = result.getBoolean("active");
        String phone = result.getString("phone");
        String email = result.getString("email");

        return new Customer(customer_id, surname, forename, address, active,phone,email);

    }

    /**
     * Shows a single customer by customerID
     * @param customerId
     * @return Customer
     */
    public static Customer viewSingleCustomer(int customerId){
        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE customer_id = ?")){

                statement.setInt(1, customerId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    if (result.next()){

                        return createCustomerFromResultSet(result);
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates an Arraylist containing all customers
     * @return ArrayList<Customer>
     */
    public static ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer")){
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()){

                        customers.add(createCustomerFromResultSet(result));
                    }
                    return customers;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }

    public static int getIdFromCustomerName(String forename, String surname){

        for (int i = 0; i < getAllCustomers().size(); i++){
            if (viewSingleCustomer(i+1).getSurname().equals(surname) && viewSingleCustomer(i+1).getForename().equals(forename)){
                return viewSingleCustomer(i+1).getCustomerId();
            }
        }
        return -1;

    }

    /**
     * Inserts a customer into the SQL table customer. Takes a Customer object as arguement
     * @param newCustomer
     * @return Customer
     */
    public static Customer createCustomer(Customer newCustomer){

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)){

                statement.setInt(1, newCustomer.getCustomerId());
                statement.setString(2, newCustomer.getSurname());
                statement.setString(3, newCustomer.getForename());
                statement.setString(4, newCustomer.getAddress());
                statement.setBoolean(5, newCustomer.isActive());
                statement.setString(6,newCustomer.getPhone());
                statement.setString(7,newCustomer.getEmail());

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

                Customer customer = new Customer(generatedId, newCustomer.getForename(), newCustomer.getSurname(), newCustomer.getAddress(),newCustomer.isActive(), newCustomer.getPhone(),newCustomer.getEmail());
                //statement.execute();
                return customer;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Edits the status of a customer (if the customer is active or not)
     * @param customerId
     * @param active
     * @return int
     */
    public static int editCustomerStatus(int customerId, boolean active){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE g_tdat1006_t6.customer SET active = ? WHERE customer.customer_id = ?")) {

                statement.setBoolean(1, active);
                statement.setInt(2, customerId);

                statement.execute();
                return customerId;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    // FIXME: There is something wrong with database connection?
    public static int updateCustomer(Customer customer){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE customer SET  surname = ?,forename = ?, address = ?, active = ?, phone = ?, email = ? WHERE customer_id = ?")) {
                statement.setString(1,customer.getSurname());
                statement.setString(2,customer.getForename());
                statement.setString(3,customer.getAddress());
                statement.setBoolean(4,customer.isActive());
                statement.setString(5,customer.getPhone());
                statement.setString(7,customer.getEmail());
                statement.setInt(8,customer.getCustomerId());
                return statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}
