package com.salintream.emdadcustomerclub.model;

import com.salintream.emdadcustomerclub.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Entity
@Table(name = "company", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        })
})
@Getter
@Setter
public class Company extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 40)
    private String name;


    @NotBlank
    @Size(max = 15)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    public Company(Long id) {
        this.id = id;
    }
    public Company() {

    }

    public Company(@Size(max = 40) String name, @NotBlank @Size(max = 15) String username, @NotBlank @Size(max = 100) String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
}