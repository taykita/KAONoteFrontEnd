package ru.kao.kaonotefrontend.service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.kao.kaonotefrontend.entity.Account;
import ru.kao.kaonotefrontend.integration.GatewayClient;
import ru.kao.kaonotefrontend.util.JSONUtil;
import ru.kao.kaonotefrontend.util.LoggerUtil;

@Service
public class AuthService {
    private final Logger LOGGER = LoggerUtil.getLogger(AuthService.class);

    @Autowired
    public AuthService(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    private final GatewayClient gatewayClient;

    public boolean createAccount(Account account) throws JSONException {
        JSONObject checkEmailResponse = gatewayClient.executeHttpSend(
                JSONUtil.createJSONForGateway("/account/exists/" + account.getEmail(), "GET"));
        boolean emailExists = checkEmailResponse.getBoolean("message");
        if (emailExists) {
            JSONObject createAccountJSON = JSONUtil.createJSONForGateway(
                    "/account" + account.getEmail(), "PUT",
                    new ImmutablePair<>("email", account.getEmail()),
                    new ImmutablePair<>("password", account.getPassword()),
                    new ImmutablePair<>("firstname", account.getFirstName()),
                    new ImmutablePair<>("lastname", account.getLastName()));
            JSONObject responseJSON = gatewayClient.executeHttpSend(createAccountJSON);
            int id = responseJSON.getJSONObject("message").optInt("id");
            if (id > 0)
                return true;
            else
                return false;
        } else {
            return false;
        }
    }
}
