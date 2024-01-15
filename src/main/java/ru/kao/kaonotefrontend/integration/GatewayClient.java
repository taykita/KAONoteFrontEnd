package ru.kao.kaonotefrontend.integration;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public interface GatewayClient {
    JSONObject send(JSONObject body) throws JSONException;
}
