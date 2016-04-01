package no.ntnu.iie.stud.cateringstorm.entities.order;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
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
        OrderFactory.setOrderState(1, 1);
        OrderFactory.setOrderState(2, 2);
        OrderFactory.setOrderState(3, 0);
        OrderFactory.setOrderState(4, 1);

    }

    @Test
    public void testGetCustomerName() throws Exception {
        System.out.println(OrderFactory.getCustomerName(5));

    }

    @Test
    public void testGetCustomerAddress() throws Exception {
        System.out.println(OrderFactory.getCustomerAddress(5));

    }

    @Test
    public void testCreateOrder() throws Exception {

        ArrayList<Integer> tall = new ArrayList<>();
        tall.add(1);
        tall.add(2);
        tall.add(6);

        OrderFactory.createOrder("Scooter express", new Timestamp(System.currentTimeMillis() + 8600000), 3, false, 6, 4, 5, tall);

    }
}