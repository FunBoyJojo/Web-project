package com.testplus.testplus.models;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
public class Answer {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int id;


    public Answer() {
    }

    public Answer(String answer, Boolean isTrue) {

        this.answer = answer;
        this.isTrue = isTrue;
    }

    @ManyToOne(fetch=FetchType.LAZY,
            cascade=CascadeType.ALL)
    @JoinColumn (name="query_id")
    private Query query;
    @Column
    private String answer;
    @Column
    private Boolean isTrue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getTrue() {
        return isTrue;
    }

    public void setTrue(Boolean aTrue) {
        isTrue = aTrue;
    }
}
