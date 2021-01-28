package com.salintream.emdadcustomerclub.payload;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * Created by rajeevkumarsingh on 02/08/17.
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
    private String phonenumber;

    @NaturalId
    @Size(max = 40)
    @Email
    private String email;
}
