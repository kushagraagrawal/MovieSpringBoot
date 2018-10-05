package com.stackroute.exception;

public class MovieAlreadyExistsException extends Exception {
    public MovieAlreadyExistsException(String errorMessage){
        super(errorMessage);
    }
}
