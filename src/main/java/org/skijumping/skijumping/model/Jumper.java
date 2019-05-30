package org.skijumping.skijumping.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Jumper {
    private int id;
    private String firstName;
    private String lastName;
    private Team team;
    private User user;
    private JumperSkills jumperSkills;
    private Date birthDate;
    private List<Clasification> clasifications;
    private List<Start> starts;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="id_team")
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="id_user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="id_skills")
    public JumperSkills getJumperSkills() {
        return jumperSkills;
    }

    public void setJumperSkills(JumperSkills jumperSkills) {
        this.jumperSkills = jumperSkills;
    }

    @Basic
    @Column(name = "birth_date")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @OneToMany(mappedBy = "jumper",cascade = CascadeType.ALL)
    public List<Clasification> getClasifications() {
        return clasifications;
    }

    public void setClasifications(List<Clasification> clasifications) {
        this.clasifications = clasifications;
    }
    @OneToMany(mappedBy = "jumper", cascade = CascadeType.ALL)
    public List<Start> getStarts() {
        return starts;
    }

    public void setStarts(List<Start> starts) {
        this.starts = starts;
    }

}
