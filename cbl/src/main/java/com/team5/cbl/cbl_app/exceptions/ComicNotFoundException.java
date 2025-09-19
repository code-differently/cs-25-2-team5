package com.team5.cbl.cbl_app.exceptions;

public class ComicNotFoundException extends IllegalArgumentException {
    public ComicNotFoundException(String message) {
        super(message);
    }
}
