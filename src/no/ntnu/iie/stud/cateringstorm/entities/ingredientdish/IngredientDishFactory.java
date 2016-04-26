package no.ntnu.iie.stud.cateringstorm.entities.ingredientdish;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class handling database interaction, loading and generating IngredientDish joint entity objects.
 */
public class IngredientDishFactory {

    private static IngredientDish createIngredientDishFromResultSet(ResultSet resultSet) throws SQLException {

        int ingredientId = resultSet.getInt("ingredient_id");
        int dishId = resultSet.getInt("dish_id");
        int quantity = resultSet.getInt("quantity");
        String unit = resultSet.getString("unit");
        Ingredient ingredient = IngredientFactory.getIngredient(ingredientId);
        Dish dish = DishFactory.getDish(dishId);

        return new IngredientDish(ingredient, dish, quantity, unit);
    }

    public static boolean addIngredientToDish(int ingredientId, int dishId, int quantity, String unit) {

        try (Connection connection = Database.getConnection()) {
            // Add the ingredient itself and get ID
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredient_dish VALUES(?, ?, ?, ?)")) {

                statement.setInt(1, ingredientId);
                statement.setInt(2, dishId);
                statement.setInt(3, quantity);
                statement.setString(4, unit);

                int affectedRows = statement.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Dish tempDish;

    public static ArrayList<IngredientDish> createDish(ArrayList<IngredientDish> ingredientDishes, String name, String description, int dishType, boolean active) {

        int generatedId;
        ArrayList<IngredientDish> returnList = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try {
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO dish VALUES(DEFAULT, ?, ?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS)) {

                    statement.setString(1, name);
                    statement.setString(2, description);
                    statement.setInt(3, dishType);
                    statement.setBoolean(4, active);

                    int affectedRows = statement.executeUpdate();
                    if (affectedRows == 0) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return null;
                    }
                    try (ResultSet result = statement.getGeneratedKeys()) {
                        if (result.next()) {
                            generatedId = result.getInt(1);
                            tempDish = new Dish(generatedId, name, description, dishType, active);
                        } else {
                            connection.rollback();
                            connection.setAutoCommit(true);
                            return null; // No ID?
                        }
                    }
                }

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredient_dish VALUES(?,?,?,?)")) {

                    for (IngredientDish ingDish : ingredientDishes) {
                        statement.setInt(1, ingDish.getIngredient().getIngredientId());
                        statement.setInt(2, generatedId);
                        statement.setInt(3, ingDish.getQuantity());
                        statement.setString(4, ingDish.getUnit());
                        statement.addBatch();
                        returnList.add(new IngredientDish(IngredientFactory.getIngredient(ingDish.getIngredient().getIngredientId()), tempDish, ingDish.getQuantity(), ingDish.getUnit()));
                    }
                    statement.executeBatch();
                }
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw e;
            }

            //All good, commence commit
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


        return returnList;
    }

    public static ArrayList<IngredientDish> getAllIngredientsDishesInOrder(int orderId) {
        ArrayList<IngredientDish> temp = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM \n" +
                    "ingredient INNER JOIN ingredient_dish ON ingredient.ingredient_id = ingredient_dish.ingredient_id\n" +
                    "INNER JOIN dish_food_package ON ingredient_dish.dish_id = dish_food_package.dish_id\n" +
                    "INNER JOIN _order_food_package ON dish_food_package.food_package_id = _order_food_package.food_package_id WHERE _order_food_package._order_id = ?; ")) {

                statement.setInt(1, orderId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        temp.add(createIngredientDishFromResultSet(result));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
    /*
    public static IngredientDish addIngredientToNewDish(int ingredientId, int quantity, String unit) {

        int generatedId;

        try (Connection connection = Database.getConnection()) {
            // Add the ingredient itself and get ID
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredient_dish VALUES(?, DEFAULT, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, ingredientId);
                statement.setInt(2, quantity);
                statement.setString(3, unit);

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    return null; // No rows inserted
                }
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        generatedId = result.getInt(1);
                    } else {
                        return null; // No ID?
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return new IngredientDish(IngredientFactory.getIngredient(ingredientId), DishFactory.getDish(generatedId), quantity, unit);
    }*/

    public static ArrayList<IngredientDish> getAllIngredientDishes() {

        ArrayList<IngredientDish> temp = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredient_dish")) {

                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        temp.add(createIngredientDishFromResultSet(result));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static boolean removeIngredientFromDish(int ingredientId, int dishId) {

        ArrayList<IngredientDish> ingDishList = getAllIngredientDishes();
        boolean exists = false;
        for (IngredientDish temp : ingDishList) {
           exists = temp.getIngredient().getIngredientId() == ingredientId && temp.getDish().getDishId() == dishId;
            if (exists) {
                try (Connection connection = Database.getConnection()) {
                    // Add the ingredient itself and get ID
                    try (PreparedStatement statement = connection.prepareStatement("DELETE FROM ingredient_dish WHERE dish_id = ? && ingredient_id = ?")) {

                        statement.setInt(1, dishId);
                        statement.setInt(2, ingredientId);

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        }
            return false;
    }

    //TO BE USED BEFORE ADDING NEW DISHES
    public static boolean removeAllIngredientFromDish(int dishId) {

        try (Connection connection = Database.getConnection()) {
            // Add the ingredient itself and get ID
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM ingredient_dish WHERE dish_id = ?")) {

                statement.setInt(1, dishId);
                statement.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<IngredientDish> getAllIngredientsInDish(int dishId){

        ArrayList<IngredientDish> returnList = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            // Add the ingredient itself and get ID
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredient_dish WHERE dish_id = ?")) {

                statement.setInt(1, dishId);
                statement.execute();

                try (ResultSet resultSet = statement.getResultSet()){
                    while (resultSet.next()){
                        returnList.add(createIngredientDishFromResultSet(resultSet));
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return returnList;
    }
}
