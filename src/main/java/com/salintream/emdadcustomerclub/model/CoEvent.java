package com.salintream.emdadcustomerclub.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/29/21.
 */

@Entity

@Table(name = "coevent", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username","co_id"
        })
})
@Data
@Accessors(chain = true)
public class CoEvent {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
//    @Column(name = "co_id")
//    private Long coId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "co_id", nullable = false)
    private Company company;


    @NotBlank
    @Size(max = 15)
    private String username;


    @Size(max = 40)
    private String name;


    private int scoreValue;



    public CoEvent() {

    }
}
