package org.skijumping.skijumping.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Clasification implements Comparable<Clasification> {
    private int id;
    private Jumper jumper;
    private Tournee tournee;
    private Integer points=0;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="id_jumper")
    public Jumper getJumper() {
        return jumper;
    }

    public void setJumper(Jumper jumper) {
        this.jumper = jumper;
    }
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="id_tournee")
    public Tournee getTournee() {
        return tournee;
    }

    public void setTournee(Tournee tournee) {
        this.tournee = tournee;
    }

    @Basic
    @Column(name = "points")
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void makePoints(int i){
       switch (i){
           case 1: points+=100; break;
           case 2: points+=80; break;
           case 3: points+=60; break;
           case 4: points+=50; break;
           case 5: points+=45; break;
           case 6: points+=40; break;
           case 7: points+=35; break;
           case 8: points+=30; break;
           case 9: points+=25; break;
           case 10: points+=20; break;
           default: points= points + 30 - i; break;
       }
    }

    @Override
    public int compareTo(Clasification clasification) {
        if (clasification == null || clasification.getPoints() == null)
            return -1;
        else
            return this.getPoints().compareTo(clasification.getPoints());
    }
}
