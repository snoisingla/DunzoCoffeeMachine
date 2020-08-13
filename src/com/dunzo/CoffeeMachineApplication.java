package com.dunzo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.*;

/**
 * The CoffeeMachineApplication initializes a working coffee machine with the input json file and runs it.
 */
public class CoffeeMachineApplication {

    /**
     * This is the main method which makes use of getCoffeeMachineWithJSONDataFromFile and  orderBeverages methods.
     * @param args Unused.
     * @return Nothing.
     * @exception IOException
     * @exception ParseException
     * @exception CoffeeMachineException
     *
     */
    public static void main(String[] args) throws CoffeeMachineException, IOException, ParseException {
        String filePath = new File("").getAbsolutePath();
        CoffeeMachine coffeeMachine = getCoffeeMachineWithJSONDataFromFile(filePath + "/data/dunzo.json");
        List<String> result = coffeeMachine.orderBeverages(new String[] {"hot_tea","black_tea","hot_coffee"});
        result.stream().forEach(s -> System.out.println(s));
    }

    private static CoffeeMachine getCoffeeMachineWithJSONDataFromFile(String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        CoffeeMachine coffeeMachine;
        try (Reader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONObject machine = (JSONObject) jsonObject.get("machine");
            JSONObject outletsJson = (JSONObject) machine.get("outlets");
            Long count = (Long) outletsJson.get("count_n");

            Map beverageMapJson = ((Map)machine.get("beverages"));
            Iterator<Map.Entry> beverageItr = beverageMapJson.entrySet().iterator();
            List<Beverage> beverages = new ArrayList<>();
            while (beverageItr.hasNext()) {
                Map.Entry mapEntry1 = beverageItr.next();
                String beverageName = (String) mapEntry1.getKey();
                Map<String, Long> ingredientsMap = (Map<String, Long>) mapEntry1.getValue();
                List<Ingredient> ingredientsList = new ArrayList<>();
                for (Map.Entry<String, Long> mapEntry2 : ingredientsMap.entrySet()) {
                    Ingredient ingredient = new Ingredient(mapEntry2.getKey(), mapEntry2.getValue().intValue());
                    ingredientsList.add(ingredient);
                }
                Beverage beverage = new Beverage(beverageName, ingredientsList);
                beverages.add(beverage);
            }

            Map stockMapJson = ((Map)machine.get("total_items_quantity"));
            Iterator<Map.Entry> stockIterator = stockMapJson.entrySet().iterator();
            List<Ingredient> ingredients = new ArrayList<>();
            while (stockIterator.hasNext()){
                Map.Entry mapEntry1 = stockIterator.next();
                String ingredientName = (String) mapEntry1.getKey();
                Long ingredientQty = (Long) mapEntry1.getValue();
                Ingredient ingredient = new Ingredient(ingredientName, Math.toIntExact(ingredientQty));
                ingredients.add(ingredient);
            }
            coffeeMachine = new CoffeeMachine(count.intValue(),ingredients, beverages);
        }
        return coffeeMachine;
    }
}
