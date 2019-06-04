package org.skijumping.skijumping.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Role {
    private int id;
    private String role;
    private List<User> userList;

    public Role(){

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

    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @OneToMany(fetch=FetchType.LAZY,
            mappedBy="role",
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return role;
    }
}
