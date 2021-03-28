import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;


public class SearchOperation {
    public static void search(JSONObject result, JSONArray resultArray, JSONObject jsonObject, Connection conn, String outputFile) throws SQLException, IOException {
        try {
        result.put("type", "search");
        result.put("results", resultArray);

        JSONArray jsonArray = (JSONArray) jsonObject.get("criterias");
        Iterator criteriyaIter = jsonArray.iterator();

        while (criteriyaIter.hasNext()) {
            JSONObject criteria = (JSONObject) criteriyaIter.next();
            JSONObject resultObject = new JSONObject();
            JSONArray localArray = new JSONArray();

            resultObject.put("Criteria", criteria);
            resultObject.put("results", localArray);

            if (criteria.containsKey("lastName")) ;
            {
                String sql = "SELECT * FROM customers WHERE lastName ='" + criteria.get("lastName") + "'";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");

                    JSONObject zapros = new JSONObject();
                    zapros.put("lastName", lastName);
                    zapros.put("name", name);
                    localArray.add(zapros);
                }
            }

            if (criteria.containsKey("productName")) {
                String sql = "select\n" +
                        " name, lastname from\n" +
                        " customers as c, purchases as p, products as prod\n" +
                        " where c.id = p.customerid and prod.id = p.productid\n" +
                        " group by name, lastname, prod.id\n" +
                        " HAVING prod.productname = '" + criteria.get("productName") + "' and count(p.id) >=" + criteria.get("minTimes");
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");

                    JSONObject zapros = new JSONObject();
                    zapros.put("lastName", lastName);
                    zapros.put("name", name);
                    localArray.add(zapros);
                }
            }

            if (criteria.containsKey("minExpenses")) {
                String sql = "select name, lastname, sum(price) from\n" +
                        " customers as c, purchases as p, products as prod\n" +
                        " where c.id = p.customerid and prod.id = p.productid \n" +
                        " group by name, lastname\n" +
                        " having sum(price) > " + criteria.get("minExpenses") + "and sum(price) < " + criteria.get("maxExpenses");
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String lastName2 = resultSet.getString("lastName");

                    JSONObject zapros = new JSONObject();
                    zapros.put("lastName", lastName2);
                    zapros.put("name", name);
                    localArray.add(zapros);
                }
            }

            if (criteria.containsKey("badCustomers")) {
                String sql = " select name, lastname from\n" +
                        " customers as c, purchases as p, products as prod\n" +
                        " where c.id = p.customerid and prod.id = p.productid \n" +
                        " group by name, lastname\n" +
                        " order by count(p.id) ASC\n" +
                        " LIMIT " + criteria.get("badCustomers");
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String lastName2 = resultSet.getString("lastName");

                    JSONObject zapros = new JSONObject();
                    zapros.put("lastName", lastName2);
                    zapros.put("name", name);
                    localArray.add(zapros);
                }
            }
            resultArray.add(resultObject);
        }
    }
        catch (SQLException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(outputFile, new ErrorClass().exceptionJson(e));
        }
    }
}
