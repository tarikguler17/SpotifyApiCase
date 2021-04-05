package helper;

import com.google.common.io.Resources;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonHelper {
    public static String jsonToString(String jsonName) throws IOException {
        URL file = Resources.getResource(jsonName);
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject(myJson);
        return json.toString();
    }

    public static String jsonToString(String jsonName, String putObject, String newValue) throws IOException {
        URL file = Resources.getResource(jsonName);
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject(myJson);
        json.put(putObject, newValue);
        return json.toString();
    }

}

