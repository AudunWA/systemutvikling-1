package no.ntnu.iie.stud.cateringstorm.gui.util;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;

import java.util.Comparator;

/**
 * Comparator class to safely compare Timestamps, avoiding milliesecond differences
 */
public class OrderTimestampComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getDeliveryDate().compareTo(o2.getDeliveryDate());
    }
}
