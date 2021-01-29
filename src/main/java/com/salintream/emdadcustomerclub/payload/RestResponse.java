package com.salintream.emdadcustomerclub.payload;

import lombok.Data;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 1/29/2021 , 8:14 PM.
 */
@Data
public class RestResponse<T> {
    private String status;
    private int code;
    private String message;
    private T result;

}