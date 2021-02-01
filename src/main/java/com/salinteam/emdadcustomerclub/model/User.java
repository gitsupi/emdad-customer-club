package com.salinteam.emdadcustomerclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salinteam.emdadcustomerclub.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
@Accessors(chain = true)
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

    private Integer score;


//    @NotBlank
//    @Size(max = 100)
//    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_company",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Set<Company> companies = new HashSet<>();


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<UserEventLog> eventLogs = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="grouplevel_id", nullable=false)
    private GroupLevel groupLevel;


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<UserTransactionLog> transactionLogs = new ArrayList<>();



    public User() {

    }

    public User(@Size(max = 40) String firstname, @Size(max = 40) String lastname,
                @Size(max = 15) String username,
                @NotBlank @Size(max = 15) String phonenumber,
                String email,
                @NotBlank int score
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.phonenumber = phonenumber;
        this.email = email;
        this.score = score;
    }
}