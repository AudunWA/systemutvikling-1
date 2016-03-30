package no.ntnu.iie.stud.cateringstorm.entities.ingredient;

import java.sql.Timestamp;

/**
 * Created by Audun on 11.03.2016.
 */
public class Ingredient {
    private final int ingredientId;
    private String name;
    private String description;
    private boolean vegetarian;
    private Timestamp arrivalDate;

    public Ingredient(int ingredientId, String name, String description, boolean vegetarian, Timestamp arrivalDate) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.description = description;
        this.vegetarian = vegetarian;
        this.arrivalDate = arrivalDate;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public Timestamp getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", vegetarian=" + vegetarian +
                ", arrivalDate=" + arrivalDate +
                '}';
    }
}
