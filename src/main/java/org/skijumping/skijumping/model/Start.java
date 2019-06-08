package org.skijumping.skijumping.model;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@Table(name = "competition_jumper", schema = "mydb", catalog = "")
public class Start implements Comparable<Start>{
    private Jumper jumper;
    private Competition competition;
    private Double points;
    private int id;
    private int first_jump;
    private int second_jump;

    public Start(){

    }
    public Start(Jumper jumper, Competition competition, int first_jump, int second_jump) {
        this.jumper = jumper;
        this.competition = competition;
        this.first_jump = first_jump;
        this.second_jump = second_jump;
        this.points = makePoints(first_jump,second_jump);
    }

    @Basic
    @Column(name = "points")
    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    @Column(name="first_jump")
    public int getFirst_jump() {
        return first_jump;
    }

    public void setFirst_jump(int first_jump) {
        this.first_jump = first_jump;
    }
    @Column(name="second_jump")
    public int getSecond_jump() {
        return second_jump;
    }

    public void setSecond_jump(int second_jump) {
        this.second_jump = second_jump;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @JoinColumn(name="id_competition")
    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Double makePoints(int fj, int sj){
        int k = getCompetition().getHill().getkPoint();
        if (k < 185)
            points = 114 + (fj-k) * 1.8 + 114 + (sj-k) * 1.8;
        else
            points = 114 + (fj-k) * 1.2 + 114 + (sj-k) * 1.2;
        DecimalFormat dc = new DecimalFormat("##.##");
        points = Double.parseDouble(dc.format(points));
        return points;
    }
    @Override
    public int compareTo(Start start) {
        if (start == null || start.getPoints() == null)
            return -1;
        else
            return this.getPoints().compareTo(start.getPoints());
    }

    @Override
    public String toString() {
        return "Start{" +
                "jumper=" + jumper +
                ", competition=" + competition +
                ", points=" + points +
                ", id=" + id +
                ", first_jump=" + first_jump +
                ", second_jump=" + second_jump +
                '}';
    }
}
