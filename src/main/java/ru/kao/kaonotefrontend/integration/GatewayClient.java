package ru.kao.kaonotefrontend.integration;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kao.kaonotefrontend.service.AuthService;
import ru.kao.kaonotefrontend.util.LoggerUtil;

import java.util.UUID;

@Component
public class GatewayClient {
    private final Logger logger = LoggerUtil.getLogger(GatewayClient.class);
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
        logger.debug("Post message to gateway. \nMessage = {}. HttpHeaders = {}", request.getBody(), request.getHeaders());
        ResponseEntity<String> restDTOResponseEntity = delegate.postForEntity(RESOURCE_URL + "/http", request, String.class, MediaType.APPLICATION_JSON);
        logger.debug("Received message from gateway. \nMessage = {}. HttpHeaders = {}", restDTOResponseEntity.getBody(), restDTOResponseEntity.getHeaders());
        return new JSONObject(restDTOResponseEntity.getBody());
    }
}