package com.pablopronsky.eggnews.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;

    private boolean isActive;
    @Temporal(TemporalType.DATE)
    private Date date;

    public News(){

    }

    public News(Long id, String title, String body, boolean isActive, Date date) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.isActive = isActive;
        this.date = date;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
