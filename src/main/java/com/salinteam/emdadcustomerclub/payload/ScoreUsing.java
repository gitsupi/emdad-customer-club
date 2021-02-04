package com.salinteam.emdadcustomerclub.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 2/3/2021 , 4:10 PM.
 */
@Getter
@Setter
@Accessors(chain = true)
public class ScoreUsing {
    private String userid;
    private Long value;
}
