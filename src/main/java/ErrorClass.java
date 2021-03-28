import netscape.javascript.JSObject;
import org.json.simple.JSONObject;

public class ErrorClass {
    public static JSONObject exceptionJson(Exception e) {
        JSONObject exceptionJsonObject = new JSONObject();
        exceptionJsonObject.put("type", "error");
        System.out.println(e);
        exceptionJsonObject.put("message", e.getMessage());
        return exceptionJsonObject;
    }
}
