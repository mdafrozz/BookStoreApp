package com.bridgelabz.bookstoreapp.exception;

@SuppressWarnings("serial")
public class UserException extends RuntimeException{
    public UserException(String exception){
        super(exception);
    }
}
