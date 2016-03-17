package no.ntnu.iie.stud.cateringstorm.entities.dish;

/**
 * Created by Audun on 11.03.2016.
 */
public class Dish {
    private final int dishId;
    private int dishType;
    private String name;
    private String description;
    private boolean active;

    public Dish(int dishId, String name, String description, int dishType, boolean active) {
        this.dishId = dishId;
        this.dishType = dishType;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    public int getDishId() {
        return dishId;
    }

    public int getDishType() {
        return dishType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
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
