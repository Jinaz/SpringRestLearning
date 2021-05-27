package com.jinaz.learning.rest.Model;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    private @Id @GeneratedValue int userid;
    private String prename;
    private String surname;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }


    User(){}
    public User(String name1, String name2, String message){
        this.prename = name1;
        this.surname = name2;
        this.message = message;

    }

    @Override
    public String toString(){
        return "User{"+"id="+this.userid+", prename="+this.prename+", surname="+this.surname+", message="+this.message.toString()+"}";
    }

}
