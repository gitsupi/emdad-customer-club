package com.salinteam.emdadcustomerclub.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 2/3/2021 , 4:17 PM.
 */

@Getter
@Setter
@Accessors(chain = true)
public class ScoreResponse {
    private Long userScore;
    private Long valueScore;
    private String userId;

    public ScoreResponse(Long userScore, Long valueScore, String userId) {
        this.userScore = userScore;
        this.valueScore = valueScore;
        this.userId = userId;
    }
}
