package com.testplus.testplus.models;

import javax.persistence.*;
@Entity
public class PassedTests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(fetch=FetchType.LAZY,
            cascade=CascadeType.ALL)
    @JoinColumn (name="user_id")
    private User user;

    public PassedTests(double result) {

        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public int getCountoftry() {
        return countoftry;
    }

    public void setCountoftry(int countoftry) {
        this.countoftry = countoftry;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public PassedTests() {
    }

    @ManyToOne(fetch=FetchType.EAGER,
            cascade=CascadeType.ALL)
    @JoinColumn (name="test_id")
    private Test test;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Column
    private double result;
    @Column
    private int countoftry;
    @Column
    private long time;
}
