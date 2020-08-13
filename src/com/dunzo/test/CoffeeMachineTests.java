package com.dunzo.test;

import com.dunzo.Beverage;
import com.dunzo.CoffeeMachine;
import com.dunzo.CoffeeMachineException;
import com.dunzo.Ingredient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMachineTests {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getBeverages_Test_Should_Throw_Exception_Outlets_OutOfBound() throws CoffeeMachineException {
        List<Ingredient> availableStock = getAvailableStock();
        List<Beverage> beverages = getBeverages();
        exception.expect(CoffeeMachineException.class);
        CoffeeMachine coffeeMachine = new CoffeeMachine(1, availableStock, beverages);
        coffeeMachine.orderBeverages(new String[]{"Hot Tea", "Hot Milk"});
    }

    //dummy data
    private List<Ingredient> getAvailableStock(){
        List<Ingredient> availableStock = new ArrayList<>();
        Ingredient i1 = new Ingredient("Milk", 10);
        Ingredient i2 = new Ingredient("Water", 10);
        Ingredient i3 = new Ingredient("Sugar", 5);
        Ingredient i4 = new Ingredient("Tea Leaves", 5);
        availableStock.add(i1);
        availableStock.add(i2);
        availableStock.add(i3);
        availableStock.add(i4);
        return availableStock;
    }

    //dummy data
    private List<Beverage> getBeverages(){
        List<Beverage> beverages = new ArrayList<>();

        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient i11 = new Ingredient("Milk", 5);
        Ingredient i12 = new Ingredient("Water", 5);
        Ingredient i13 = new Ingredient("Sugar", 3);
        Ingredient i14 = new Ingredient("Tea Leaves", 3);
        ingredients.add(i11);
        ingredients.add(i12);
        ingredients.add(i13);
        ingredients.add(i14);
        Beverage beverage = new Beverage("Hot Tea", ingredients);

        List<Ingredient> ingredients1 = new ArrayList<>();
        Ingredient i111 = new Ingredient("Milk", 3);
        Ingredient i112 = new Ingredient("Water", 3);
        Ingredient i113 = new Ingredient("Sugar", 1);
        Ingredient i114 = new Ingredient("Tea Leaves", 0);
        ingredients1.add(i111);
        ingredients1.add(i112);
        ingredients1.add(i113);
        ingredients1.add(i114);
        Beverage beverage1 = new Beverage("Hot Milk", ingredients1);

        beverages.add(beverage);
        beverages.add(beverage1);
        return beverages;
    }
}
