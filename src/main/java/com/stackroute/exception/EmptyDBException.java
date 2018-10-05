package com.stackroute.exception;

public class EmptyDBException extends Exception {
    public EmptyDBException(String errMessage){
        super(errMessage);
    }
}
