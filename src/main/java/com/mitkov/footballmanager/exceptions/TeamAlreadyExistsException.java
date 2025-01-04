package com.mitkov.footballmanager.exceptions;

public class TeamAlreadyExistsException extends RuntimeException {

    public TeamAlreadyExistsException(String name) {
        super("Team with name " + name + " already exists!");
    }

}
