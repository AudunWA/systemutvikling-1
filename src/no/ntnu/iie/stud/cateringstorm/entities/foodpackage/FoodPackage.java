package no.ntnu.iie.stud.cateringstorm.entities.foodpackage;

/**
 * Created by Audun on 16.03.2016.
 */
public class FoodPackage {
    private final int foodPackageId;
    private String name;
    private double cost;
    private boolean active;

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
