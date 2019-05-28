package org.skijumping.skijumping.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Competition {
    private int id;
    private Hill hill;
    private Date dateof;
    private Set<Tournee> tournees = new HashSet<>();
    private List<Start> starts;

    public Competition (){

    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="id_hill")
    public Hill getHill() {
        return hill;
    }

    public void setHill(Hill hill) {
        this.hill = hill;
    }

    @Basic
    @Column(name = "dateof")
    public Date getDateof() {
        return dateof;
    }

    public void setDateof(Date dateof) {
        this.dateof = dateof;
    }
    @ManyToMany(mappedBy = "competitions")
    public Set<Tournee> getTournees() {
        return tournees;
    }

    public void setTournees(Set<Tournee> tournees) {
        this.tournees = tournees;
    }
    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    public List<Start> getStarts() {
        return starts;
    }

    public void setStarts(List<Start> starts) {
        this.starts = starts;
    }
}
