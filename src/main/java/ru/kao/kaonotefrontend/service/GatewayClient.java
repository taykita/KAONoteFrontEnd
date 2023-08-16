package ru.kao.kaonotefrontend.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kao.kaonotefrontend.util.RestUtil;

import java.io.Serializable;
import java.util.Queue;

@Service
public class GatewayClient {
    private final RestTemplate delegate;
    private final String RESOURCE_URL;

    public GatewayClient(@Value("${ru.kao.note.front.gateway.url}") String resourceUrl) {
        delegate = new RestTemplate();
        this.RESOURCE_URL = resourceUrl;
    }

    public JSONObject executeHttpSend(JSONObject body) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);

        ResponseEntity<String> restDTOResponseEntity = delegate.postForEntity(RESOURCE_URL + "/http", request, String.class, MediaType.APPLICATION_JSON);
        return new JSONObject(restDTOResponseEntity.getBody());
    }
}
