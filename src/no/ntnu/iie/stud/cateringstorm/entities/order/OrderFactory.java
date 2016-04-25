package no.ntnu.iie.stud.cateringstorm.entities.order;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class handling database interaction, loading and generating Order entity objects.
 */

public final class OrderFactory {

    /**
     * Used in the method below
     */
    private static final String salesForPeriodQuery = "SELECT DATE(`_order`.`_order_time`) AS date, SUM(cost) AS sales FROM `_order_food_package` INNER JOIN `food_package` USING(food_package_id) INNER JOIN `_order` USING(`_order_id`) WHERE DATE(`_order`.`_order_time`) BETWEEN ? AND ? GROUP BY DATE(`_order`.`_order_time`);";

    /**
     * @param orderId
     * @return Order
     */
    public static Order getOrder(int orderId) {
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

    /**
     * @param orderId
     * @return Timestamp
     */
    public static Timestamp getDeliveryStart(int orderId) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT delivery_start_time FROM `_order` WHERE `_order_id` = ?")) {
                statement.setInt(1, orderId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    if (result.next()) {
                        return result.getTimestamp("delivery_start_time");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Set start of delivery to time current time method is called
     *
     * @param orderId
     */
    public static void setDeliveryStart(int orderId) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET delivery_start_time = NOW() WHERE _order_id = ?")) {
                statement.setInt(1, orderId);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get currently relevant addresses
     *
     * @return ArrayList<String>
     */
    public static ArrayList<String> getAllAvailableDeliveryAddresses() {
        ArrayList<String> addresses = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT address FROM _order NATURAL JOIN customer WHERE (status = 0) && DATE(delivery_time) = CURDATE() ORDER BY delivery_time")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        addresses.add(result.getString("address"));
                    }
                }
            }
            return addresses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get every address existing in database
     *
     * @return ArrayList<String>
     */
    public static ArrayList<String> getAllAddresses() {
        ArrayList<String> addresses = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT address FROM _order NATURAL JOIN customer WHERE (status = 0 || status > 2) && delivery_time > NOW() ORDER BY delivery_time")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        addresses.add(result.getString("address"));
                    }
                }
            }
            return addresses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all orders, regardless of status
     *
     * @return ArrayList<Order>
     */
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

    /**
     * Getting all order relevant for chefs, with relevant statuses
     * Excluding orders being delivered and orders already delivered.
     *
     * @return ArrayList<Order>
     */
    public static ArrayList<Order> getAllOrdersChef() {
        ArrayList<Order> employees = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `_order` WHERE (status < 2 || status = 3) && delivery_time > NOW() ORDER BY delivery_time")) {
                statement.executeQuery();

                try (ResultSet resultPreUpdate = statement.getResultSet()) {
                    while (resultPreUpdate.next()) {

                        if ((createOrderFromResultSet(resultPreUpdate).getDeliveryDate().compareTo(new Date(System.currentTimeMillis() + 2 * 86400000))) == -1) {
                            try (PreparedStatement statementUpdate = connection.prepareStatement("UPDATE _order SET priority = 1 WHERE _order_id = ?")) {
                                statementUpdate.setInt(1, createOrderFromResultSet(resultPreUpdate).getOrderId());
                                statementUpdate.execute();
                            }
                        }
                    }
                }
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

    /**
     * TODO: Remove if not used at deadline
     *
     * @return ArrayList<Order>
     */
    public static ArrayList<Order> getAllOrdersChauffeur() {
        ArrayList<Order> employees = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `_order` WHERE (status = 0) && delivery_time > NOW() ORDER BY delivery_time")) {
                statement.executeQuery();

                try (ResultSet resultPreUpdate = statement.getResultSet()) {
                    while (resultPreUpdate.next()) {

                        if ((createOrderFromResultSet(resultPreUpdate).getDeliveryDate().compareTo(new Date(System.currentTimeMillis() + 2 * 86400000))) == -1) {
                            try (PreparedStatement statementUpdate = connection.prepareStatement("UPDATE _order SET priority = 1 WHERE _order_id = ?")) {
                                statementUpdate.setInt(1, createOrderFromResultSet(resultPreUpdate).getOrderId());
                                statementUpdate.execute();
                            }
                        }
                    }
                }
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

    /**
     * Sending all available orders to chauffeur
     *
     * @return ArrayList<Order>
     */
    public static ArrayList<Order> getAllAvailableOrdersChauffeur() {
        ArrayList<Order> employees = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `_order` WHERE status = 0 && DATE(delivery_time) = CURDATE() ORDER BY delivery_time")) {
                statement.executeQuery();

                try (ResultSet resultPreUpdate = statement.getResultSet()) {
                    while (resultPreUpdate.next()) {

                        if ((createOrderFromResultSet(resultPreUpdate).getDeliveryDate().compareTo(new Date(System.currentTimeMillis() + 2 * 86400000))) == -1) {
                            try (PreparedStatement statementUpdate = connection.prepareStatement("UPDATE _order SET priority = 1 WHERE _order_id = ?")) {
                                statementUpdate.setInt(1, createOrderFromResultSet(resultPreUpdate).getOrderId());
                                statementUpdate.execute();
                            }
                        }
                    }
                }
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

    /**
     * Making available for map view for chauffeurs
     *
     * @return ArrayList<Order>
     */
    public static ArrayList<Order> getAllAvailableOrdersForChauffeurTable() {
        ArrayList<Order> employees = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `_order` WHERE (status = 0 || status = 4) && delivery_time > NOW() ORDER BY delivery_time")) {
                statement.executeQuery();

                try (ResultSet resultPreUpdate = statement.getResultSet()) {
                    while (resultPreUpdate.next()) {

                        if ((createOrderFromResultSet(resultPreUpdate).getDeliveryDate().compareTo(new Date(System.currentTimeMillis() + 2 * 86400000))) == -1) {
                            try (PreparedStatement statementUpdate = connection.prepareStatement("UPDATE _order SET priority = 1 WHERE _order_id = ?")) {
                                statementUpdate.setInt(1, createOrderFromResultSet(resultPreUpdate).getOrderId());
                                statementUpdate.execute();
                            }
                        }
                    }
                }
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

    /**
     * @param result
     * @return
     * @throws SQLException
     */
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

        Customer customer = CustomerFactory.getCustomer(customerId);
        if (customer == null) {
            throw new NullPointerException("customer is null, check customerId.");
        }

        return new Order(orderId, description, deliveryDate, orderDate, portions, priority, salespersonId, customer, recurringOrderId, status, chauffeurId);
    }

    /**
     * @param orderID
     * @param status
     * @return
     */
    public static boolean setOrderState(int orderID, int status) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET status = ? WHERE _order_id = ?")) {

                statement.setInt(1, status);
                statement.setInt(2, orderID);
                return statement.executeUpdate() == 1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 10
     * Find orders by customer name.
     *
     * @return An ArrayList containing all orders matching search
     */

    public static ArrayList<Order> getOrdersByQuery(String searchQuery) {

        ArrayList<Order> temp = new ArrayList<>();
        String input = searchQuery.trim();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select o.* from _order o join customer c ON (o.customer_id LIKE c.customer_id) where concat_ws(' ',c.forename,c.surname) like ?;")) {
                statement.setString(1, '%' + input + '%');
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        temp.add(createOrderFromResultSet(result));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * Runs an INSERT query to create a customer in the database.
     * @param description The description of the order.
     * @param deliveryTime When the order is set to be delivered.
     * @param portions How many portions of the order.
     * @param priority Whether this order is priority or not.
     * @param salespersonId If -1 inserts NULL, otherwise value.
     * @param customerId The ID of the customer.
     * @param chauffeurId If -1 inserts NULL, otherwise value.
     * @param recurringOrderId If -1 inserts NULL, otherwise value.
     * @param packageId A list of all food packages used.
     * @return Order The created order.
     */
    public static Order createOrder(String description, Timestamp deliveryTime, int portions, boolean priority,
                                    int salespersonId, int customerId, int chauffeurId, int recurringOrderId, ArrayList<Integer> packageId) {
        try (Connection connection = Database.getConnection()) {
            return createOrder(connection, description, deliveryTime, portions, priority, salespersonId, customerId, chauffeurId, recurringOrderId, packageId);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * Runs an INSERT query to create a customer in the database.
     * @param connection The database connection to use.
     * @param description The description of the order.
     * @param deliveryTime When the order is set to be delivered.
     * @param portions How many portions of the order.
     * @param priority Whether this order is priority or not.
     * @param salespersonId If -1 inserts NULL, otherwise value.
     * @param customerId The ID of the customer.
     * @param chauffeurId If -1 inserts NULL, otherwise value.
     * @param recurringOrderId If -1 inserts NULL, otherwise value.
     * @param packageId A list of all food packages used.
     * @return Order The created order.
     */
    private static Order createOrder(Connection connection, String description, Timestamp deliveryTime, int portions, boolean priority,
                                    int salespersonId, int customerId, int chauffeurId, int recurringOrderId, ArrayList<Integer> packageId) throws SQLException {
        Customer customer = CustomerFactory.getCustomer(customerId);
        if (customer == null) {
            return null;
        }

        Timestamp orderTime = new Timestamp(System.currentTimeMillis());
        int generatedId;
        int initialStatus = 1;

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO _order VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL);", PreparedStatement.RETURN_GENERATED_KEYS)) {

                //Insert data
                statement.setString(1, description);
                statement.setTimestamp(2, deliveryTime);
                statement.setTimestamp(3, orderTime);
                statement.setInt(4, portions);
                statement.setBoolean(5, priority);

                if (salespersonId == -1) {
                    statement.setNull(6, Types.INTEGER);
                } else {
                    statement.setInt(6, salespersonId);
                }

                statement.setInt(7, customerId);

                if (recurringOrderId == -1) {
                    statement.setNull(8, Types.INTEGER);
                } else {
                    statement.setInt(8, recurringOrderId);
                }

                statement.setInt(9, initialStatus);

                if (chauffeurId == -1) {
                    statement.setNull(10, Types.INTEGER);
                } else {
                    statement.setInt(10, chauffeurId);
                }

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return null;
                }

                generatedId = Database.getGeneratedKeys(statement);
                if (generatedId == -1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return null; // No ID?
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
        } catch (SQLException e) {
            connection.rollback();
            connection.setAutoCommit(true);
            throw e;
        }

        //All good, commence commit
        connection.commit();
        connection.setAutoCommit(true);

        Order order = new Order(generatedId, description, deliveryTime, orderTime, portions, priority, salespersonId, customer, recurringOrderId, initialStatus, chauffeurId);
        return order;
    }

    public static ArrayList<Order> createOrdersForRecurringOrders(ArrayList<RecurringOrder> recurringOrders) {
        ArrayList<Order> orders = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            for (RecurringOrder recurringOrder : recurringOrders) {
                ArrayList<Integer> foodPackageIds = new ArrayList<>();
                foodPackageIds.add(recurringOrder.getFoodPackageId());
                // TODO: Define salesperson ID
                Order order = createOrder(connection, "Recurring order", recurringOrder.getDeliveryTime(), recurringOrder.getAmount(), false, -1, recurringOrder.getCustomerId(), -1, recurringOrder.getRecurringOrderId(), foodPackageIds);
                if (order != null) {
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return orders;
    }

    /**
     * @param orderId
     * @param newDate
     * @return boolean
     */
    public static boolean setOrderDate(int orderId, Timestamp newDate) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET delivery_time = ? WHERE _order_id = ?")) {

                statement.setTimestamp(1, newDate);
                statement.setInt(2, orderId);


                return statement.executeUpdate() == 1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param orderId
     * @param portions
     * @return boolean
     */
    public static boolean setOrderPortions(int orderId, int portions) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET portions = ? WHERE _order_id = ?")) {

                statement.setInt(1, portions);
                statement.setInt(2, orderId);

                return statement.executeUpdate() == 1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param orderId
     * @param description
     * @return boolean
     */
    public static boolean setOrderDescription(int orderId, String description) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET description = ? WHERE _order_id = ?")) {

                statement.setString(1, description);
                statement.setInt(2, orderId);

                return statement.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param orderID
     * @return ArrayList<Integer>
     */
    public static ArrayList<Integer> getPackages(int orderID) {
        ArrayList<Integer> packages = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT food_package_id FROM _order_food_package WHERE _order_id = ?")) {
                statement.setInt(1, orderID);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        packages.add(result.getInt("food_package_id"));
                    }
                }
            }
            return packages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * For orders to be set at a higher priority as specified in problem description
     * If a big event in need of orders comes up, it is to be prioritized above all other pending orders on the same date
     *
     * @param orderID
     * @param priority
     * @return boolean
     */
    public static boolean setOrderPriority(int orderID, boolean priority) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE _order SET priority = ? WHERE _order_id = ?")) {

                statement.setBoolean(1, priority);
                statement.setInt(2, orderID);

                return statement.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @return HashMap<LocalDate, Double>
     */
    public static HashMap<LocalDate, Double> getSalesForPeriod(LocalDate startDate, LocalDate endDate) {
        HashMap<LocalDate, Double> sales = new HashMap<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(salesForPeriodQuery)) {
                statement.setDate(1, Date.valueOf(startDate));
                statement.setDate(2, Date.valueOf(endDate));
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        LocalDate date = result.getDate("date").toLocalDate();
                        double income = result.getDouble("sales");
                        sales.put(date, income);
                    }
                }
            }
            return sales;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
