package no.ntnu.iie.stud.cateringstorm.entities.foodpackage;

import no.ntnu.iie.stud.cateringstorm.database.Database;
import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;

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
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM food_package")) {
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
     * Gets all foodpackages with name matching a query.
     * @return An ArrayList containing all foodpackages matched.
     */
    public static ArrayList<FoodPackage> getAllFoodPackagesByQuery(String searchQuery) {
        ArrayList<FoodPackage> foodPackages = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM food_package WHERE name LIKE ?")){
                statement.setString(1, '%' + searchQuery + '%');
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()) {
                        foodPackages.add(createFoodPackageFromResultSet(result));
                    }

                }
                return foodPackages;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
    /**
     * Gets all active foodpackes with name matching a query.
     * @return An ArrayList containing all foodpackages matched.
     */
    public static ArrayList<FoodPackage> getActiveFoodPackagesByQuery(String searchQuery) {
        ArrayList<FoodPackage> foodPackages = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM food_package WHERE name LIKE ? AND active = TRUE")){
                statement.setString(1, '%' + searchQuery + '%');
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()) {
                        foodPackages.add(createFoodPackageFromResultSet(result));
                    }

                }
                return foodPackages;

            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
    /**
     * Creates an arraylist of the active foodpackages in the SQL table foodpackages
     * @return ArrayList<FoodPackage>
     */
    public static ArrayList<FoodPackage> getActiveFoodPackages(){
        ArrayList<FoodPackage> foodPackages = new ArrayList<>();
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM food_package WHERE active = TRUE")){
                statement.executeQuery();

                try (ResultSet result = statement.getResultSet()){
                    while (result.next()) {

                        foodPackages.add(createFoodPackageFromResultSet(result));
                    }

                }
                return foodPackages;

            }
        } catch (SQLException e){
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
     Inserts a new food package, including its dishes, into the database.
     * @param name The food package name
     * @param cost The cost of the food package
     * @param dishes A list of the dishes
     * @return A FoodPackage object representing the new food package, or null if an error occurred.
     */
    public static FoodPackage createFoodPackage(String name, double cost, ArrayList<Dish> dishes) {
        if (dishes == null) {
            throw new IllegalArgumentException("dishes cannot be null.");
        }
        int generatedId; // The AUTO_INCREMENT food_package_id from INSERT

        try (Connection connection = Database.getConnection()) {
            // Start transaction
            connection.setAutoCommit(false);

            try {
                // Add the package itself and get ID
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO food_package VALUES(DEFAULT, ?, ?, TRUE)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                    statement.setString(1, name);
                    statement.setDouble(2, cost);

                    int affectedRows = statement.executeUpdate();
                    if (affectedRows == 0) {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return null; // No rows inserted
                    }

                    try (ResultSet result = statement.getGeneratedKeys()) {
                        if (result.next()) {
                            generatedId = result.getInt(1);
                        } else {
                            connection.rollback();
                            connection.setAutoCommit(true);
                            return null; // No ID?
                        }
                    }
                }

                // Add the dishes to the package
                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO dish_food_package VALUES(?, ?)")) {
                    for (Dish dish : dishes) {
                        statement.setInt(1, dish.getDishId());
                        statement.setInt(2, generatedId);
                        statement.addBatch();
                    }
                    statement.executeBatch();
                }
            } catch (SQLException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw e;
            }

            // All good, commit all
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        // Create object and return
        FoodPackage foodPackage = new FoodPackage(generatedId, name, cost, true);
        return foodPackage;
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
    /**
     Runs an UPDATE-query of a foodpackage, with all its columns.
     * @param foodPackage The foodpackage to update
     * @return An integer representing affected rows
     */
    public static int updateFoodPackage(FoodPackage foodPackage) {
        try (Connection connection = Database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE food_package SET name = ?, cost = ?, active = ? WHERE food_package_id = ?")) {
                statement.setString(1, foodPackage.getName());
                statement.setDouble(2, foodPackage.getCost());
                statement.setBoolean(3, foodPackage.isActive());
                statement.setInt(4, foodPackage.getFoodPackageId());

                return statement.executeUpdate();
            }
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}
