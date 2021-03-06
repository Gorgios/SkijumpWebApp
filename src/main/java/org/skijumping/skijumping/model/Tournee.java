package org.skijumping.skijumping.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tournee {
    private int id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private Set<Competition> competitions = new HashSet<>();
    private List<Clasification> clasifications;
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
    @Column(name = "date_start")
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Basic
    @Column(name = "date_end")
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    @ManyToMany(mappedBy = "tournees")
    public Set<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
    }
    @OneToMany(mappedBy = "tournee",cascade = CascadeType.ALL)
    public List<Clasification> getClasifications() {
        return clasifications;
    }

    public void setClasifications(List<Clasification> clasifications) {
        this.clasifications = clasifications;
    }
}
