/*
 * Copyright (c) 2016.
 */

package com.crooks.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by johncrooks on 6/28/16.
 */
@Entity
@Table(name="photos")
public class Photo {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User sender;

    @ManyToOne
    User recipient;

    @Column(nullable = false)
    String filename;

    @Column (nullable = false)
    int viewLength;

    @Column
    Boolean isPublic;

    @Column
    Integer counter;

    public Photo(int id, User sender, User recipient, String filename, int viewLength, Boolean isPublic, Integer counter) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.filename = filename;
        this.viewLength = viewLength;
        this.isPublic = isPublic;
        this.counter = 0;
    }

    public Photo(User sender, User recipient, String filename, int viewLength, Boolean isPublic, Integer counter) {
        this.sender = sender;
        this.recipient = recipient;
        this.filename = filename;
        this.viewLength = viewLength;
        this.isPublic = isPublic;
        this.counter = 0;
    }

    public Photo(User sender, User recipient, String filename, int viewLength, Boolean isPublic) {
        this.sender = sender;
        this.recipient = recipient;
        this.filename = filename;
        this.viewLength = viewLength;
        this.isPublic = isPublic;
    }

    public Photo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getViewLength() {
        return viewLength;
    }

    public void setViewLength(int viewLength) {
        this.viewLength = viewLength;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}

