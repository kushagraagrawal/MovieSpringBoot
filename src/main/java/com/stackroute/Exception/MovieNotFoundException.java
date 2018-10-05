package com.stackroute.Exception;

public class MovieNotFoundException extends Exception {
    public MovieNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
