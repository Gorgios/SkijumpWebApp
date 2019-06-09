package org.skijumping.skijumping.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
public class Hill {
    private int id;
    private String name;
    private String country;
    private int hillSize =140;
    private int kPoint =120 ;
    private List<Competition> competitions;

    public Hill () {

    }

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
    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotNull
    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    @NotNull
    @Basic
    @Column(name = "hill_size")
    public int getHillSize() {
        return hillSize;
    }

    public void setHillSize(int hillSize) {
        this.hillSize = hillSize;
    }
    @NotNull
    @Basic
    @Column(name = "k_point")
    public int getkPoint() {
        return kPoint;
    }

    public void setkPoint(int kPoint) {
        this.kPoint = kPoint;
    }

    @OneToMany(fetch=FetchType.LAZY,
            mappedBy="hill",
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }
}
