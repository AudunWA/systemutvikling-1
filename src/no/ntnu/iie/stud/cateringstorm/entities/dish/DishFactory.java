package no.ntnu.iie.stud.cateringstorm.entities.dish;

import no.ntnu.iie.stud.cateringstorm.database.Database;
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
     * View a single dish from the SQL table dish by giving its dishID
     * @param dishId
     * @return Dish
     */
    public static Dish viewSingleDish(int dishId){
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
     * Creates an arraylist of all the dishes in the SQL table dish
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
     * takes a dish and inserts it into the SQL table dish
     * @param newDish
     * @return Dish
     */
    public static Dish createDish(Dish newDish){

        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO dish VALUES(DEFAULT, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setString(1, newDish.getName());
                statement.setString(2, newDish.getDescription());
                statement.setInt(3, newDish.getDishType());
                statement.setBoolean(4, newDish.isActive());

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

                Dish dish = new Dish(generatedId, newDish.getName(), newDish.getDescription(), newDish.getDishType(), newDish.isActive());
                return dish;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
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
}
