package no.ntnu.iie.stud.cateringstorm.entities.dish;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;
import no.ntnu.iie.stud.cateringstorm.entities.recurringorder.RecurringOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Chris on 16.03.2016.
 */
public final class DishFactory {

    /**
     * Creates a Dish object from result
     * @param result
     * @return Dish
     * @throws SQLException
     */
    private static Dish createDishFromResultSet(ResultSet result) throws SQLException{
        int dishId = result.getInt("dish_id");
        String name = result.getString("name");
        String desc = result.getString("description");
        int type = result.getInt("dish_type_id");
        boolean active = result.getBoolean("active");

        return new Dish(dishId, name, desc, type, active);
    }

    /**
     * Gets a single dish from the SQL table dish by giving its ID
     * @param dishId
     * @return Dish
     */
    public static Dish getDish(int dishId){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM dish WHERE dish_id = ?")){

                statement.setInt(1, dishId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    if (result.next()) {

                        return createDishFromResultSet(result);
                    }

                }

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates an Arraylist of all the dishes in the SQL table dish
     * @return ArrayList<Dish>
     */
    public static ArrayList<Dish> getAllDishes(){
        ArrayList<Dish> dishes = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM dish")){
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()) {

                        dishes.add(createDishFromResultSet(result));
                    }

                }
                return dishes;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets all dishes with name matching a query.
     * @return An ArrayList containing all dishes matched.
     */
    public static ArrayList<Dish> getAllDishesByQuery(String searchQuery) {
        ArrayList<Dish> dishes = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM dish WHERE name LIKE ?")){
                statement.setString(1, '%' + searchQuery + '%');
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()) {
                        dishes.add(createDishFromResultSet(result));
                    }

                }
                return dishes;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets all active dishes with name matching a query.
     * @return An ArrayList containing all dishes matched.
     */
    public static ArrayList<Dish> getActiveDishesByQuery(String searchQuery) {
        ArrayList<Dish> dishes = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM dish WHERE name LIKE ? AND active = TRUE")){
                statement.setString(1, '%' + searchQuery + '%');
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()) {
                        dishes.add(createDishFromResultSet(result));
                    }

                }
                return dishes;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets all dishes of a food package
     * @param foodPackageId The ID of the food package
     * @return ArrayList with all the Dishes<Dish>
     */
    public static ArrayList<Dish> getDishes(int foodPackageId){
        ArrayList<Dish> dishes = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM dish" +
                    " INNER JOIN dish_food_package USING (dish_id)" +
                    " WHERE dish_food_package.food_package_id = ?")){
                statement.setInt(1, foodPackageId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()) {
                        dishes.add(createDishFromResultSet(result));
                    }

                }
                return dishes;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates an arraylist of the active dishes in the SQL table dish
     * @return ArrayList<Dish>
     */
    public static ArrayList<Dish> getActiveDishes(){
        ArrayList<Dish> dishes = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM dish WHERE active = TRUE")){
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()) {

                        dishes.add(createDishFromResultSet(result));
                    }

                }
                return dishes;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public static Dish createDish(String name, String description, int dishType, boolean active){

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO dish VALUES(DEFAULT, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, dishType);
                statement.setBoolean(4, active);

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    return null; // No rows inserted
                }

                int generatedId;
                try (ResultSet result = statement.getGeneratedKeys()) {
                    if (result.next()) {
                        generatedId = result.getInt(1);
                    } else {
                        return null; // No ID?
                    }
                }

                Dish dish = new Dish(generatedId, name, description, dishType, active);
                return dish;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Dish createDish(String name, String description, int dishType, boolean active, ArrayList<Ingredient> ingredients){
        int generatedId;

        try (Connection connection = Database.getConnection()) {
            try {
                connection.setAutoCommit(false);

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO dish VALUES(DEFAULT, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
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

                    generatedId = Database.getGeneratedKeys(statement);
                    if(generatedId == -1) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return null;
                    }
                }

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO ingredient_dish VALUES (?, ?, ?)")) {
                    for (Ingredient ingredient : ingredients) {
                        statement.setInt(1, ingredient.getIngredientId());
                        statement.setInt(2, generatedId);
                        statement.setDouble(3, ingredient.getAmount()); // TODO: Not right, right?
                    }
                    statement.executeBatch();
                }
            } catch (SQLException e){
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

        return new Dish(generatedId, name, description, dishType, active);
    }

    /**
     * Edits the status of a dish (If the dish is active or not)
     * @param dishId
     * @param active
     * @return int
     */
    public static int editDish(int dishId, boolean active){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE g_tdat1006_t6.dish SET active = ? WHERE dish.dish_id = ?")) {

                statement.setBoolean(1, active);
                statement.setInt(2, dishId);

                statement.execute();
                return dishId;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public static String editDishDescription(int dishId, String text){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE g_tdat1006_t6.dish SET description = ? WHERE dish.dish_id = ?")) {

                statement.setString(1, text);
                statement.setInt(2, dishId);

                statement.execute();
                return text;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    public static String editDishName(int dishId, String text){
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE g_tdat1006_t6.dish SET name = ? WHERE dish.dish_id = ?")) {

                statement.setString(1, text);
                statement.setInt(2, dishId);

                statement.execute();
                return text;

            }
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     Runs an UPDATE-query of a dish, with all its columns.
     * @param dish The dish to update
     * @return An integer representing affected rows
     */
    public static int updateDish(Dish dish) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE dish SET dish_type_id = ?, name = ?, description = ?, active = ? WHERE dish_id = ?")) {
                statement.setInt(1, dish.getDishType());
                statement.setString(2, dish.getName());
                statement.setString(3, dish.getDescription());
                statement.setBoolean(4, dish.isActive());
                statement.setInt(5, dish.getDishId());

                return statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}
