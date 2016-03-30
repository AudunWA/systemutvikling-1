package no.ntnu.iie.stud.cateringstorm.entities.dish;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chris on 16.03.2016.
 */
public class DishFactoryTest {

    @Test
    public void testViewDish() throws Exception {

        for (int i = 0; i < 10; i++) {
            System.out.println(DishFactory.viewDish(i));
        }

    }

    @Test
    public void testGetAllDishes() throws Exception {

        for (Dish dish :  DishFactory.getAllDishes()) {
            System.out.println(dish);
        }


    }
}