package com.salintream.emdadcustomerclub.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salintream.emdadcustomerclub.model.audit.DateAudit;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 01/29/21.
 */

@Entity
@Data
@Accessors(chain = true)
public class UserTransactionLog extends DateAudit {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long transId;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int score;

    private Long price;

    public UserTransactionLog(Long transId, User user, int score,Long price) {
        this.transId = transId;
        this.user = user;
        this.score = score;
        this.price = price;
    }



    public UserTransactionLog() {

    }
}
