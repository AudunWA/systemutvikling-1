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
    public void testGetIngredient() throws Exception {
        Assert.assertNotNull(IngredientFactory.getIngredient(1));
    }

    @Test
    public void testGetAllIngredients() throws Exception {
        Assert.assertNotNull(IngredientFactory.getAllIngredients());
    }

    @Test
    public void testCreateIngredient() throws Exception {
        /*int ingredientId, Timestamp arrivalDate, String name, String description, boolean vegetarian, Date expireDate, double amount, String unit*/
        Ingredient ingredient = new Ingredient(1, new Timestamp(System.currentTimeMillis() - 10*(86400000)),"TestIngredient","Test desc",true, new Date(System.currentTimeMillis() + 10*(86400000)),100,"Kg"),
        testIngredient= IngredientFactory.createIngredient(ingredient.getArrivalDate(),ingredient.getName(),ingredient.getDescription(),ingredient.isVegetarian(),ingredient.getExpireDate(),ingredient.getAmount(),ingredient.getUnit());
        Assert.assertNotNull(testIngredient);
        Assert.assertEquals(ingredient.getName(),testIngredient.getName());
        Assert.assertEquals(ingredient.getAmount(),testIngredient.getAmount(),0.001);
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