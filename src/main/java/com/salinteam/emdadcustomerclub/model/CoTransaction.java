package com.salinteam.emdadcustomerclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salinteam.emdadcustomerclub.model.audit.DateAudit;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 1/30/2021 , 3:54 PM.
 */

@Entity
@Table(name = "cotransaction", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username", "co_id"
        })
})
@Data
@Accessors(chain = true)
public class CoTransaction extends DateAudit {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "co_id", nullable = false)
    private Company company;


    @NotBlank
    @Size(max = 15)
    private String username;

    @Size(max = 40)
    private String name;

    private Long unitprice;

    //todo
    private Long userLevelId;


    public CoTransaction(Company company, @NotBlank @Size(max = 15) String username, Long unitprice) {
        this.company = company;
        this.username = username;
        this.unitprice = unitprice;
    }

    public CoTransaction() {

    }
}
