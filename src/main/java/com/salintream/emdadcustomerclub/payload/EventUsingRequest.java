package com.salintream.emdadcustomerclub.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 1/29/2021 , 7:05 PM.
 */


@Data
@Accessors(chain = true)
public class EventUsingRequest {

    @NotBlank
    private String eventId;

    @NotBlank
    private String userId;


}
