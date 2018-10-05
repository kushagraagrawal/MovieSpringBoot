package com.stackroute.Exception;

public class EmptyDBException extends Exception {
    public EmptyDBException(String errMessage){
        super(errMessage);
    }
}
