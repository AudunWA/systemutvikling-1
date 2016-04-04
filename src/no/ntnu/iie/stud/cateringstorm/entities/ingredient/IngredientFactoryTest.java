package no.ntnu.iie.stud.cateringstorm.entities.ingredient;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Chris on 30.03.2016.
 */
public class IngredientFactoryTest {

    @Test
    public void testViewSingleIngredient() throws Exception {
        System.out.println(IngredientFactory.viewSingleIngredient(1));

    }

    @Test
    public void testViewAllIngredient() throws Exception {
        for (Ingredient ingredient : IngredientFactory.viewAllIngredient()){
            System.out.println(ingredient);
        }
    }

    @Test
    public void testInsertNewIngredient() throws Exception {
        Ingredient ingredient = IngredientFactory.insertNewIngredient("Unit test", "Unit test", 8008135, "testunit", false, new Timestamp(System.currentTimeMillis() - 10*(86400000)), new Date(System.currentTimeMillis() + 10*(86400000)));
        Assert.assertNotNull(ingredient);
        System.out.println("ingredient = " + ingredient);
    }

    @Test
    public void testShowExpired() throws Exception {
        for (Ingredient ingredient : IngredientFactory.showExpired()){
            System.out.println(ingredient);
        }
    }

    @Test
    public void testViewAllIngredientByDishId() throws Exception {

        for (Ingredient ingredient : IngredientFactory.viewAllIngredientByDishId(1)) {

            System.out.println(ingredient);
        }

    }
}