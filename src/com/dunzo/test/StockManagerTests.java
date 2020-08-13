package com.dunzo.test;

import com.dunzo.Beverage;
import com.dunzo.CoffeeMachineException;
import com.dunzo.Ingredient;
import com.dunzo.StockManager;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StockManagerTests {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void checkAndUpdateStock_Test_Should_Throw_Exception_Ingredient_Not_Available() throws CoffeeMachineException {
        List<Ingredient> availableStock = getAvailableStock();
        Beverage beverage = getBeverage();
        availableStock.remove(0); //ingredient not in stock

        StockManager stockManager = new StockManager(availableStock);
        exception.expect(CoffeeMachineException.class);
        stockManager.checkAndUpdateStock(beverage);
    }

    @Test
    public void checkAndUpdateStock_Test_Should_Throw_Exception_Ingredient_Insufficient_Quantity() throws CoffeeMachineException {
        List<Ingredient> availableStock = getAvailableStock();
        Beverage beverage = getBeverage();
        availableStock.get(0).setQty(1); //ingredient qty insufficient

        StockManager stockManager = new StockManager(availableStock);
        exception.expect(CoffeeMachineException.class);
        stockManager.checkAndUpdateStock(beverage);
    }

    @Test
    public void checkAndUpdateStock_Test_Should_Update_Quantity() throws CoffeeMachineException {

        List<Ingredient> availableStock = getAvailableStock();
        Beverage beverage = getBeverage();

        StockManager stockManager = new StockManager(availableStock);
        stockManager.checkAndUpdateStock(beverage);

        availableStock.forEach(ingredient -> {
            String name = ingredient.getName();
            int qty = ingredient.getQty();
            switch (name){
                case "Milk" :
                case "Water" :
                    assertEquals(5, qty);
                    break;
                case "Sugar" :
                case "Tea Leaves" :
                    assertEquals(2, qty);
                    break;
            }
        });
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
    private Beverage getBeverage(){
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
        return beverage;
    }
}
