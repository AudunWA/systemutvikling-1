package no.ntnu.iie.stud.cateringstorm.entities.ingredientdish;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * JUnit test class for IngredientDishFactory.
 */
//TODO: Finish this class after Factory is completed
public class IngredientDishFactoryTest {

    @Test
    public void testAddIngredientToDish() throws Exception {
        /*int ingredientId, int dishId, int quantity, String unit*/

        Ingredient ingredient = new Ingredient(1, new Timestamp(System.currentTimeMillis() - 10 * (86400000)), "TestIngredient", "Test desc", true, new Date(System.currentTimeMillis() + 10 * (86400000)), 100, "Kg");
        ingredient = IngredientFactory.createIngredient(ingredient.getArrivalDate(), ingredient.getName(), ingredient.getDescription(), ingredient.isVegetarian(), ingredient.getExpireDate(), ingredient.getAmount(), ingredient.getUnit());
        Dish dish = DishFactory.createDish("Penguin", "NOOOOOT NOOOOOOOT!!!", 2, true);
        boolean ingredientAdded = IngredientDishFactory.addIngredientToDish(ingredient.getIngredientId(), dish.getDishId(), 100, "Kg");
        Assert.assertEquals(true, ingredientAdded);

    }

   /* @Test
    public void testAddIngredientToNewDish() throws Exception {

    }*/
    // TODO: Write unit tests.
    @Test
    public void testGetAllIngredientDishes() throws Exception {

    }

    @Test
    public void testRemoveIngredientFromDish() throws Exception {

    }
    @Test
    public void testCreateDish() throws Exception {

    }
    @Test
    public void testGetAllIngredientDishesInOrder() throws Exception {

    }
    @Test
    public void testRemoveAllIngredientFromDish() throws Exception {

    }
    @Test
    public void testGetAllIngredientsInDish() throws Exception {

    }

}