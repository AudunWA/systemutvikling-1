package no.ntnu.iie.stud.cateringstorm.entities.order;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Audun on 11.03.2016.
 */
// FIXME: Inspect methods, fix echterbugs and use asserts in every test
public class OrderFactoryTest {

    @Test
    public void testGetOrder() throws Exception {
        Assert.assertNotNull(OrderFactory.getOrder(1));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        ArrayList<Order> orders = OrderFactory.getAllOrders();
        Assert.assertNotNull(orders);
        for (int i = 0; i < orders.size(); i++) {
            Assert.assertNotNull(orders.get(i));
            Assert.assertTrue(orders.get(i) instanceof Order);//FIXME: Redundant?
        }
    }

    @Test
    public void testGetOrdersByQuery() throws Exception {
        String testName = "Mark";
        ArrayList<Order> orders = OrderFactory.getOrdersByQuery(testName);
        Assert.assertNotNull(orders);
        for (Order order : orders) {
            Assert.assertTrue(order.getCustomerName().contains(testName));
        }
    }

    @Test
    public void testCreateOrder() throws Exception {

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        Order order = OrderFactory.createOrder("Scooter express", new Timestamp(System.currentTimeMillis() + 8600000), 3, false, -1, 4, -1, -1, numbers);
        Assert.assertNotNull(order);
    }

    @Test
    public void testSetOrderState() throws Exception {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        Order test1 = OrderFactory.createOrder("Scooter express", new Timestamp(System.currentTimeMillis() + 8600000), 3, false, -1, 4, -1, -1, numbers);
        OrderFactory.setOrderState(test1.getOrderId(), 2);
        Order test2 = OrderFactory.getOrder(test1.getOrderId());
        Assert.assertNotEquals(test1.getStatus(), test2.getStatus());
        Assert.assertEquals(2, test2.getStatus());
    }
    @Test
    public void testGetAllOrdersChauffeur() throws Exception {
        ArrayList<Order> orders = OrderFactory.getAllOrdersChauffeur();
        Assert.assertNotNull(orders);
    }

    // TODO: Maybe test this further
    @Test
    public void testGetSalesForPeriod() throws Exception {
        HashMap<LocalDate, Double> sales = OrderFactory.getSalesForPeriod(LocalDate.now().minusDays(7), LocalDate.now());
        Assert.assertNotNull(sales);
        /*for(Map.Entry<LocalDate, Double> entry : sales.entrySet()) {
            System.out.println("Sales entry: " + entry.getKey() + ", " + entry.getValue());
        }*/
    }
}