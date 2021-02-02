package com.salinteam.emdadcustomerclub.model;

import com.salinteam.emdadcustomerclub.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

    @ManyToMany
    @JoinTable(
            name = "company_role",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();


    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<CoEvent> coEvents = new ArrayList<>();


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