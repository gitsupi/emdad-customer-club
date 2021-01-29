package com.salintream.emdadcustomerclub.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/29/21.
 */

@Entity

@Data
@Accessors(chain = true)
public class UserActionLog {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserActionLog() {

    }
}
