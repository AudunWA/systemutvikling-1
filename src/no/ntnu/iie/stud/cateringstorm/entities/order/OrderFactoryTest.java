package no.ntnu.iie.stud.cateringstorm.entities.order;

import com.sun.org.apache.xpath.internal.operations.Or;
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
    public void testGetAllOrders() throws Exception {
        ArrayList<Order> orders = OrderFactory.getAllOrders();
        Assert.assertNotNull(orders);
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    @Test
    public void testSetOrderState() throws Exception {
        OrderFactory.setOrderState(1, false);
        OrderFactory.setOrderState(2, false);
        OrderFactory.setOrderState(3, false);
        OrderFactory.setOrderState(4, false);

    }
}