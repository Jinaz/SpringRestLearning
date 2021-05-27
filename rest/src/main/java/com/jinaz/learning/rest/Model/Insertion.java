package com.jinaz.learning.rest.Model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ADDMESSAGE")
public class Insertion {

    private @Id @GeneratedValue Long id;

    private String description;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    Insertion(){}
    public Insertion(String description, Status status){
        this.description = description;
        this.status = status;
    }


}
