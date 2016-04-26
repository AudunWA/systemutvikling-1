package no.ntnu.iie.stud.cateringstorm.entities.foodpackage;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * JUnit test class for FoodPackageFactory.
 */
public class FoodPackageFactoryTest {

    @Test
    public void testGetFoodPackage() throws Exception {
        // Existing package
        FoodPackage foodPackage = FoodPackageFactory.getFoodPackage(1);
        Assert.assertNotNull(foodPackage);
        System.out.println(foodPackage);

        // Non-existing package
        foodPackage = FoodPackageFactory.getFoodPackage(-1);
        Assert.assertNull(foodPackage);
    }

    @Test
    public void testGetAllFoodPackages() throws Exception {
        ArrayList<FoodPackage> foodPackages = FoodPackageFactory.getAllFoodPackages();
        Assert.assertNotNull(foodPackages);
        /*for (FoodPackage foodPackage : foodPackages) {
            System.out.println(foodPackage);
        }*/
    }

    @Test
    public void testGetFoodPackages() throws Exception {

        ArrayList<Dish> dishes = DishFactory.getAllDishes();
        FoodPackage foodPackage = FoodPackageFactory.createFoodPackage("Unit test package1", 8135, dishes),
                foodPackage2 = FoodPackageFactory.createFoodPackage("Unit test package2", 8145, dishes);
        //Filling arraylist
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(foodPackage.getFoodPackageId());
        numbers.add(foodPackage2.getFoodPackageId());
        //Creating test order
        Order order = OrderFactory.createOrder("TestGetFoodPackageOrder", new Timestamp(System.currentTimeMillis() - 888 + 8600000), 3, false, -1, 4, -1, -1, numbers);
        ArrayList<FoodPackage> foodPackages = FoodPackageFactory.getFoodPackages(order.getOrderId());
        Assert.assertNotNull(foodPackages);
        for (int i = 0; i < foodPackages.size(); i++) {
            Assert.assertEquals((int) numbers.get(i), foodPackages.get(i).getFoodPackageId());
        }
    }

    @Test
    public void testInsertNewFoodPackage() throws Exception {
        ArrayList<Dish> dishes = DishFactory.getAllDishes();
        FoodPackage foodPackage = FoodPackageFactory.createFoodPackage("Unit test package", 8008135, dishes);
        Assert.assertNotNull(foodPackage);
        //System.out.println(foodPackage);
    }
}