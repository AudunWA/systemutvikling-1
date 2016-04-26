package no.ntnu.iie.stud.cateringstorm.entities.recurringorder;

import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackageFactory;
import no.ntnu.iie.stud.cateringstorm.entities.subscription.Subscription;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * JUnit test class for RecurringOrder.
 */
public class RecurringOrderTest {
    // TODO: Finish test.
    @Test
    public void testGetDeliveryTime() throws Exception {
        Subscription subscription = new Subscription(-1,new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()+10000000),10000,1,true);
        ArrayList<RecurringOrder> recOrders = new ArrayList<>();
        FoodPackage foodPackage = FoodPackageFactory.getFoodPackage(1);
        recOrders.add(new RecurringOrder(-1,3,3600*14,5,subscription,foodPackage));
    }

}