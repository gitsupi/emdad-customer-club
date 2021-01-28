package com.salintream.emdadcustomerclub.model;

import com.salintream.emdadcustomerclub.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/27/21.
 */

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        }),
        @UniqueConstraint(columnNames = {
            "phonenumber"
        })
})
@Getter
@Setter
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
//
//    @NotBlank
//    @Size(max = 100)
//    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_company",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Set<Company> companies = new HashSet<>();

    public User() {

    }

    public User(@Size(max = 40) String firstname, @Size(max = 40) String lastname,
                @Size(max = 15) String username,
                @NotBlank @Size(max = 15) String phonenumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.phonenumber = phonenumber;
    }
}