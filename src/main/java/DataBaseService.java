import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseService {
    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/test database", "postgres", "28961a");
        } catch (SQLException e) {
            System.out.println("wrong sql url!");
            e.printStackTrace();
        }
    }

    public static void createDataBase(String inputFile, String operationType, String outputFile) throws ParseException, SQLException, java.text.ParseException, IOException {
        try {
            JSONObject result = new JSONObject();
            JSONArray resultArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            jsonObject = new ReadJson().readTheJson(inputFile, outputFile);
            if (operationType.equals("search")) {
                new SearchOperation().search(result, resultArray, jsonObject, conn, outputFile);
            }
            if (operationType.equals("stat")) {
                new StatOperation().stat(result, resultArray, jsonObject, conn, outputFile);
            }
            if ((!operationType.equals("stat")) && (!operationType.equals("search"))) {
                throw new Exception();
            }
            new WriteJson().writeTheJson(outputFile, result);
        }

        catch(FileNotFoundException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(outputFile, new ErrorClass().exceptionJson(e));
        }
        catch (ParseException e) {
            System.out.println("Exception happend!9");
            new WriteJson().writeTheJson(outputFile, new ErrorClass().exceptionJson(e));
        } catch(SQLException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(outputFile, new ErrorClass().exceptionJson(e));
        } catch(IOException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(outputFile, new ErrorClass().exceptionJson(e));
        }
        catch (NullPointerException e) {
            System.out.println("Exception happend!");
            JSONObject nullPointerJson = new JSONObject();
            nullPointerJson.put("type", "error");
            nullPointerJson.put("message", "wrong operation type or inputFileData is corruted");
            new WriteJson().writeTheJson(outputFile, nullPointerJson);
        }   catch (Exception e) {
            System.out.println("Exception happend!");
            JSONObject nullPointerJson = new JSONObject();
            nullPointerJson.put("type", "error");
            nullPointerJson.put("message", "wrong operation type");
            new WriteJson().writeTheJson(outputFile, nullPointerJson);
        }
    }
}
