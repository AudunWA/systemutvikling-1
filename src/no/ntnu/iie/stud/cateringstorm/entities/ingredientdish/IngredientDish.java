package no.ntnu.iie.stud.cateringstorm.entities.ingredientdish;

import no.ntnu.iie.stud.cateringstorm.entities.dish.Dish;
import no.ntnu.iie.stud.cateringstorm.entities.ingredient.Ingredient;

/**
 * Joint class of MySQL entities ingredient and dish, containing a quantity and a unit.
 */
public class IngredientDish {
    private final Ingredient ingredient;
    private final Dish dish;
    private final String unit;
    private double quantity;

    public IngredientDish(Ingredient ingredient, Dish dish, double quantity, String unit) {
        this.ingredient = ingredient;
        this.dish = dish;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Dish getDish() {
        return dish;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return "IngredientDish{" +
                "ingredientId=" + ingredient +
                ", dishId=" + dish +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
