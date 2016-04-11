package no.ntnu.iie.stud.cateringstorm.entities.order;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Audun on 11.03.2016.
 */
public final class OrderFactory {
    public static Order newOrder(int orderId) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `_order` WHERE `_order_id` = ?")) {
                statement.setInt(1, orderId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    if (result.next()) {
                        return createOrderFromResultSet(result);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Order> getAllOrders() {
        ArrayList<Order> employees = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `_order` WHERE status != -1")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        employees.add(createOrderFromResultSet(result));
                    }
                }
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Order createOrderFromResultSet(ResultSet result) throws SQLException {
        int orderId = result.getInt("_order_id");
        String description = result.getString("description");
        Timestamp deliveryDate = result.getTimestamp("delivery_time");
        Timestamp orderDate = result.getTimestamp("_order_time");
        int portions = result.getInt("portions");
        boolean priority = result.getBoolean("priority");
        int salespersonId = result.getInt("salesperson_id");
        int customerId = result.getInt("customer_id");
        int recurringOrderId = result.getInt("rec_order_id");
        int status = result.getInt("status");
        int chauffeurId = result.getInt("chauffeur_id");

        Customer customer = CustomerFactory.viewSingleCustomer(customerId);
        if(customer == null) {
            throw new NullPointerException("customer is null, check customerId.");
        }

        return new Order(orderId, description, deliveryDate, orderDate, portions, priority, salespersonId, customer, recurringOrderId, status, chauffeurId);
    }

    //This method is used by the Chauffeur, through ChaufferOrderView
    public static boolean setOrderState(int orderID, int status){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET status = ? WHERE _order_id = ?")) {

                statement.setInt(1, status);
                statement.setInt(2, orderID);

                statement.execute();
                return true;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    //This method is currently used by ChauffeurOrderTableModel
    public static String getCustomerName(int customerId){
        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT surname, forename FROM customer WHERE customer_id = ?")){

                statement.setInt(1, customerId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    if (result.next()) {
                        String temp = result.getString("surname");
                        temp += ", " + result.getString("forename");
                        return temp;
                    }
                }

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static String getCustomerAddress(int customerId){
        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT address FROM customer WHERE customer_id = ?")){

                statement.setInt(1, customerId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    if (result.next()) {
                        String temp = result.getString("address");
                        return temp;
                    }
                }

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Order createOrder(String description, Timestamp deliveryTime, int portions, boolean priority,
                                    int salespersonId, int customerId, int chauffeurId, ArrayList<Integer> packageId) {
        Customer customer = CustomerFactory.viewSingleCustomer(customerId);
        if(customer == null) {
            return null;
        }

        Timestamp orderTime = new Timestamp(System.currentTimeMillis());
        int generatedId;

        try (Connection connection = Database.getConnection()) {

            try {

                connection.setAutoCommit(false);

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO _order VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, null, ?, null);", PreparedStatement.RETURN_GENERATED_KEYS)) {

                    //Insert data
                    statement.setString(1, description);
                    statement.setTimestamp(2, deliveryTime);
                    statement.setTimestamp(3, orderTime);
                    statement.setInt(4, portions);
                    statement.setBoolean(5, priority);
                    statement.setInt(6, salespersonId);
                    statement.setInt(7, customerId);
                    statement.setInt(8, 1);

                    int affectedRows = statement.executeUpdate();

                    if (affectedRows == 0) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return null;
                    }

                    try (ResultSet result = statement.getGeneratedKeys()) {
                        if (result.next()) {
                            generatedId = result.getInt(1);
                        } else {
                            return null; // No ID?
                        }
                    }
                }

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO _order_food_package VALUES(?,?)")) {

                    for (Integer numbers : packageId) {
                        statement.setInt(1, generatedId);
                        statement.setInt(2, numbers);
                        statement.addBatch();
                    }
                        statement.executeBatch();
                }
            } catch (SQLException e){
                connection.rollback();
                connection.setAutoCommit(true);
                throw e;
            }

            //All good, commence commit
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        Order order = new Order(generatedId, description, deliveryTime, orderTime, portions, priority, salespersonId, customer, 0, 1, 0);
        return order;
    }

    public static boolean setOrderDate(int orderId, Timestamp newDate){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET delivery_time = ? WHERE _order_id = ?")) {

                statement.setTimestamp(1, newDate);
                statement.setInt(2, orderId);

                statement.execute();
                return true;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public static boolean setOrderPortions(int orderId, int portions){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET portions = ? WHERE _order_id = ?")) {

                statement.setInt(1, portions);
                statement.setInt(2, orderId);

                statement.execute();
                return true;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public static boolean setOrderDescription(int orderId, String description){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET description = ? WHERE _order_id = ?")) {

                statement.setString(1, description);
                statement.setInt(2, orderId);

                statement.execute();
                return true;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public static boolean setOrderPriority(int orderID, boolean priority){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET priority = ? WHERE _order_id = ?")) {

                statement.setBoolean(1, priority);
                statement.setInt(2, orderID);

                statement.execute();
                return true;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
