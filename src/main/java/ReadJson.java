import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadJson {
    public static JSONObject readTheJson(String inputFile, String outputFile) throws IOException, ParseException {
        try {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        JSONObject readedJsonObject = new JSONObject();
        String readJson = "";
        while (reader.ready()) {
            if (readJson.equals("")) {
                readJson = reader.readLine() + "\n";
            } else {
                readJson = readJson + reader.readLine() + "\n";
            }
        }

        JSONParser parser = new JSONParser();
        readedJsonObject = (JSONObject) parser.parse(readJson);

        return readedJsonObject;
    }
        catch (ParseException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(outputFile, new ErrorClass().exceptionJson(e));
        }
   return null;
    }
}

