package no.ntnu.iie.stud.cateringstorm.entities.order;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Audun on 11.03.2016.
 */
public class OrderFactoryTest {

    @Test
    public void testGetOrder() throws Exception {
        Assert.assertNotNull(OrderFactory.getOrder(1));
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
    public void testCreateOrder() throws Exception {

        ArrayList<Integer> tall = new ArrayList<>();
        tall.add(1);
        tall.add(2);
        tall.add(3);

        OrderFactory.createOrder("Scooter express", new Timestamp(System.currentTimeMillis() + 8600000), 3, false, 6, 4, 5, tall);

    }

    @Test
    public void testGetSalesForPeriod() throws Exception {
        HashMap<LocalDate, Double> sales = OrderFactory.getSalesForPeriod(LocalDate.now().minusDays(7), LocalDate.now());
        Assert.assertNotNull(sales);
        for(Map.Entry<LocalDate, Double> entry : sales.entrySet()) {
            System.out.println("Sales entry: " + entry.getKey() + ", " + entry.getValue());
        }
    }
}