package com.salinteam.emdadcustomerclub.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abolfazl Ghahremani on 02/08/17.
 */

@Getter
@Setter
public class AddGrouplevelRequest {
    @NotBlank
    List<GLRequest> glRequests = new ArrayList<>();
}
