package com.salinteam.emdadcustomerclub.model;

import com.salinteam.emdadcustomerclub.model.audit.DateAudit;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "group_level")
@Getter
@Setter
public class GroupLevel extends DateAudit {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String levelName;

//    @NotNull
//    private Long score;
    @NotNull
    private Long minscore;

    @NotNull
    private Long maxscore;


    public GroupLevel(Long grouplevel_id) {
        this.id = grouplevel_id;
    }

    public GroupLevel() {

    }

    @OneToMany(
            mappedBy = "groupLevel",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
//    @Fetch(FetchMode.SELECT)
//    @BatchSize(size = 30)
    private List<User> users;

//    @OneToMany
//    private Set<User> items;


}
