package utilities;

import org.json.JSONObject;

public class TestUtil {

    public static boolean jsonHasKey(String json, String key) {
	JSONObject objJSONObject = new JSONObject(json);
	return objJSONObject.has(key);
    }

    public static String getJSONKeyValue(String json, String key) {
	JSONObject objJSONObject = new JSONObject(json);
	return objJSONObject.get(key).toString();
    }

}
