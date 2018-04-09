package io.github.javathought.devoxx.model;

import org.glassfish.jersey.internal.util.Base64;

/**
 * Credentials used by user to login on basic authentication
 */
public class Credentials {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String encode() {
        return Base64.encodeAsString(username + ":" + password);
    }
}
