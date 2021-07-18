package com.petrov.rest;

public class BadRequestException extends Throwable {
    public BadRequestException(String msg){
        super(msg);
    }
}
