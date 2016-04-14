package no.ntnu.iie.stud.cateringstorm.gui.statistics;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.util.DateUtil;
import no.ntnu.iie.stud.cateringstorm.gui.util.OrderTimestampComparator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.*;

/**
 * Created by Audun on 14.04.2016.
 */
public class DeliveredOrdersChart extends ChartPanel {
    public DeliveredOrdersChart() {
        super(ChartFactory.createBarChart("Delivered orders last 7 days", "Date", "Delivered orders", generateCategoryDataset()));

        // Set Y-axis to only display whole numbers
        CategoryPlot plot = getChart().getCategoryPlot();
        NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    }

    private static CategoryDataset generateCategoryDataset() {
        // Get all orders // TODO: Delivered only
        ArrayList<Order> orders = OrderFactory.getAllOrders();
        if(orders == null) {
            return null; // TODO: Throw exception or log error?
        }

        // Sort by order date
        orders.sort(new OrderTimestampComparator());

        // The last week
        LocalDate endLocalDate = LocalDate.now();
        LocalDate startLocalDate = endLocalDate.minusDays(7);

        long daysTotal = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        for (long i = 0; i <= daysTotal; i++) {
            LocalDate date = startLocalDate.plusDays(i);
           dataset.addValue(0, "orders", formatter.format(date));
        }


        for(Order order : orders) {
            LocalDate date = DateUtil.convertDate(order.getDeliveryDate());
            if(date.isBefore(startLocalDate) || date.isAfter(endLocalDate)) {
                continue; // Outside range
            }

            dataset.setValue(dataset.getValue("orders", formatter.format(date)).intValue() + 1, "orders", formatter.format(date));
        }
        return dataset;
    }
}
