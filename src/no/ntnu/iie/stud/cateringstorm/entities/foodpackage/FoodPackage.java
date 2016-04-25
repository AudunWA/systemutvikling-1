package no.ntnu.iie.stud.cateringstorm.entities.foodpackage;

/**
 * Java representation of database entity "food_package".
 */
public class FoodPackage {
    private final int foodPackageId;
    private String name;
    private double cost;
    private boolean active;

    /**
     * Constructs and initializes a FoodPackage with the specified details.
     * @param foodPackageId The ID of the food package.
     * @param name The name of the food package.
     * @param cost The cost of the food package.
     * @param active Whether the food package is active or inactive.
     */
    public FoodPackage(int foodPackageId, String name, double cost, boolean active) {
        this.foodPackageId = foodPackageId;
        this.name = name;
        this.cost = cost;
        this.active = active;
    }

    /**
     * Returns the id of the food package.
     * @return the id of the food package.
     */
    public int getFoodPackageId() {
        return foodPackageId;
    }

    /**
     * Returns the name of the food package.
     * @return the name of the food package.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the food package.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the cost of the food package.
     * @return the cost of the food package.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the price of the customer.
     * @param cost The new price.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Determines whether the food package is active. An inactive food package is practically considered a deleted one.
     * @return true if the food package is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Activates or inactivates this food package. An inactive food package is practically considered a deleted one.
     * @param active If true, this food package is active, otherwise this food package is inactive.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns a string representation of this food package.
     * @return a string representation of this food package.
     */
    @Override
    public String toString() {
        return "FoodPackage{" +
                "foodPackageId=" + foodPackageId +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", active=" + active +
                '}';
    }
}
