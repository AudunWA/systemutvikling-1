package no.ntnu.iie.stud.cateringstorm.entities.ingredient;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * JUnit test class for IngredientFactory.
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
        Ingredient ingredient = new Ingredient(1, new Timestamp(System.currentTimeMillis() - 10 * (86400000)), "TestIngredient", "Test desc", true, new Date(System.currentTimeMillis() + 10 * (86400000)), 100, "Kg"),
                testIngredient = IngredientFactory.createIngredient(ingredient.getArrivalDate(), ingredient.getName(), ingredient.getDescription(), ingredient.isVegetarian(), ingredient.getExpireDate(), ingredient.getAmount(), ingredient.getUnit());
        Assert.assertNotNull(testIngredient);
        Assert.assertEquals(ingredient.getName(), testIngredient.getName());
        Assert.assertEquals(ingredient.getAmount(), testIngredient.getAmount(), 0.001);
    }

    @Test
    public void testShowExpired() throws Exception {
        for (Ingredient ingredient : IngredientFactory.getExpiredIngredients()) {
            Assert.assertTrue(new Date(System.currentTimeMillis()).after(ingredient.getExpireDate()));
        }
    }

    @Test
    public void testViewAllIngredientByDishId() throws Exception {
        ArrayList<Ingredient> ingredList = new ArrayList<>();
        ingredList.add(IngredientFactory.getIngredient(1));
        ingredList.add(IngredientFactory.getIngredient(2));
        ingredList.add(IngredientFactory.getIngredient(3));
        ingredList.add(IngredientFactory.getIngredient(4));
        Dish dish = DishFactory.createDish("Testname", "Test desc", 2, true, ingredList);
        Assert.assertNotNull(dish);// FIXME: Maybe abundant?
        ArrayList<Ingredient> ingredients = IngredientFactory.getIngredients(dish.getDishId());
        Assert.assertNotNull(ingredients);
        for (int i = 0; i < ingredients.size(); i++) {
            Assert.assertNotNull(ingredients.get(i));
            Assert.assertEquals(ingredients.get(i).getIngredientId(), ingredList.get(i).getIngredientId());
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