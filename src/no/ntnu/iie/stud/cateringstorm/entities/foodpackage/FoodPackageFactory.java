package no.ntnu.iie.stud.cateringstorm.entities.foodpackage;

import no.ntnu.iie.stud.cateringstorm.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Audun on 16.03.2016.
 */
public final class FoodPackageFactory {
    /**
     * Shows a single _package object given the id
     * @param foodPackageId
     * @return FoodPackage if foodPackageID matches
     */
    public static FoodPackage getFoodPackage(int foodPackageId) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `food_package` WHERE `food_package_id` = ?")) {
                statement.setInt(1, foodPackageId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    if (result.next()) {
                        return createFoodPackageFromResultSet(result);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates an arraylist of all the _package objects in the SQL table _package
     * @return arraylist of all the packages
     */
    public static ArrayList<FoodPackage> getAllFoodPackages() {
        ArrayList<FoodPackage> foodPackages = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `food_package`")) {
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        foodPackages.add(createFoodPackageFromResultSet(result));
                    }
                }
            }
            return foodPackages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     Gets all the food packages for a specified order.
     * @param orderId The ID of the order
     * @return A list of all the food packages
     */
    public static ArrayList<FoodPackage> getFoodPackages(int orderId) {
        ArrayList<FoodPackage> foodPackages = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT `food_package`.* FROM `_order_food_package`" +
                    " NATURAL JOIN `food_package`" +
                    " WHERE `_order_id` = ?")) {
                statement.setInt(1, orderId);
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()) {
                    while (result.next()) {
                        foodPackages.add(createFoodPackageFromResultSet(result));
                    }
                }
            }
            return foodPackages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a FoodPackage object from result
     * @param result
     * @return FoodPackage
     * @throws SQLException
     */
    private static FoodPackage createFoodPackageFromResultSet(ResultSet result) throws SQLException {
        int foodPackageId = result.getInt("food_package_id");
        String name = result.getString("name");
        double cost = result.getDouble("cost");
        boolean active = result.getBoolean("active");

        return new FoodPackage(foodPackageId, name, cost, active);
    }
}
