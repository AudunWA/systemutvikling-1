package no.ntnu.iie.stud.cateringstorm.entities.ingredient;

import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Created by Chris on 30.03.2016.
 */
public class IngredientFactoryTest {

    @Test
    public void testViewSingleIngredient() throws Exception {
        System.out.println(IngredientFactory.viewSingleIngredient(1));

    }

    @Test
    public void testViewAllIngredient() throws Exception {
        for (Ingredient ingredient : IngredientFactory.viewAllIngredient()){
            System.out.println(ingredient);
        }
    }

    @Test
    public void testCreateIngredient() throws Exception {
        Ingredient test = new Ingredient(0, null, "test", "test for factory", false, new Timestamp(System.currentTimeMillis() - 10*(86400000)));
        IngredientFactory.createIngredient(test);
    }

    @Test
    public void testShowExpired() throws Exception {
        for (Ingredient ingredient : IngredientFactory.showExpired()){
            System.out.println(ingredient);
        }
    }
}