package no.ntnu.iie.stud.cateringstorm.entities.dish;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chris on 16.03.2016.
 */
public class DishFactoryTest {

    @Test
    public void testViewSingleDish() throws Exception {

        System.out.println(DishFactory.viewSingleDish(1));

    }

    @Test
    public void testGetAllDishes() throws Exception {

        for (Dish dish :  DishFactory.getAllDishes()) {
            System.out.println(dish);
        }
    }

    @Test
    public void testCreateDish() throws Exception {
        Dish knutSpesial = new Dish(0, "Penguin", "NOOOOOT NOOOOOOOT!!!", 2, true);
        knutSpesial = DishFactory.createDish(knutSpesial);
        Assert.assertNotNull(knutSpesial);
        System.out.println(knutSpesial);
    }

    @Test
    public void testEditDish() throws Exception {

        DishFactory.editDish(7, true);

    }

    @Test
    public void testEditDishDescription() throws Exception {
        DishFactory.editDishDescription(1, "Test");

    }

    @Test
    public void testEditDishName() throws Exception {
        DishFactory.editDishName(1, "Test Dish");

    }
}