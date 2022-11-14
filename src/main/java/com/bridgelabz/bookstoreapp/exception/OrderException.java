package com.bridgelabz.bookstoreapp.exception;

@SuppressWarnings("serial")
public class OrderException extends RuntimeException{
    public OrderException(String exception){
        super(exception);
    }
}
