package com.pao.laboratory03.exercise.model;

import com.pao.laboratory03.exercise.exception.InvalidGradeException;
import com.pao.laboratory03.exercise.exception.InvalidStudentException;

import java.util.HashMap;
import java.util.Map;

public class Student {
//    - Câmpuri private: String name, int age, Map<Subject, Double> grades
    private String name;
    private int age;
    private Map<Subject, Double> grades;
// *    - Constructor: Student(String name, int age)
// *      → inițializează grades ca HashMap gol
// *      → validare: dacă age < 18 sau age > 60, aruncă InvalidStudentException
    public Student(String name, int age){
        if(age<18 || age>60){
            throw new InvalidStudentException(age);
        }
        this.age=age;
        this.name=name;
        this.grades=new HashMap<>();
    }
// *    - Metode: getName(), getAge(), getGrades()
    public String getName(){ return name;}
    public int getAge(){return age;}
    public Map<Subject, Double> getGrades(){return grades;}
// *    - addGrade(Subject subject, double grade)
// *      → dacă grade < 1 sau grade > 10, aruncă InvalidGradeException
// *      → pune nota în map (suprascrie dacă materia există deja)
    public void addGrade(Subject subject, double grade){
        if (grade<1 || grade>10){
            throw new InvalidGradeException(grade);
        }
        grades.put(subject, grade);
    }
// *    - double getAverage()
// *      → calculează media aritmetică a notelor (returnează 0 dacă nu are note)
    public double getAverage(){
        double sum=0;
        for (Map.Entry<Subject, Double> entry : grades.entrySet()) {
            sum += entry.getValue();
        }
        return sum/grades.size();
    }
// *    - toString() → "Student{name='Ana', age=20, avg=8.50}"
    @Override public String toString(){
        return "Student{name="+this.getName()+" ,age="+this.getAge()+", avg="+this.getAverage()+"}";
    }
}
