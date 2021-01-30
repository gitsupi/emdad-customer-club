package com.salintream.emdadcustomerclub.payload;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 1/29/2021 , 7:05 PM.
 */


@Data
@Accessors(chain = true)
public class TransactionUsingRequest {

    @NotBlank
    private String transId;

    @NotBlank
    private String userId;

    private Long price;


}
