package org.skijumping.skijumping.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
public class User {
    private int id;
    private Role role;
    private String username;
    private String password;
    private String email;
    private Byte enabled;
    private Jumper jumper;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="id_role")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    @NotNull
    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @NotNull
    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @NotNull
    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "enabled")
    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }

    @OneToOne(mappedBy="user",cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})

    public Jumper getJumper() {
        return jumper;
    }

    public void setJumper(Jumper jumper) {
        this.jumper = jumper;
    }
}
