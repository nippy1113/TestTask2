import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class TestCreator {
    public static JSONObject createJsonForSearchTesting() {
        JSONObject testObject = new JSONObject();
        JSONArray  testArray = new JSONArray();
        JSONObject testLastName = new JSONObject();
        testLastName.put("lastName", "Romanov");
        testArray.add(testLastName);
        JSONObject productName = new JSONObject();
        productName.put("minTimes", 2);
        productName.put("productName", "beer");
        JSONObject Expenses = new JSONObject();
        Expenses.put("minExpenses", 30);
        Expenses.put("maxExpenses", 80);
        JSONObject badCastomers = new JSONObject();
        badCastomers.put("badCustomers", 1);
        testArray.add(productName);
        testArray.add(Expenses);
        testArray.add(badCastomers);

        testObject.put("criterias", testArray);

        return testObject;
    }

    public static JSONObject createJsonForStatTesting() {
        JSONObject testJeyson = new JSONObject();
        testJeyson.put("startDate", "2020-01-14");
        testJeyson.put("endDate", "2020-01-29");

        return testJeyson;
    }
}