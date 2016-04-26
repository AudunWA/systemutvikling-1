package no.ntnu.iie.stud.cateringstorm.entities.ingredientdish;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;
import no.ntnu.iie.stud.cateringstorm.entities.order.Order;
import no.ntnu.iie.stud.cateringstorm.entities.order.OrderFactory;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * JUnit test class for IngredientDishFactory.
 */
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

    @Test
    public void testGetAllIngredientDishes() throws Exception {

        ArrayList<IngredientDish> test = IngredientDishFactory.getAllIngredientDishes();
        Assert.assertNotNull(test);

    }

    @Test
    public void testRemoveIngredientFromDish() throws Exception {

        Ingredient ingredient = new Ingredient(1, new Timestamp(System.currentTimeMillis() - 10 * (86400000)), "TestIngredient", "Test desc", true, new Date(System.currentTimeMillis() + 10 * (86400000)), 100, "Kg");
        IngredientFactory.createIngredient(ingredient.getArrivalDate(), ingredient.getName(), ingredient.getDescription(), ingredient.isVegetarian(), ingredient.getExpireDate(), ingredient.getAmount(), ingredient.getUnit());
        Dish dish = DishFactory.createDish("RemoveIngredientTest", "Test NOOOOOOOT!!!", 2, true);
        IngredientDishFactory.addIngredientToDish(ingredient.getIngredientId(), dish.getDishId(), 100, "Kg");
        boolean removed = IngredientDishFactory.removeIngredientFromDish(ingredient.getIngredientId(), dish.getDishId());
        Assert.assertEquals(true, removed);

    }
    @Test
    public void testCreateDish() throws Exception {

        Ingredient ingredient = new Ingredient(1, new Timestamp(System.currentTimeMillis() - 10 * (86400000)), "TestIngredient", "Test desc", true, new Date(System.currentTimeMillis() + 10 * (86400000)), 100, "Kg");
        Dish dish = DishFactory.createDish("createDishTest", "Test NOOOOOOOT!!!", 2, true);
        ingredient = IngredientFactory.createIngredient(ingredient.getArrivalDate(), ingredient.getName(), ingredient.getDescription(), ingredient.isVegetarian(), ingredient.getExpireDate(), ingredient.getAmount(), ingredient.getUnit());

        ArrayList<IngredientDish> ingredientDishes = new ArrayList<>();
        ingredientDishes.add(new IngredientDish(ingredient, dish, 10, "Tons"));
        ArrayList<IngredientDish> relationsCreated = IngredientDishFactory.createDish(ingredientDishes, "TestCreate", "testing create", 1, true);

        Assert.assertNotNull(relationsCreated);
    }
    @Test
    public void testGetAllIngredientDishesInOrder() throws Exception {

        ArrayList<Order> orders = OrderFactory.getAllOrders();
        if (orders.size() > 0) {
            ArrayList<IngredientDish> allRelationsInOrder = IngredientDishFactory.getAllIngredientsDishesInOrder(orders.get(1).getOrderId());
            Assert.assertNotNull(allRelationsInOrder);
        }
    }

    @Test
    public void testRemoveAllIngredientFromDish() throws Exception {

        Ingredient ingredient = new Ingredient(1, new Timestamp(System.currentTimeMillis() - 10 * (86400000)), "TestIngredient", "Test desc", true, new Date(System.currentTimeMillis() + 10 * (86400000)), 100, "Kg");
        IngredientFactory.createIngredient(ingredient.getArrivalDate(), ingredient.getName(), ingredient.getDescription(), ingredient.isVegetarian(), ingredient.getExpireDate(), ingredient.getAmount(), ingredient.getUnit());
        Dish dish = DishFactory.createDish("RemoveIngredientTest", "Test NOOOOOOOT!!!", 2, true);
        IngredientDishFactory.addIngredientToDish(ingredient.getIngredientId(), dish.getDishId(), 100, "Kg");
        boolean removed = IngredientDishFactory.removeAllIngredientFromDish(dish.getDishId());
        Assert.assertEquals(true, removed);

    }
    @Test
    public void testGetAllIngredientsInDish() throws Exception {

        Ingredient ingredient = new Ingredient(1, new Timestamp(System.currentTimeMillis() - 10 * (86400000)), "TestIngredient", "Test desc", true, new Date(System.currentTimeMillis() + 10 * (86400000)), 100, "Kg");
        Dish dish = new Dish(1, "getAllIngredientsTest", "Test NOOOOOOOT!!!", 2, true);
        ingredient = IngredientFactory.createIngredient(ingredient.getArrivalDate(), ingredient.getName(), ingredient.getDescription(), ingredient.isVegetarian(), ingredient.getExpireDate(), ingredient.getAmount(), ingredient.getUnit());

        ArrayList<IngredientDish> ingredientDishes = new ArrayList<>();
        ingredientDishes.add(new IngredientDish(ingredient, dish, 10, "Tons"));

        //IngredientDishFactory.createDish(ingredientDishes, "TestCreate", "testing create", 1, true);

        ArrayList<IngredientDish> relationsCreated = IngredientDishFactory.getAllIngredientsInDish((IngredientDishFactory.createDish(ingredientDishes, "TestCreate", "testing create", 1, true).get(0).getDish().getDishId()));

        Assert.assertEquals("TestIngredient", relationsCreated.get(0).getIngredient().getName());

    }

}