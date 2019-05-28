package org.skijumping.skijumping.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "jumper_skills", schema = "mydb", catalog = "")
public class JumperSkills {
    private int id;
    private int speed;
    private int technique;
    private int landing;
    private int takeOff;
    private Jumper jumper;

    public JumperSkills(){
    }

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "speed")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Basic
    @Column(name = "technique")
    public int getTechnique() {
        return technique;
    }

    public void setTechnique(int technique) {
        this.technique = technique;
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
    @Column(name = "take_off")
    public int getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(int takeOff) {
        this.takeOff = takeOff;
    }

    @OneToOne(mappedBy="jumperSkills", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    public Jumper getJumper() {
        return jumper;
    }

    public void setJumper(Jumper jumper) {
        this.jumper = jumper;
    }
}
