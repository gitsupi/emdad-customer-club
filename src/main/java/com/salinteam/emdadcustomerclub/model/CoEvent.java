package com.salinteam.emdadcustomerclub.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salinteam.emdadcustomerclub.model.audit.DateAudit;
import lombok.Data;
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
                "event_id","co_id","grouplevel_id"
        })
})
@Data
@Accessors(chain = true)
public class CoEvent  extends DateAudit {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
//    @Column(name = "co_id")
//    private Long coId;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "co_id", nullable = false)
    private Company company;


    @NotBlank
    @Size(max = 50)
    @Column(name = "event_id")
    private String eventId;


    @Size(max = 40)
    private String name;


    private int scoreValue;


    @ManyToOne
    @JoinColumn(name="grouplevel_id", nullable=false)
    private GroupLevel groupLevel;



    public CoEvent() {

    }
}
