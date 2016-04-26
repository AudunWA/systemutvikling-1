package no.ntnu.iie.stud.cateringstorm.entities.ingredient;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Java representation of database entity "ingredient".
 */
public class Ingredient {
    private final int ingredientId;
    private String name;
    private String description;
    private boolean vegetarian;
    private Timestamp arrivalDate;
    private Date expireDate;
    private double amount;
    private String unit;

    public Ingredient(int ingredientId, Timestamp arrivalDate, String name, String description, boolean vegetarian, Date expireDate, double amount, String unit) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.description = description;
        this.vegetarian = vegetarian;
        this.arrivalDate = arrivalDate;
        this.expireDate = expireDate;
        this.amount = amount;
        this.unit = unit;
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void incrementAmount() {
        this.amount++;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", vegetarian=" + vegetarian +
                ", arrivalDate=" + arrivalDate +
                ", expireDate=" + expireDate +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                '}';
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }
}
