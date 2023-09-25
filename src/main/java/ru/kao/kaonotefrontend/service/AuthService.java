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

import java.util.List;

@Service
public class AuthService {
    private final Logger logger = LoggerUtil.getLogger(AuthService.class);

    @Autowired
    public AuthService(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    private final GatewayClient gatewayClient;

    public boolean createAccount(Account account) throws JSONException {
        logger.debug("Check account exist. Name of the email to be checked = {}", account.getEmail());
        JSONObject checkEmailResponse = gatewayClient.executeHttpSend(
                JSONUtil.createJSONForGateway("/account/exists/" + account.getEmail(), "GET"));
        boolean emailExists = checkEmailResponse.getBoolean("message");
        if (!emailExists) {
            logger.debug("Start of registration user with email {}", account.getEmail());
            JSONObject createAccountJSON = JSONUtil.createJSONForGateway(
                    "/account", "PUT",
                    new ImmutablePair<>("email", account.getEmail()),
                    new ImmutablePair<>("password", new String(account.getPassword())),
                    new ImmutablePair<>("firstName", account.getFirstName()),
                    new ImmutablePair<>("lastName", account.getLastName()),
                    new ImmutablePair<>("isExpired", false),
                    new ImmutablePair<>("isLocked", false),
                    new ImmutablePair<>("isCredentialsExpired", false),
                    new ImmutablePair<>("isEnabled", true));
            JSONObject responseJSON = gatewayClient.executeHttpSend(createAccountJSON);
            int id = responseJSON.getJSONObject("message").optInt("id");
            logger.debug("Receive response from registration request. Message - {}", responseJSON);
            return id > 0;
        } else {
            return false;
        }
    }
}
