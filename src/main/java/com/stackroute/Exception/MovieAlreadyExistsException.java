package com.stackroute.Exception;

public class MovieAlreadyExistsException extends Exception {
    public MovieAlreadyExistsException(String errorMessage){
        super(errorMessage);
    }
}
