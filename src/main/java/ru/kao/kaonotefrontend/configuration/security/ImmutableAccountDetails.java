package ru.kao.kaonotefrontend.configuration.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ImmutableAccountDetails implements UserDetails {
    public ImmutableAccountDetails(char[] password, String email, boolean isExpired,
                                   boolean isLocked, boolean isCredentialsExpired, boolean isEnabled,
                                   Collection<? extends GrantedAuthority> authorities) {
        this.password = password;
        this.email = email;
        this.isExpired = isExpired;
        this.isLocked = isLocked;
        this.isCredentialsExpired = isCredentialsExpired;
        this.isEnabled = isEnabled;
        this.authorities = authorities;
    }

    private final char[] password;

    private final String email;

    private final boolean isExpired;

    private final boolean isLocked;

    private final boolean isCredentialsExpired;

    private final boolean isEnabled;

    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return new String(password);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
