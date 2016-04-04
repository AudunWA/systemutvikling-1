package no.ntnu.iie.stud.cateringstorm.entities.ingredient;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.foodpackage.FoodPackage;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Chris on 30.03.2016.
 */
public final class IngredientFactory {

    /**
     * result set - used for creating an ingredient object from result.
     * @param result
     * @return an ingredient object
     * @throws SQLException
     */
    private static Ingredient createIngredientFromResultSet(ResultSet result) throws SQLException{

        int ingredient_id = result.getInt("ingredient_id");
        Timestamp arrival_date = result.getTimestamp("arrival_date");
        String name = result.getString("name");
        String description = result.getString("description");
        Boolean vegetarian = result.getBoolean("vegetarian");
        Date expire_date = result.getDate("expire_date");
        double amount = result.getDouble("amount");
        String unit = result.getString("unit");

        return new Ingredient(ingredient_id, arrival_date, name, description, vegetarian, expire_date, amount, unit);
    }

    /**
     * Shows a single ingredient from the SQL table ingredient given an ID
     * @param ingredient_id
     * @return An ingredient
     */
    public static Ingredient viewSingleIngredient(int ingredient_id){

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredient WHERE ingredient_id = ?")){

                statement.setInt(1, ingredient_id);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    if (result.next()){

                        return createIngredientFromResultSet(result);
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a list of ingredients
     * @return an arrayylist containing all ingredients in the SQL table ingrdient
     */
    public static ArrayList<Ingredient> viewAllIngredient(){

        ArrayList<Ingredient> temp = new ArrayList<>();

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredient")){

                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()){

                        temp.add(createIngredientFromResultSet(result));
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * Shows expired ingredients
     * @return Arraylist containing the expired ingredients
     */
    public static ArrayList<Ingredient> showExpired(){

        Timestamp nowTime = new Timestamp(System.currentTimeMillis());

        ArrayList<Ingredient> temp = new ArrayList<>();

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredient WHERE ? > expire_date")){

                statement.setTimestamp(1, nowTime);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()){

                        temp.add(createIngredientFromResultSet(result));
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * Inserts an ingredient into SQL table ingredient given an ingredient object
     * @param name
     * @param description
     * @param amount
     * @param unit
     * @param vegetarian
     * @param arrivalDate
     * @param expireDate
     * @return
     */
    public static Ingredient insertNewIngredient(String name, String description, double amount, String unit, boolean vegetarian, Timestamp arrivalDate, Date expireDate) {
        int generatedId; // The AUTO_INCREMENT from INSERT

        try (Connection connection = Database.getConnection()) {
                // Add the ingredient itself and get ID
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredient VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                    statement.setTimestamp(1, arrivalDate);
                    statement.setString(2, name);
                    statement.setString(3, description);
                    statement.setBoolean(4, vegetarian);
                    statement.setDate(5, expireDate);
                    statement.setDouble(6, amount);
                    statement.setString(7, unit);

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

        // Create object and return
        Ingredient ingredient = new Ingredient(generatedId, arrivalDate, name, description, vegetarian, expireDate, amount, unit);
        return ingredient;
    }

    public static ArrayList<Ingredient> viewAllIngredientByDishId(int dishId){

        ArrayList<Ingredient> temp = new ArrayList<>();

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM ingredient INNER JOIN ingredient_dish ON (ingredient.ingredient_id = ingredient_dish.ingredient_id) WHERE ingredient_dish.dish_id = ?;")){


                statement.setInt(1, dishId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()){

                        temp.add(createIngredientFromResultSet(result));
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return temp;
    }
}

