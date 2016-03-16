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
        int employeeId = result.getInt("employee_id");
        int customerId = result.getInt("customer_id");
        int recurringOrderId = result.getInt("rec_order_id");
        String description = result.getString("description");
        Timestamp deliveryDate = result.getTimestamp("delivery_date");
        Timestamp orderDate = result.getTimestamp("_order_date");
        int portions = result.getInt("portions");
        boolean priority = result.getBoolean("priority");

        return new Order(orderId, employeeId, customerId, recurringOrderId, description, deliveryDate, orderDate, portions, priority);
    }
    public static boolean setOrderState(int orderID,boolean delivered ){
        // TODO: Implement order change to database. This method is used by the Chauffeur, through ChaufferOrderView
        return true;
    }
}
