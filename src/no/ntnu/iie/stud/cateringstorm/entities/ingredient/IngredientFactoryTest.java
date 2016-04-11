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
        System.out.println(IngredientFactory.getIngredient(1));

    }

    @Test
    public void testGetAllIngredients() throws Exception {
        for (Ingredient ingredient : IngredientFactory.getAllIngredients()){
            System.out.println(ingredient);
        }
    }

    @Test
    public void testInsertNewIngredient() throws Exception {
        Ingredient ingredient = IngredientFactory.createIngredient("Unit test", "Unit test", 8008135, "testunit", false, new Timestamp(System.currentTimeMillis() - 10*(86400000)), new Date(System.currentTimeMillis() + 10*(86400000)));
        Assert.assertNotNull(ingredient);
        System.out.println("ingredient = " + ingredient);
    }

    @Test
    public void testShowExpired() throws Exception {
        for (Ingredient ingredient : IngredientFactory.getExpiredIngredients()){
            System.out.println(ingredient);
        }
    }

    @Test
    public void testViewAllIngredientByDishId() throws Exception {

        for (Ingredient ingredient : IngredientFactory.getIngredients(1)) {

            System.out.println(ingredient);
        }

    }

    @Test
    public void testUpdateIngredientAmount() throws Exception {
        Ingredient ingredient = IngredientFactory.getIngredient(1); // Let's say ingredient 1 exists
        Assert.assertNotNull(ingredient);
        ingredient.incrementAmount();
        double incrementedAmunt = ingredient.getAmount();
        IngredientFactory.updateIngredientAmount(ingredient.getIngredientId(), incrementedAmunt);

        ingredient = IngredientFactory.getIngredient(ingredient.getIngredientId());
        Assert.assertNotNull(ingredient);
        Assert.assertEquals(ingredient.getAmount(), incrementedAmunt, 1e-9);
    }
}