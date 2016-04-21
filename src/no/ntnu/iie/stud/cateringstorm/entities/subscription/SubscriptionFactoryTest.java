package no.ntnu.iie.stud.cateringstorm.entities.subscription;

import no.ntnu.iie.stud.cateringstorm.entities.customer.Customer;
import no.ntnu.iie.stud.cateringstorm.entities.customer.CustomerFactory;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrder;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Audun on 21.04.2016.
 */
public class SubscriptionFactoryTest {

    @Test
    public void testCreateSubscription() throws Exception {
        Date startDate = Date.valueOf(LocalDate.of(2016, 4, 21));
        Date endDate = Date.valueOf(LocalDate.of(2016, 5, 21));
        Customer customer = CustomerFactory.getCustomer(1); // hehe
        double cost = 50.5;
        ArrayList<RecurringOrder> recurringOrders = new ArrayList<>();
        recurringOrders.add(new RecurringOrder(-1, -1, 1, 1, 2000, 4, FoodPackageFactory.getFoodPackage(1)));
        recurringOrders.add(new RecurringOrder(-1, -1, 2, 1, 10000, 4, FoodPackageFactory.getFoodPackage(2)));
        recurringOrders.add(new RecurringOrder(-1, -1, 3, 1, 12000, 4, FoodPackageFactory.getFoodPackage(3)));
        Subscription subscription = SubscriptionFactory.createSubscription(startDate, endDate, customer, cost, recurringOrders);

        Assert.assertNotNull(subscription);
        Assert.assertNotEquals(-1, subscription.getSubscriptionId());
        Assert.assertEquals(startDate, subscription.getStartDate());
        Assert.assertEquals(endDate, subscription.getEndDate());
        Assert.assertEquals(cost, subscription.getCost(), 0.001);

        System.out.println(subscription);
    }
}