package ru.kao.kaonotefrontend.util;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class JSONUtil {
    public static JSONObject createJSONForGateway(String path, String method,
                                                  Pair<String, Object>... body) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path", path);
        jsonObject.put("method", method);
        JSONObject jsonBody = new JSONObject();
        for (Pair<String, Object> pair : body) {
            jsonBody.put(pair.getKey(), pair.getValue());
        }
        jsonObject.put("message", jsonBody);
        return jsonObject;
    }
}
