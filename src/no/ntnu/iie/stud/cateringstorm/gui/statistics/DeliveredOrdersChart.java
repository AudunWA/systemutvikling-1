package no.ntnu.iie.stud.cateringstorm.gui.statistics;

import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import no.ntnu.iie.stud.cateringstorm.gui.util.DateUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Audun on 14.04.2016.
 */
public class DeliveredOrdersChart extends ChartPanel {
    public DeliveredOrdersChart() {
        super(ChartFactory.createBarChart("Delivered orders last 7 days", "Date", "Delivered orders", generateCategoryDataset()));
        //super(ChartFactory.createBarChart("Delivered orders last 7 days", "Date", "Delivered orders", generateCategoryDatasetOrderTime()));

        // Set Y-axis to only display whole numbers
        CategoryPlot plot = getChart().getCategoryPlot();
        NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    }

    public static ChartPanel generateOrdersOrderedChart(LocalDate startTime, LocalDate endTime) {
        ChartPanel chartPanel = new ChartPanel(ChartFactory.createBarChart("Orders placed last 7 days", "Date", "Orders placed", generateCategoryDatasetOrderTime(startTime, endTime)));
        return chartPanel;
    }

    public static ChartPanel generateIncomeChart(LocalDate startTime, LocalDate endTime) {
        ChartPanel chartPanel = new ChartPanel(ChartFactory.createLineChart("Income last 7 days", "Date", "Income (NOK)", generateIncomeDataset(startTime, endTime)));
        return chartPanel;
    }

    private static CategoryDataset generateCategoryDataset() {
        // Get all orders // TODO: Delivered only
        ArrayList<Order> orders = OrderFactory.getAllOrders();
        if (orders == null) {
            return null; // TODO: Throw exception or log error?
        }

        // Sort by order date
        //orders.sort(new OrderTimestampComparator());

        // The last week
        LocalDate endLocalDate = LocalDate.now();
        LocalDate startLocalDate = endLocalDate.minusDays(7);

        long daysTotal = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);

        // Create the correct X-axis scale (from first date to last)
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        for (long i = 0; i <= daysTotal; i++) {
            LocalDate date = startLocalDate.plusDays(i);
            dataset.addValue(0, "orders", formatter.format(date));
        }


        // Increment y-values of dates containing orders
        for (Order order : orders) {
            LocalDate date = DateUtil.convertDate(order.getDeliveryDate()).toLocalDate();
            if (date.isBefore(startLocalDate) || date.isAfter(endLocalDate)) {
                continue; // Outside range, ignore
            }

            int currentValue = dataset.getValue("orders", formatter.format(date)).intValue();
            dataset.setValue(currentValue + 1, "orders", formatter.format(date));
        }
        return dataset;
    }

    private static CategoryDataset generateCategoryDatasetOrderTime(LocalDate startTime, LocalDate endTime) {
        // Get all orders
        ArrayList<Order> orders = OrderFactory.getAllOrders();
        if (orders == null) {
            return null; // TODO: Throw exception or log error?
        }

        long daysTotal = ChronoUnit.DAYS.between(startTime, endTime);

        // Create the correct X-axis scale (from first date to last)
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        for (long i = 0; i <= daysTotal; i++) {
            LocalDate date = startTime.plusDays(i);
            dataset.addValue(0, "orders", formatter.format(date));
        }


        // Increment y-values of dates containing orders
        for (Order order : orders) {
            LocalDate date = DateUtil.convertDate(order.getOrderDate()).toLocalDate();
            if (date.isBefore(startTime) || date.isAfter(endTime)) {
                continue; // Outside range, ignore
            }

            int currentValue = dataset.getValue("orders", formatter.format(date)).intValue();
            dataset.setValue(currentValue + 1, "orders", formatter.format(date));
        }
        return dataset;
    }

    private static CategoryDataset generateIncomeDataset(LocalDate startTime, LocalDate endTime) {
        // Get all orders
        HashMap<LocalDate, Double> salesMap = OrderFactory.getSalesForPeriod(startTime, endTime);
        if (salesMap == null) {
            return null; // TODO: Throw exception or log error?
        }

        long daysTotal = ChronoUnit.DAYS.between(startTime, endTime);

        // Create the correct X-axis scale (from first date to last)
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        for (long i = 0; i <= daysTotal; i++) {
            LocalDate date = startTime.plusDays(i);
            dataset.addValue(0, "income", formatter.format(date));
        }


        // Increment y-values of dates containing orders
        for (Map.Entry<LocalDate, Double> sale : salesMap.entrySet()) {
            LocalDate date = sale.getKey();
            if (date.isBefore(startTime) || date.isAfter(endTime)) {
                continue; // Outside range, ignore
            }
            dataset.setValue(sale.getValue(), "income", formatter.format(date));
        }
        return dataset;
    }
}
