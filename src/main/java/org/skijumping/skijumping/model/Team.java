package org.skijumping.skijumping.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
public class Team {
    private int id;
    private String name;
    private Coach coach;
    private List<Jumper> jumpers;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @NotNull
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @OneToOne(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name="id_coach")
    public Coach getCoach() {
        return coach;
    }
    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    @OneToMany(mappedBy = "team", cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    public List<Jumper> getJumpers() {
        return jumpers;
    }

    public void setJumpers(List<Jumper> jumpers) {
        this.jumpers = jumpers;
    }

    @Override
    public String toString() {
        return name ;
    }
}
