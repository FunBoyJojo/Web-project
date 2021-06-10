package com.testplus.testplus.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column
    private String name, secondname, patronymic;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column
    private String login;
    @Column
    private String password;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CreatedTests> createdtests;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<PassedTests> passedtests;

    public List<CreatedTests> getCreatedtests() {
        return createdtests;
    }

    public void setCreatedtests(List<CreatedTests> createdtests) {
        this.createdtests = createdtests;
    }

    public List<PassedTests> getPassedtests() {
        return passedtests;
    }

    public void setPassedtests(List<PassedTests> passedtests) {
        this.passedtests = passedtests;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User(){}
    public User(int id, String name, String secondname, String patronymic, String login, String password) {
        this.id = id;
        this.name = name;
        this.secondname = secondname;
        this.patronymic = patronymic;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
