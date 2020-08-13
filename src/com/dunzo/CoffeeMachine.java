package com.dunzo;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * The CoffeeMachine class manages all the coffee machine related operations like
 * validating the beverages,ordering the beverages.
 */
public class CoffeeMachine {
    int outlets;
    Map<String,Beverage> beverageMap = new HashMap<>();
    StockManager stockManager;

    /**
     * @param outlets represents outlets from which beverages can be served at the same time.
     * @param availableStock represents the list of ingredients available
     * @param beverages represents the list of beverages that can be made by this coffee machine
     */
    public CoffeeMachine(int outlets, List<Ingredient> availableStock, List<Beverage> beverages){
        this.outlets = outlets;
        this.beverageMap = getBeveragesMap(beverages);
        this.stockManager = new StockManager(availableStock);
    }

    public int getOutlets() {
        return outlets;
    }

    /**
     * This method is used to order beverages from the coffee machine in parallel at the same time.
     * @param orderedBeverageNamesArray is a String array for the beverages names to order.
     * @exception CoffeeMachineException
     */
    public List<String> orderBeverages(String... orderedBeverageNamesArray) throws CoffeeMachineException {
        List<String> orderedBeverageNames = new ArrayList<>(Arrays.asList(orderedBeverageNamesArray));
        if(orderedBeverageNames.size() > getOutlets()){
            throw new CoffeeMachineException("Outlets out of bound");
        }

        List<String> result = new ArrayList<>();
        List<CompletableFuture> cfs = orderedBeverageNames.stream().map(beverageName -> {
            //Make each beverage in a parallel thread.
            return CompletableFuture.runAsync(() -> {
                try{
                    validateBeverage(beverageName);
                    Beverage beverage = beverageMap.get(beverageName);
                    stockManager.checkAndUpdateStock(beverage);
                    result.add(beverageName+" is ready to serve");
                } catch (CoffeeMachineException ex){
                    result.add(beverageName+" can't be ordered because "+ex.getMessage());
                }
            });
        }).collect(Collectors.toList());

        //Wait for all threads to complete.
        CompletableFuture[] cfsArray = cfs.stream().toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(cfsArray).join();
        return result;
    }

    private void validateBeverage(String beverageName) throws CoffeeMachineException {
        if(!beverageMap.containsKey(beverageName)){
            throw new CoffeeMachineException(beverageName+" is not found");
        }
    }

    private Map<String, Beverage> getBeveragesMap(List<Beverage> beverages){
        beverages.forEach(beverage -> {
            beverageMap.put(beverage.getName(), beverage);
        });
        return beverageMap;
    }
}
