# DunzoCoffeeMachine

## How to run

- Open the project in IntelliJ IDEA
- To run the application, run `CoffeeMachineApplication`
- To run the tests, right click on the project and select "Run All Tests"

## How to test for a different JSON file

Currently, `dunzo.json` file is included in this repository. This name is hardcoded in `CoffeeMachineApplication.java`.
1. Add the new json file in data/ folder
2. Modify these two lines inside `CoffeeMachineApplication.java`

```
        CoffeeMachine coffeeMachine = getCoffeeMachineWithJSONDataFromFile(filePath + "/data/dunzo.json");
        List<String> result = coffeeMachine.orderBeverages(new String[] {"hot_tea","black_tea","hot_coffee"});
```
