package com.testplus.testplus.models;

import javax.persistence.*;

@Entity
public class CreatedTests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne(fetch=FetchType.EAGER,
            cascade=CascadeType.ALL)
    @JoinColumn (name="test_id")
    private Test test;
    @ManyToOne(fetch=FetchType.EAGER,
            cascade=CascadeType.ALL)
    @JoinColumn (name="user_id")
    private User user;
    public CreatedTests(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CreatedTests() {
    }
}
