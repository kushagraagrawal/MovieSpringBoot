package com.stackroute.exception;

public class MovieNotFoundException extends Exception {
    public MovieNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
