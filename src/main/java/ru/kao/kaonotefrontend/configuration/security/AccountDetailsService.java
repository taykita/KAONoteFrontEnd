package ru.kao.kaonotefrontend.configuration.security;

import org.slf4j.Logger;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kao.kaonotefrontend.integration.GatewayClient;
import ru.kao.kaonotefrontend.util.JSONUtil;
import ru.kao.kaonotefrontend.util.LoggerUtil;

import java.util.List;

public class AccountDetailsService implements UserDetailsService {
    public AccountDetailsService(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }
    private static Logger logger = LoggerUtil.getLogger(AccountDetailsService.class);

    private final GatewayClient gatewayClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            JSONObject body = JSONUtil.createJSONForGateway("/account/" + username, "GET");

            Object message = gatewayClient.send(body).get("message");
            if (message instanceof JSONObject accountJSON) {
                logger.debug("Authorize the user with email = {}", username);
                return new ImmutableAccountDetails(accountJSON.getString("password").toCharArray(), accountJSON.getString("email"),
                        accountJSON.getBoolean("isExpired"), accountJSON.getBoolean("isLocked"),
                        accountJSON.getBoolean("isCredentialsExpired"), accountJSON.getBoolean("isEnabled"),
                        List.of(new SimpleGrantedAuthority("ROLE_" + "USER")));
            } else {
                logger.error("User account - {} doesn't exist", username);
                throw new UsernameNotFoundException("User account doesn't exist");
            }
        } catch (JSONException e) {
            logger.error("JSON creating exception", e);
            throw new UsernameNotFoundException("JSON creating exception", e);
        }
    }
}
