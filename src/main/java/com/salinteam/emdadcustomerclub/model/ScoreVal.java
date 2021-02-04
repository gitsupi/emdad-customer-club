package com.salinteam.emdadcustomerclub.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Created by Abolfazl Ghahremani(Joobin)  on 2/3/2021 , 4:10 PM.
 */
@Entity
@Table(name = "scorevalue")
@Getter
@Setter
@Accessors(chain = true)
public class ScoreVal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long value;
}
