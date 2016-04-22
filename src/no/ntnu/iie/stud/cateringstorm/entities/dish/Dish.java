package no.ntnu.iie.stud.cateringstorm.entities.dish;


/**
 * Created by Audun on 11.03.2016.
 * Class for entity dish in MySQL
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

    /**
     * Method is intended to produce a user friendly value
     * @return String
     */
    public String dishTypeText(){
        if (getDishType() == 1){
            return "Appetiser";
        } else if (getDishType() == 2){
            return "Main course";
        } else if (getDishType() == 3){
            return "Dessert";
        } else {
            return "error";
        }
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

    public void setName(String newName){
        this.name = newName;
    }

    public void setDishType(int dishType) {
        this.dishType = dishType;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    public void setActive(Boolean newActive){
        this.active = newActive;
    }
}
