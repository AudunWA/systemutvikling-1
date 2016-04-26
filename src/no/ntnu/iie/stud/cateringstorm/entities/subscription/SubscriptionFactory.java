package no.ntnu.iie.stud.cateringstorm.entities.subscription;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class handling database interaction, loading and generating Subscription entity objects.
 */
public final class SubscriptionFactory {
    /**
     * Returning every subscription registered in database, including inactive ones
     *
     * @return ArrayList<Subscription>
     */
    public static ArrayList<Subscription> getAllSubscriptions() {
        ArrayList<Subscription> results = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM subscription")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        results.add(createSubscriptionFromResultSet(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return results;
    }

    /**
     * Returns a subscription with a selected ID.
     * @return The selected subscription, or null if error or not found.
     */
    public static Subscription getSubscription(int subscriptionId) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM subscription WHERE subscription_id = ?")) {
                statement.setInt(1, subscriptionId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                       return createSubscriptionFromResultSet(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    public static void generateOrdersFromSubscriptions() {
        // 1. Get all subscriptions
        // 2. Get their recurring orders
        // 3. Check if each has real orders created for them
        // 4. If orders are missing, create them.
        String s = "SELECT COUNT(*) FROM ";
    }*/

    /**
     * Method creates a subscription inserting it to database
     *
     * @param startDate
     * @param endDate
     * @param customer
     * @param cost
     * @param orders
     * @return Subscription
     */
    public static Subscription createSubscription(Date startDate, Date endDate, Customer customer, double cost, ArrayList<RecurringOrder> orders) {
        int generatedId;

        try (Connection connection = Database.getConnection()) {
            try {
                connection.setAutoCommit(false);

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO subscription VALUES(DEFAULT, ?, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {
                    //Insert data
                    statement.setDate(1, startDate);
                    statement.setDate(2, endDate);
                    statement.setDouble(3, cost);
                    statement.setInt(4, customer.getCustomerId());
                    statement.setBoolean(5, true); // Active

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
                        return null;
                    }
                }

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO recurring_order VALUES (DEFAULT,?,?,?,?,?)")) {

                    for (RecurringOrder recurringOrder : orders) {
                        statement.setInt(1, recurringOrder.getWeekday());
                        statement.setInt(2, recurringOrder.getRelativeTime());
                        statement.setInt(3, generatedId);
                        statement.setInt(4, recurringOrder.getFoodPackageId());
                        statement.setInt(5, recurringOrder.getAmount());
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
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        Subscription subscription = new Subscription(generatedId, startDate, endDate, cost, customer.getCustomerId(), true);
        return subscription;
    }
    /**
     * Creates an arraylist of the active subscriptions in the SQL table subscriptions
     *
     * @return ArrayList<Subscription>
     */
    public static ArrayList<Subscription> getActiveSubscriptions() {
        ArrayList<Subscription> foodPackages = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM subscription WHERE active = TRUE")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {

                        foodPackages.add(createSubscriptionFromResultSet(result));
                    }

                }
                return foodPackages;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * Gets all subscriptions with name matching a query.
     *
     * @return An ArrayList containing all subscription matched.
     */
    public static ArrayList<Subscription> getAllSubscriptionsByQuery(String searchQuery) {
        ArrayList<Subscription> subscription = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM subscription WHERE customer_name LIKE ?")) {
                statement.setString(1, '%' + searchQuery + '%');
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        subscription.add(createSubscriptionFromResultSet(result));
                    }

                }
                return subscription;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets all active foodpackes with name matching a query.
     *
     * @return An ArrayList containing all subscriptions matched.
     */
    public static ArrayList<Subscription> getActiveSubscriptionsByQuery(String searchQuery) {
        ArrayList<Subscription> subscriptions = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM subscription WHERE name LIKE ? AND active = TRUE")) {
                statement.setString(1, '%' + searchQuery + '%');
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        subscriptions.add(createSubscriptionFromResultSet(result));
                    }

                }
                return subscriptions;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Runs an UPDATE-query of a subscription, with all its columns.
     * This overload also replaces all the recurring orders in subscription.
     *
     * @param subscription The subscription to update
     * @param recurringOrders      The list of recurring orders to replace
     * @return Boolean representing success or not
     */
    public static boolean updateSubscription(Subscription subscription, List<RecurringOrder> recurringOrders) {
        if (recurringOrders == null) {
            throw new IllegalArgumentException("recurringOrders cannot be null.");
        }

        try (Connection connection = Database.getConnection()) {
            // Start transaction
            connection.setAutoCommit(false);

            try {
                // Update the food package itself
                try (PreparedStatement statement = connection.prepareStatement("UPDATE subscription SET start_date = ?, end_date = ?, cost = ?, customer_id = ?, active = ? WHERE subscription_id = ?")) {
                    statement.setDate(1, subscription.getStartDate());
                    statement.setDate(2, subscription.getEndDate());
                    statement.setDouble(3, subscription.getCost());
                    statement.setInt(4, subscription.getCustomerId());
                    statement.setBoolean(5, subscription.isActive());
                    statement.setInt(6, subscription.getSubscriptionId());

                    int affectedRows = statement.executeUpdate();
                    if (affectedRows == 0) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false; // No rows inserted
                    }
                }

                // Add the recurring orders to the subscription
                // First update existing ones
                try (PreparedStatement statement = connection.prepareStatement("UPDATE recurring_order SET week_day = ?, relative_time = ?, amount = ? WHERE rec_order_id = ?")) {

                    for (RecurringOrder recurringOrder : recurringOrders) {
                        // Only update old ones
                        if(recurringOrder.isNew()) {
                            continue;
                        }
                        statement.setInt(1, recurringOrder.getWeekday());
                        statement.setInt(2, recurringOrder.getRelativeTime());
                        statement.setInt(3, recurringOrder.getAmount());
                        statement.setInt(4, recurringOrder.getRecurringOrderId());
                        statement.addBatch();
                    }
                    statement.executeBatch();
                }

                // Then insert new ones
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO recurring_order VALUES (DEFAULT,?,?,?,?,?)")) {

                    for (RecurringOrder recurringOrder : recurringOrders) {
                        // Only insert new ones
                        if(!recurringOrder.isNew()) {
                            continue;
                        }
                        statement.setInt(1, recurringOrder.getWeekday());
                        statement.setInt(2, recurringOrder.getRelativeTime());
                        statement.setInt(3, subscription.getSubscriptionId());
                        statement.setInt(4, recurringOrder.getFoodPackageId());
                        statement.setInt(5, recurringOrder.getAmount());
                        statement.addBatch();
                    }
                    statement.executeBatch();
                }
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw e;
            }

            // All good, commit all
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // All good, return true
        return true;
    }

    /**
     * @param result
     * @return Subscription
     * @throws SQLException
     */
    private static Subscription createSubscriptionFromResultSet(ResultSet result) throws SQLException {
        int subscriptionId = result.getInt("subscription_id");
        int customerId = result.getInt("customer_id");
        Date startDate = result.getDate("start_date");
        Date endDate = result.getDate("end_date");
        double cost = result.getDouble("cost");
        boolean active = result.getBoolean("active");

        Customer customer = CustomerFactory.getCustomer(customerId);
        if (customer == null) {
            throw new NullPointerException("customer is null, check customerId.");
        }

        return new Subscription(subscriptionId, startDate, endDate, cost, customerId, active);
    }
    /**
     * Runs an UPDATE-query of a subscription, with all its columns.
     *
     * @param subscription The subscription to update
     * @return An integer representing affected rows
     */
    public static int updateSubscription(Subscription subscription) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE subscription SET start_date = ?, end_date = ?, cost = ?, active = ? WHERE subscription_id = ?")) {
                statement.setDate(1, subscription.getStartDate());
                statement.setDate(2, subscription.getEndDate());
                statement.setDouble(3, subscription.getCost());
                statement.setBoolean(4, subscription.isActive());
                statement.setInt(5, subscription.getSubscriptionId());

                return statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
