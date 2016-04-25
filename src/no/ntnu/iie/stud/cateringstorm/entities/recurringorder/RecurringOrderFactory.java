package no.ntnu.iie.stud.cateringstorm.entities.recurringorder;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.Subscription;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.SubscriptionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Audun on 24.04.2016.
 */
public final class RecurringOrderFactory {
    /**
     * Gets a list of all recurring orders in the future without an actual order tied to it.
     * @return a list of all recurring orders in the future without an actual order tied to it.
     */
    private static ArrayList<RecurringOrder> getOrdersPendingCreation() {
        ArrayList<RecurringOrder> orders = new ArrayList<>();
        try(Connection connection = Database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement(
                    "SELECT recurring_order.* FROM recurring_order" +
                    "  NATURAL JOIN subscription" +
                    "  WHERE subscription.end_date >= CURDATE()" +
                    "        AND rec_order_id NOT IN(" +
                    "    SELECT rec_order_id FROM `_order`" +
                    "    WHERE _order.delivery_time >= CURDATE()" +
                    "          AND rec_order_id IS NOT NULL" +
                    ")")) {
                try(ResultSet result = statement.executeQuery()) {
                    while(result.next()) {
                        RecurringOrder order = createRecurringOrderFromResultSet(result);
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return orders;
    }

    public static int goThroughRecurringOrders() {
        ArrayList<RecurringOrder> pending = getOrdersPendingCreation();
        ArrayList<Order> createdOrders = OrderFactory.createOrdersForRecurringOrders(pending);
        return createdOrders.size();
    }

    /**
     * Creates a recurring order from a result set.
     * @param result The result set.
     * @return RecurringOrder The recurring order.
     * @throws SQLException
     */
    private static RecurringOrder createRecurringOrderFromResultSet(ResultSet result) throws SQLException {
        int recurringOrderId = result.getInt("rec_order_id");
        int subscriptionId = result.getInt("subscription_id");
        int foodPackageId = result.getInt("food_package_id");
        int weekday = result.getInt("week_day");
        int relativeTime = result.getInt("relative_time");
        int amount = result.getInt("amount");

        Subscription subscription = SubscriptionFactory.getSubscription(subscriptionId);
        if(subscription == null) {
            throw new NullPointerException("subscription is null.");
        }

        FoodPackage foodPackage = FoodPackageFactory.getFoodPackage(foodPackageId);
        if(foodPackage == null) {
            throw new NullPointerException("foodPackage is null.");
        }

        return new RecurringOrder(recurringOrderId, weekday, relativeTime, amount, subscription, foodPackage);
    }
}
