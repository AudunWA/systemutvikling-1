package no.ntnu.iie.stud.cateringstorm.entities.ingredientdish;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.dish.DishFactory;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.IngredientFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Chris on 21.04.2016.
 */
public class IngredientDishFactory {

    private static IngredientDish createIngredientDishFromResultSet(ResultSet resultSet) throws SQLException{

        int ingredientId = resultSet.getInt("ingredient_id");
        int dishId = resultSet.getInt("dish_id");
        int quantity = resultSet.getInt("quantity");
        String unit = resultSet.getString("unit");
        Ingredient ingredient = IngredientFactory.getIngredient(ingredientId);
        Dish dish = DishFactory.getDish(dishId);

        return new IngredientDish(ingredient,dish,quantity,unit);
    }

    public static boolean addIngredientToDish(int ingredientId, int dishId, int quantity, String unit){

        try (Connection connection = Database.getConnection()) {
            // Add the ingredient itself and get ID
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredient_dish VALUES(?, ?, ?, ?)")) {

                statement.setInt(1, ingredientId);
                statement.setInt(2, dishId);
                statement.setInt(3, quantity);
                statement.setString(4, unit);

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    return false; // No rows inserted
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static IngredientDish addIngredientToNewDish(int ingredientId, int quantity, String unit){

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
    }

    public static ArrayList<IngredientDish> getAllIngredientDishes(){

        ArrayList<IngredientDish> temp = new ArrayList<>();

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredient_dish")){

                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()){
                        temp.add(createIngredientDishFromResultSet(result));
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return temp;
    }

    public static boolean RemoveIngredientFromDish(int ingredientId, int dishId) {

        boolean check = false;
        ArrayList<IngredientDish> ingDishList = getAllIngredientDishes();

        for (IngredientDish temp : ingDishList) {
            if (temp.getIngredient().getIngredientId() == ingredientId && temp.getDish().getDishId() == dishId) {
                check = true;
            }
        }
        if (check) {
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
        } else {
            return false;
        }
    }

}
