package com.jinaz.learning.rest.subtypes;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Chatmessage {
    private @Id @GeneratedValue Long id;
    private int senderid;
    private String msg;

    public int getSenderid() {
        return senderid;
    }

    public void setSenderid(int senderid) {
        this.senderid = senderid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    Chatmessage(){}
    Chatmessage(String msg, int senderid){
        this.msg = msg;
        this.senderid = senderid;
    }

    @Override
    public String toString(){
        return "{"+"messageid="+this.id+", message=\'"+this.msg+"\'}";
    }
}
