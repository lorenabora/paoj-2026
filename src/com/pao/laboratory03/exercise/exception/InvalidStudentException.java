package com.pao.laboratory03.exercise.exception;

import com.pao.laboratory03.exercise.model.Student;

public class InvalidStudentException extends RuntimeException{
    private final int age;

    public InvalidStudentException(int age) {
        super("Studentul nu este valid");
        this.age=age;
    }

    public int getAge() { return age; }
}
