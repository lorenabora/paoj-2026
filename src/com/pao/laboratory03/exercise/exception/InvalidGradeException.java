package com.pao.laboratory03.exercise.exception;

public class InvalidGradeException extends RuntimeException{
    private final double grade;

    public InvalidGradeException(double grade) {
        super("Nu ai cum sa ai nota"+grade);
        this.grade=grade;
    }

    public double getGrade() { return grade; }
}
