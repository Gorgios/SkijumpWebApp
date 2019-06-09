package org.skijumping.skijumping.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Competition  {
    private int id;
    private Hill hill;
    private Date dateof;
    private Set<Tournee> tournees = new HashSet<>();
    private List<Start> starts;

    public Competition (){

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
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="id_hill")
    public Hill getHill() {
        return hill;
    }

    public void setHill(Hill hill) {
        this.hill = hill;
    }
    @NotNull
    @Basic
    @Column(name = "dateof")
    public Date getDateof() {
        return dateof;
    }

    public void setDateof(Date dateof) {
        this.dateof = dateof;
    }
    @NotNull
    @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST })
    @JoinTable(
            name = "competition_tournee",
            joinColumns = { @JoinColumn(name = "id_competition") },
            inverseJoinColumns = { @JoinColumn(name = "id_tournee") }
    )
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

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", hill=" + hill +
                ", dateof=" + dateof +
                ", tournees=" + tournees +
                ", starts=" + starts +
                '}';
    }
}
