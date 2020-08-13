package com.dunzo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The StockManager class manages stock available in the coffee machine. It handles operations like
 * validating the ingredients for a beverage, validating and updating the stock.
 */
public class StockManager {
    Map<String,Ingredient> stockMap = new HashMap<>();

    public StockManager(List<Ingredient> availableStock) {
        this.stockMap = getStockMap(availableStock);
    }

    /**
     * This method is used to first check whether stock is sufficient to make the beverage and then,
     * update the stock to reflect the ingredients used in making that beverage.
     * This method can be called in multiple parallel threads and uses synchronized keyword to handle
     * race conditions.
     * @param beverage Beverage to order.
     * @exception CoffeeMachineException
     */
    public synchronized void checkAndUpdateStock(Beverage beverage) throws CoffeeMachineException {
        validateStockAvailability(beverage);
        List<Ingredient> ingredientsRequired = beverage.getIngredients();
        ingredientsRequired.forEach(ingredientRequired -> {
            Ingredient ingredientInStock = stockMap.get(ingredientRequired.getName());
            int updatedQty = ingredientInStock.getQty() - ingredientRequired.getQty();
            ingredientInStock.setQty(updatedQty);
        });
    }

    private void validateStockAvailability(Beverage beverage) throws CoffeeMachineException {
        List<Ingredient> ingredients = beverage.getIngredients();
        for (Ingredient ingredient : ingredients) {
            validateIngredientAvailability(ingredient);
        }
    }

    private void validateIngredientAvailability(Ingredient ingredient) throws CoffeeMachineException {
        if(stockMap.containsKey(ingredient.getName())){
            Ingredient ingredientInStock = stockMap.get(ingredient.getName());
            if(ingredient.getQty() <= ingredientInStock.getQty()){
                return;
            }
        }
        throw new CoffeeMachineException(ingredient.getName()+" is not found");
    }

    private Map<String, Ingredient> getStockMap(List<Ingredient> availableStock){
        availableStock.forEach(ingredient -> {
            stockMap.put(ingredient.getName(), ingredient);
        });
        return stockMap;
    }
}
