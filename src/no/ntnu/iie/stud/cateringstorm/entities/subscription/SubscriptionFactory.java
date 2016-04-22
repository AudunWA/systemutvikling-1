package no.ntnu.iie.stud.cateringstorm.entities.subscription;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrder;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Audun on 18.04.2016.
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
}
