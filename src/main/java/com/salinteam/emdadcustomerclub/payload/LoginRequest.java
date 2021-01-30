package com.salinteam.emdadcustomerclub.payload;

import javax.validation.constraints.NotBlank;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */
public class LoginRequest {
    @NotBlank
    private String username;

    @NotBlank
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
}
