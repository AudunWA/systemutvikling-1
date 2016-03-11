package no.ntnu.iie.stud.cateringstorm.entities.order;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Audun on 11.03.2016.
 */
public class OrderFactoryTest {

    @Test
    public void testNewOrder() throws Exception {
        Assert.assertNotNull(OrderFactory.newOrder(1));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        ArrayList<Order> orders = OrderFactory.getAllOrders();
        Assert.assertNotNull(orders);
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}