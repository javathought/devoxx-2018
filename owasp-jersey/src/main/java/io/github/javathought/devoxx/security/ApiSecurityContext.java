package io.github.javathought.devoxx.security;

import io.github.javathought.devoxx.model.User;

import java.security.Principal;

public class ApiSecurityContext implements javax.ws.rs.core.SecurityContext {

    private final User user;

    public ApiSecurityContext(User user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String s) {
        return user.isInRole(s);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "custom";
    }
}
