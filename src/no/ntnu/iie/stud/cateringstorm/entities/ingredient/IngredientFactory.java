package no.ntnu.iie.stud.cateringstorm.entities.ingredient;

import no.ntnu.iie.stud.cateringstorm.database.Database;

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
        Boolean vegatarian = result.getBoolean("vegetarian");
        Timestamp expire_date = result.getTimestamp("expire_date");

        return new Ingredient(ingredient_id, arrival_date, name, description, vegatarian, expire_date);
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
     * Inserts an ingredient into SQL table ingredient given an ingredient object
     * @param ingredient
     * @return returns the ingredient added or null
     */
    public static Ingredient createIngredient(Ingredient ingredient){

        try (Connection connection = Database.getConnection()){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredient VALUES (?,?,?,?,?,?)")){

                statement.setInt(1, ingredient.getIngredientId());
                statement.setTimestamp(2, ingredient.getArrivalDate());
                statement.setString(3, ingredient.getName());
                statement.setString(4, ingredient.getDescription());
                statement.setBoolean(5, ingredient.isVegetarian());
                statement.setTimestamp(6, ingredient.getExpireDate());

                statement.execute();
                return ingredient;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
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
}

