package no.ntnu.iie.stud.cateringstorm.entities.dish;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Unit test class for DishFactory.
 */
public class DishFactoryTest {

    @Test
    public void testCreateDish() throws Exception {
        Dish dish = DishFactory.createDish("TestCreateDish", "TestCreateDish desc", 2, true);
        Assert.assertNotNull(dish);
    }

    @Test
    public void testGetDish() throws Exception {
        Dish dish = DishFactory.createDish("Testgetdish", "TestGetDish desc", 2, true),
                testDish = DishFactory.getDish(dish.getDishId());
        Assert.assertNotNull(testDish);
        Assert.assertEquals(dish.getDishId(), testDish.getDishId());
        System.out.println(DishFactory.getDish(1));
    }

    @Test
    public void testGetAllDishes() throws Exception {
        ArrayList<Dish> dishes = DishFactory.getAllDishes();
        Assert.assertNotNull(dishes);
        for (Dish dish : dishes) {
            Assert.assertNotNull(dish);
        }
    }

    @Test
    public void testEditDishStatus() throws Exception {
        Dish dish = DishFactory.createDish("Testgetdish", "TestGetDish desc", 2, true);
        DishFactory.editDishStatus(dish.getDishId(), false);
        Dish editedDish = DishFactory.getDish(dish.getDishId());
        Assert.assertTrue(!editedDish.isActive());//Check
    }

    @Test
    public void testEditDishDescription() throws Exception {
        Dish dish = DishFactory.createDish("Testgetdish", "TestGetDish desc", 2, true);
        int affectedRows = DishFactory.editDishDescription(dish.getDishId(), "Test");
        Assert.assertEquals(1, affectedRows);
        Dish editedDish = DishFactory.getDish(dish.getDishId());
        Assert.assertNotEquals(dish.getDescription(), editedDish.getDescription());
    }

    @Test
    public void testEditDishName() throws Exception {
        Dish dish = DishFactory.createDish("Testgetdish", "TestGetDish desc", 2, true);
        int affectedRows = DishFactory.editDishName(dish.getDishId(), "Testnan");
        Assert.assertEquals(1, affectedRows);
        Dish editedDish = DishFactory.getDish(dish.getDishId());
        Assert.assertNotEquals(dish.getName(), editedDish.getName());
    }

    @Test
    public void testUpdateDish() throws Exception {
        final String newDescription = "Changed.";

        Dish dish = DishFactory.createDish("testUpdateDish", "Changeme", 1, true);
        Assert.assertNotNull(dish);
        dish.setDescription(newDescription);

        int affectedRows = DishFactory.updateDish(dish);
        Assert.assertEquals(affectedRows, 1);

        dish = DishFactory.getDish(dish.getDishId());
        Assert.assertNotNull(dish);
        Assert.assertEquals(dish.getDescription(), newDescription);
    }
    @Test
    public void testGetDishes() throws Exception {
        ArrayList<Dish> dishes = DishFactory.getDishes(1); // We assume food package 1 exists
        Assert.assertNotNull(dishes);
        for (Dish dish : dishes) {
            Assert.assertNotNull(dish);
        }
    }
}