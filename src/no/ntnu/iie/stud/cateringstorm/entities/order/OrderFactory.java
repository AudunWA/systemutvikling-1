package no.ntnu.iie.stud.cateringstorm.entities.order;

import no.ntnu.iie.stud.cateringstorm.database.Database;

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
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `_order`")) {
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
        int sales_id = result.getInt("salesperson_id");
        int customerId = result.getInt("customer_id");
        int recurringOrderId = result.getInt("rec_order_id");
        int status = result.getInt("status");
        int chauffeur_id = result.getInt("chauffeur_id");

        return new Order(orderId, description, deliveryDate, orderDate, portions, priority, sales_id, customerId, recurringOrderId, status, chauffeur_id);
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

        Timestamp orderTime = new Timestamp(System.currentTimeMillis());
        int generatedId;

        try (Connection connection = Database.getConnection()) {

            try {

                connection.setAutoCommit(false);

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO _order VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, null, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {

                    //Insert data
                    statement.setString(1, description);
                    statement.setTimestamp(2, deliveryTime);
                    statement.setTimestamp(3, orderTime);
                    statement.setInt(4, portions);
                    statement.setBoolean(5, priority);
                    statement.setInt(6, salespersonId);
                    statement.setInt(7, customerId);
                    statement.setInt(8, 1);
                    statement.setInt(9, chauffeurId);

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

        Order order = new Order(generatedId, description, deliveryTime, orderTime, portions, priority, salespersonId, customerId, 0, 1, chauffeurId);
        return order;
    }

}
