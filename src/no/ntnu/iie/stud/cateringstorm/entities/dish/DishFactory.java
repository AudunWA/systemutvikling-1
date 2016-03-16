package no.ntnu.iie.stud.cateringstorm.entities.dish;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Chris on 16.03.2016.
 */
public final class DishFactory {

    private static Dish createDishFromResultSet(ResultSet result) throws SQLException{
        int dishId = result.getInt("dish_id");
        String name = result.getString("name");
        String desc = result.getString("description");
        int type = result.getInt("dish_type_id");
        boolean active = result.getBoolean("active");

        return new Dish(dishId, name, desc, type, active);
    }

    public static Dish viewDish(int dishId){
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
}
