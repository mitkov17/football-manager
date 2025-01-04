package com.mitkov.footballmanager.exceptions;

public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException(String name, String surname) {
        super("Player with name " + name + " " + surname + " already exists!");
    }

}
