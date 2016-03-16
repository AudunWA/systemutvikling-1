package no.ntnu.iie.stud.cateringstorm.entities.dish;

/**
 * Created by Audun on 11.03.2016.
 */
public class Dish {
    private final int dishId;
    private DishType dishType;
    private String name;
    private String description;
    private boolean active;

    public Dish(int dishId, DishType dishType, String name, String description, boolean active) {
        this.dishId = dishId;
        this.dishType = dishType;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishId=" + dishId +
                ", dishType=" + dishType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", active=" + active +
                '}';
    }
}
