package com.salintream.emdadcustomerclub.payload;

import lombok.Data;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 27/01/21.
 */
@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";


    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }



}
