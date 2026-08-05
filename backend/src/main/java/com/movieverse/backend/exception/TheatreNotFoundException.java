package com.movieverse.backend.exception;

public class TheatreNotFoundException extends RuntimeException{
    public TheatreNotFoundException(String msg){
        super(msg);
    }
}
