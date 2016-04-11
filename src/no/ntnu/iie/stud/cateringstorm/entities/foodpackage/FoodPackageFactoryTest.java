package no.ntnu.iie.stud.cateringstorm.entities.foodpackage;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Audun on 16.03.2016.
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
        for (FoodPackage foodPackage : foodPackages) {
            System.out.println(foodPackage);
        }
    }

    @Test
    public void testGetFoodPackages() throws Exception {
        ArrayList<FoodPackage> foodPackages = FoodPackageFactory.getFoodPackages(1);
        Assert.assertNotNull(foodPackages);
        for (FoodPackage foodPackage : foodPackages) {
            System.out.println(foodPackage);
        }
    }

    @Test
    public void testInsertNewFoodPackage() throws Exception {
        ArrayList<Dish> dishes = DishFactory.getAllDishes();
        FoodPackage foodPackage = FoodPackageFactory.createFoodPackage("Unit test package", 8008135, dishes);
        Assert.assertNotNull(foodPackage);
        System.out.println(foodPackage);
    }
}