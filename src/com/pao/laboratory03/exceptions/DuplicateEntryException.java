package com.pao.laboratory03.exceptions;

public class DuplicateEntryException extends RuntimeException{
    private final String name;

    public DuplicateEntryException(String name) {
        super(name+" este deja in lista");
        this.name=name;
    }

    public String getName() { return name; }
}