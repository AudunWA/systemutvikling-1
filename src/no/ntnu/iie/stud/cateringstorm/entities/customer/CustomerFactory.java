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

    private static Customer createCustomerFromResultSet(ResultSet result) throws SQLException{

        int customer_id = result.getInt("customer_id");
        String surname = result.getString("surname");
        String forename = result.getString("forename");
        String address = result.getString("address");
        Boolean active = result.getBoolean("active");
        int area_id = result.getInt("area_id");

        return new Customer(customer_id, surname, forename, address, active, area_id);

    }

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

    public static Customer createCustomer(Customer newCustomer){

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?,?)")){

                statement.setInt(1, newCustomer.getCustomerId());
                statement.setString(2, newCustomer.getSurname());
                statement.setString(3, newCustomer.getForename());
                statement.setString(4, newCustomer.getAddress());
                statement.setBoolean(5, newCustomer.isActive());
                statement.setInt(6, newCustomer.getAreaId());

                statement.execute();
                return newCustomer;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static int editCustomer(int customerId, boolean active){
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

}
