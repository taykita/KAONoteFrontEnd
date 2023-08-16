package ru.kao.kaonotefrontend.configuration.security;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kao.kaonotefrontend.service.GatewayClient;

import java.util.List;

public class AccountDetailsService implements UserDetailsService {
    public AccountDetailsService(GatewayClient gatewayClient) {
        this.gatewayClient = gatewayClient;
    }

    private final GatewayClient gatewayClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            JSONObject body = new JSONObject();
            body.put("path", "/account/" + username);
            body.put("method", "GET");

            JSONObject gatewayDTO = gatewayClient.executeHttpSend(body);

            JSONObject accountJSON = gatewayDTO.getJSONObject("message");
            return new ImmutableAccountDetails(accountJSON.getString("password").toCharArray(), accountJSON.getString("email"),
                    accountJSON.getBoolean("isExpired"), accountJSON.getBoolean("isLocked"),
                    accountJSON.getBoolean("isCredentialsExpired"), accountJSON.getBoolean("isEnabled"),
                    List.of(new SimpleGrantedAuthority("ROLE_" + "USER")));
        } catch (JSONException e) {
            throw new RuntimeException("JSON creating exception", e);
        }
    }
}
