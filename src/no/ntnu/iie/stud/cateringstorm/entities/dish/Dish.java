package no.ntnu.iie.stud.cateringstorm.entities.dish;


/**
 * Java representation of database entity "dish".
 */
public class Dish {
    private final int dishId;
    private int dishType;
    private String name;
    private String description;
    private boolean active;

    /**
     * Constructs and initializes a Dish with the specified details.
     *
     * @param dishId      The ID of the dish
     * @param name        The name of the dish
     * @param description The description of the dish
     * @param dishType    The type ID of this dish
     * @param active      Whether the dish is active or inactive
     */
    public Dish(int dishId, String name, String description, int dishType, boolean active) {
        this.dishId = dishId;
        this.dishType = dishType;
        this.name = name;
        this.description = description;
        this.active = active;
    }

    /**
     * Returns a string representation of a dish type id.
     *
     * @return a string representation of a dish type id.
     */
    public String dishTypeText() {
        if (dishType == 1) {
            return "Appetiser";
        } else if (dishType == 2) {
            return "Main course";
        } else if (dishType == 3) {
            return "Dessert";
        } else {
            throw new IndexOutOfBoundsException("Dish type " + dishType + " not defined");
        }
    }

    /**
     * Returns the id of this dish.
     *
     * @return int The id of this dish.
     */
    public int getDishId() {
        return dishId;
    }

    /**
     * Returns the type id of this dish.
     *
     * @return int The type id of this dish.
     */
    public int getDishType() {
        return dishType;
    }

    /**
     * Sets the type id of this dish.
     *
     * @param dishType The type id of this dish.
     */
    public void setDishType(int dishType) {
        this.dishType = dishType;
    }

    /**
     * Returns the name of this dish.
     *
     * @return String The name of this dish.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this dish.
     *
     * @param newName The new name for this dish.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Returns the description of this dish.
     *
     * @return String Description of this dish.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this dish.
     *
     * @param newDescription The new name for this dish.
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Determines whether the dish is active. An inactive dish is practically considered a deleted one.
     *
     * @return true if the dish is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Activates or inactivates this dish. An inactivated dish is practically considered a deleted one.
     *
     * @param newActive If true, this dish is active, otherwise this dish is inactive.
     */
    public void setActive(Boolean newActive) {
        this.active = newActive;
    }

    /**
     * Returns a string representation of this dish.
     *
     * @return a string representation of this dish.
     */
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
