package org.skijumping.skijumping.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    private int takeOf=0;
    private int landing=0;

    private int technique=0;
    private int credits=0;
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
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @NotNull
    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @NotNull
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
    @Min(value = 0, message = "Musi byc większe lub równe zero")
    @Max(value = 20 , message = "Nie może być większe od 20")
    public int getTakeOf() {
        return takeOf;
    }

    public void setTakeOf(int takeOf) {
        this.takeOf = takeOf;
    }
    @Basic
    @Column(name = "landing")
    @Min(value = 0, message = "Musi byc większe lub równe zero")
    @Max(value = 20 , message = "Nie może być większe od 20")
    public int getLanding() {
        return landing;
    }

    public void setLanding(int landing) {
        this.landing = landing;
    }
    @Basic
    @Column(name = "technique")
    @Min(value = 0, message = "Musi byc większe lub równe zero")
    @Max(value = 20 , message = "Nie może być większe od 20")
    public int getTechnique() {
        return technique;
    }

    @Column(name="credits")
    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setTechnique(int technique) {
        this.technique = technique;
    }

    public void setStarts(List<Start> starts) {
        this.starts = starts;
    }

    public void makeTraining(){
        if (credits >= 5){
            Random generator = new Random();
            int luck = generator.nextInt(5);
            switch (luck){
                case 0: {
                    if (takeOf<20)
                        takeOf++;
                    break;
                }
                case 2:{
                    if (landing<20)
                        landing ++;
                    break;
                }
                case 3: {
                   if (technique <20)
                        technique++;
                    break;
                }
                default: break;
            }
            credits -= 5;
        }
    }
    public Start makeStart(Competition competition){
        double firstjump,secondjump;
        Random generator = new Random();
        int luck=generator.nextInt(10) -5;
        firstjump = competition.getHill().getkPoint()-20 + (takeOf *4 + technique *3 + landing *3)/5 + luck;
        luck=generator.nextInt(10) -5;
        secondjump = competition.getHill().getkPoint()-20 + (takeOf *4 + technique *3 + landing *3)/5 + luck;
        Start start = new Start(this,competition,(int)firstjump,(int)secondjump);
        return start;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
