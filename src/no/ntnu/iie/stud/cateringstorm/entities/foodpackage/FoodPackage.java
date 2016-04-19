package no.ntnu.iie.stud.cateringstorm.entities.foodpackage;

/**
 * Created by Audun on 16.03.2016.
 */
public class FoodPackage {
    private final int foodPackageId;
    private String name;
    private double cost;
    private boolean active;
    
    public int getFoodPackageId() {
        return foodPackageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public FoodPackage(int foodPackageId, String name, double cost, boolean active) {
        this.foodPackageId = foodPackageId;
        this.name = name;
        this.cost = cost;
        this.active = active;
    }

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
