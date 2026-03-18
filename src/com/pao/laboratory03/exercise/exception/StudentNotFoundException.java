package com.pao.laboratory03.exercise.exception;

public class StudentNotFoundException extends RuntimeException{
    private final String name;

    public StudentNotFoundException(String name) {
        super("Studentul "+name+" nu a fost gasit.");
        this.name=name;
    }

    public String getName() { return name; }
}
