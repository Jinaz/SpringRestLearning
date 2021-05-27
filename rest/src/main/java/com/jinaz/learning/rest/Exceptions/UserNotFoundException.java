package com.jinaz.learning.rest.Exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(int id){
        super("no user found with id"+ id);
    }
}
