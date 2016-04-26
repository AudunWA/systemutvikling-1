package no.ntnu.iie.stud.cateringstorm.entities.recurringorder;

import no.ntnu.iie.stud.cateringstorm.entities.subscription.Subscription;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.SubscriptionFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * JUnit test class from RecurringOrderFactory
 */
public class RecurringOrderFactoryTest {
    @Test
    public void testGoThroughRecurringOrders() throws Exception {
        int recurringOrders = RecurringOrderFactory.goThroughRecurringOrders();
        Assert.assertNotEquals(-1,recurringOrders);
    }

    @Test
    public void testGetRecurringOrders() throws Exception {
        // FIXME: Make "new" subscription instead of using a hardcoded one
        Subscription subscription = SubscriptionFactory.getSubscription(1);// We assume subscription #1 exists.
        ArrayList<RecurringOrder> factoryOrders = RecurringOrderFactory.getRecurringOrders(subscription.getSubscriptionId());
        Assert.assertNotNull(factoryOrders);
    }
}