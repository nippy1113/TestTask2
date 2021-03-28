import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatOperation {
    public static void stat(JSONObject result, JSONArray resultArray, JSONObject jsonObject, Connection conn, String outputFile) throws SQLException, IOException {
        try {
        result.put("type", "stat");
        result.put("totalDays", createTotalDays(jsonObject));
        result.put("customers", resultArray);

        JSONObject startDate = new JSONObject();
        startDate.put("startDate", jsonObject.get("startDate"));
        JSONObject endDate = new JSONObject();
        endDate.put("endDate", jsonObject.get("endDate"));

        String sql2 = "select c.id, name, lastname, sum(price) from\n" +
                "                    customers as c, purchases as p, products as prod\n" +
                "                    where c.id = p.customerid and prod.id = p.productid \n" +
                "                    and date BETWEEN '" + startDate.get("startDate") + "' and '" + endDate.get("endDate") + "'" +
                "                    group by c.id, name, lastname\n" +
                "                    order by  4 desc";
        Statement statement2 = conn.createStatement();
        ResultSet resultSet2 = statement2.executeQuery(sql2);

        while (resultSet2.next()) {
            String customerName = resultSet2.getString("name");
            String customerLastname = resultSet2.getString("lastname");
            int customerId = resultSet2.getInt("id");
            int totalExpenses = resultSet2.getInt("sum");

            String sql = "select productname, sum(price) from\n" +
                    "           customers as c, purchases as p, products as prod\n" +
                    "           where c.id = p.customerid and prod.id = p.productid  \n" +
                    "           and date BETWEEN '" + startDate.get("startDate") + "' and '" + endDate.get("endDate") + "'" +
                    "           and customerid = " + customerId + "\n" +
                    "           group by name, lastname, productname\n" +
                    "           order by name, 2 desc;";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            JSONObject localJeyson = new JSONObject();
            JSONArray localArray = new JSONArray();
            Map map = new HashMap();
            map.put("name", customerLastname + " " + customerName);
            map.put("purchases", localArray);
            localJeyson.putAll(map);

            resultArray.add(localJeyson);

            while (resultSet.next()) {
                String productName = resultSet.getString("productname");
                int expenses = resultSet.getInt("sum");

                JSONObject zapros = new JSONObject();
                zapros.put("name", productName);
                zapros.put("expenses", expenses);
                localArray.add(zapros);
            }
            localJeyson.put("totalExpenses", totalExpenses);
        }

        String sql3 = "select  sum(price) from\n" +
                "           customers as c, purchases as p, products as prod\n" +
                "           where c.id = p.customerid and prod.id = p.productid  \n" +
                "           and date BETWEEN '" + startDate.get("startDate") + "' and '" + endDate.get("endDate") + "'";
        Statement statement3 = conn.createStatement();
        ResultSet resultSet3 = statement3.executeQuery(sql3);
        resultSet3.next();

        int totalExpensesOffAll = resultSet3.getInt("sum");
        result.put("totalExpenses", totalExpensesOffAll);

        String sql4 = "select sum(price) from\n" +
                "         customers as c, purchases as p, products as prod\n" +
                "         where c.id = p.customerid and prod.id = p.productid \n" +
                "         and date BETWEEN '" + startDate.get("startDate") + "' and '" + endDate.get("endDate") + "'" +
                "         group by c.id, name, lastname";
        Statement statement4 = conn.createStatement();
        ResultSet resultSet4 = statement4.executeQuery(sql4);

        ArrayList<Integer> arrayList = new ArrayList<>();
        while (resultSet4.next()) {
            int max = resultSet4.getInt("sum");
            arrayList.add(max);
        }

        result.put("avgExpenses", calculateAverage(arrayList));
    }
        catch (SQLException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(outputFile, new ErrorClass().exceptionJson(e));
        }
    }

    private static double calculateAverage(List<Integer> marks) {
        Integer sum = 0;
        if(!marks.isEmpty()) {
            for (Integer mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }

    public static int createTotalDays(JSONObject jsonObject) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateString = (String) jsonObject.get("startDate");
        String endDateString = (String) jsonObject.get("endDate");
        LocalDate startDateLocalDate = LocalDate.parse(startDateString, dateFormat);
        LocalDate endDateLocalDate = LocalDate.parse(endDateString, dateFormat);
        Period period = Period.between(startDateLocalDate, endDateLocalDate);
        int days = period.getDays();
        return days;
    }
}
