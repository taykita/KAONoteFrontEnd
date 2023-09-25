package ru.kao.kaonotefrontend.util;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import ru.kao.kaonotefrontend.configuration.security.AccountDetailsService;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class JSONUtil {
    private static Logger logger = LoggerUtil.getLogger(JSONUtil.class);

    @SafeVarargs
    public static JSONObject createJSONForGateway(String path, String method, Pair<String, Object>... body)
            throws JSONException {
        return createJSONForGateway(path, method, new LinkedList<>(), body);
    }

    @SafeVarargs
    public static JSONObject createJSONForGateway(String path, String method, List<Pair<String, Object>> headers,
                                                  Pair<String, Object>... body)
            throws JSONException {
        logger.debug("Start creating JSON with: \npath = {}\nmethod={}\nbody = {}", path, method, body);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("path", path);
        jsonObject.put("method", method);
        JSONObject jsonBody = new JSONObject();
        for (Pair<String, Object> pair : body) {
            jsonBody.put(pair.getKey(), pair.getValue());
        }
        if (jsonBody.length() != 0) {
            logger.debug("Message is not null. Filling it");
            jsonObject.put("message", jsonBody.toString());
        }

        JSONObject jsonHeaders = new JSONObject();
        for (Pair<String, Object> pair : headers) {
            jsonHeaders.put(pair.getKey(), pair.getValue());
        }
        jsonHeaders.put("UUID", UUID.randomUUID());
        jsonObject.put("headers", jsonHeaders);

        return jsonObject;
    }
}
