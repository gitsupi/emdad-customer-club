package com.salinteam.emdadcustomerclub.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salinteam.emdadcustomerclub.model.audit.DateAudit;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "co_event_id", nullable = false)
    private CoEvent coEvent;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int score;

    public UserEventLog( CoEvent coEvent, User user, int score) {
        this.coEvent = coEvent;
        this.user = user;
        this.score = score;
    }

    public UserEventLog() {

    }
}
