package com.bridgelabz.bookstoreapp.exception;


@SuppressWarnings("serial")
public class CartException extends RuntimeException{
    public CartException(String exception){
        super(exception);
    }
}
