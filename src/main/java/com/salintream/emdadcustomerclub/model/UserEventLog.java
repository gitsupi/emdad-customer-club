package com.salintream.emdadcustomerclub.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salintream.emdadcustomerclub.model.audit.DateAudit;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/29/21.
 */

@Entity
@Data
@Accessors(chain = true)
public class UserEventLog extends DateAudit {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long eventId;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int score;

    public UserEventLog(Long eventId, User user, int score) {
        this.eventId = eventId;

        this.user = user;
        this.score = score;
    }

    public UserEventLog() {

    }
}
