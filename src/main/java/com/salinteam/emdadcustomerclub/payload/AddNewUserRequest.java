package com.salinteam.emdadcustomerclub.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * Created by Abolfazl Ghahremani on 02/08/17.
 */

@Getter
@Setter
public class AddNewUserRequest {

    @Size(max = 40)
    private String firstname;

    @Size(max = 40)
    private String lastname;

    @Size(max = 15)
    private String username;

    @NotBlank
    @Size(max = 15)
    @ApiModelProperty(required = true)
    private String phonenumber;

    @NaturalId
    @Size(max = 40)
    @Email
    private String email;


}
