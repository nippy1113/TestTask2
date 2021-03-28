import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class WriteJson {
    public static void writeTheJson(String outputFile, JSONObject result) throws IOException {
        try {
            FileWriter writer = new FileWriter(outputFile);
            writer.write(result.toJSONString());
            writer.flush();
            writer.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(outputFile, new ErrorClass().exceptionJson(e));
        }
        catch (IOException e) {
            System.out.println("Exception happend!");
            new WriteJson().writeTheJson(outputFile, new ErrorClass().exceptionJson(e));
        }
        }
}
