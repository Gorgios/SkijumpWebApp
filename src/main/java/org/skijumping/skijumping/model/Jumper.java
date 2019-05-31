package org.skijumping.skijumping.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Entity
public class Jumper {
    private int id;
    private String firstName;
    private String lastName;
    private Team team;
    private User user;
    private Date birthDate;
    private List<Clasification> clasifications;
    private List<Start> starts;
    private int takeOf;
    private int landing;
    private int technique;

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
    @Column(name = "take_of")
    public int getTakeOf() {
        return takeOf;
    }

    public void setTakeOf(int takeOf) {
        this.takeOf = takeOf;
    }
    @Basic
    @Column(name = "landing")
    public int getLanding() {
        return landing;
    }

    public void setLanding(int landing) {
        this.landing = landing;
    }
    @Basic
    @Column(name = "technique")
    public int getTechnique() {
        return technique;
    }

    public void setTechnique(int technique) {
        this.technique = technique;
    }

    public void setStarts(List<Start> starts) {
        this.starts = starts;
    }

    public Start makeStart(Competition competition){
        double firstjump,secondjump;
        LinkedList m = new LinkedList();
        Random generator = new Random();
        int luck=generator.nextInt(10) -5;
        firstjump = competition.getHill().getkPoint()-20 + (takeOf *5 + technique *3 + landing *2)/5 + luck;
        luck=generator.nextInt(10) -5;
        secondjump = competition.getHill().getkPoint()-20 + (takeOf *5 + technique *3 + landing *2)/5 + luck;
        Start start = new Start(this,competition,(int)firstjump,(int)secondjump);
        return start;
    }
}
