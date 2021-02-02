package com.salinteam.emdadcustomerclub.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class GLRequest {

    @NotNull
    private String levelName;

    @NotNull
    private Long score;

}
