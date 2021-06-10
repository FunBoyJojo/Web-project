package com.testplus.testplus.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String title;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Query> querys;

    @Column
    private int countOfPass;
    @Column
    private long time;

    public int getCountOfPass() {
        return countOfPass;
    }

    public void setCountOfPass(int countOfPass) {
        this.countOfPass = countOfPass;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Test() {
    }

    public Test(String title) {
        this.title = title;
    }

    public Test(String title, long time, int countOfPass) {
        this.title = title;
        this.time = time;
        this.countOfPass = countOfPass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Query> getQuerys() {
        return querys;
    }

    public void setQuerys(List<Query> querys) {
        this.querys = querys;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
