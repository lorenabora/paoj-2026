package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.*;

public class StudentService {
    //camp
    private List<Student> students= new ArrayList<>();
    //singleton
    private static StudentService instance;

    //constructor privat
    private StudentService(){}

    public static StudentService getInstance(){
        if(instance == null){
            instance = new StudentService();
        }
        return instance;
    }

//    a) void addStudent(String name, int age)
//  → creează Student și adaugă în listă
//  → dacă există deja un student cu același nume, aruncă RuntimeException
    public void addStudent(String name, int age){
        for (Student s:students){
            if(s.getName()==name){
                throw  new RuntimeException("Studentul cu numele "+name+" exista deja");
            }
        }
        students.add(new Student(name, age));
    }

    // b) findByName(String name)
    // → caută în listă, aruncă StudentNotFoundException dacă nu găsește
    public Student findByName(String name) {
        boolean found =false;
        for(Student s:students){
            if(s.getName()==name){
                found=true;
                return s;
            }
        }
        if(!found){
            throw new StudentNotFoundException(name);
        }
    }

    // c) addGrade(String studentName, Subject subject, double grade)
    // → găsește studentul (findByName) și adaugă nota
    public void addGrade(String studentName, Subject subject, double grade) {
        Student s = findByName(studentName);
        s.addGrade(subject, grade);
    }

    // d) printAllStudents()
    // → afișează toți studenții cu notele lor
    public void printAllStudents() {
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // e) printTopStudents()
    // → sortează studenții descrescător după medie și afișează
    public void printTopStudents() {
        students.sort(Comparator.comparingDouble(Student::getAverage).reversed());
        for(Student s: students){
            System.out.println(s);
        }
    }

    // f) getAveragePerSubject()
    // → calculează media pe fiecare materie (din toți studenții care au notă)
    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, List<Double>> colectieNote = new HashMap<>();
        for (Student s : students) {
            for (Map.Entry<Subject, Double> entry : s.getGrades().entrySet()) {
                Subject subject = entry.getKey();
                List<Double> noteStudent = Collections.singletonList(entry.getValue());

                colectieNote.putIfAbsent(subject, new ArrayList<>());
                colectieNote.get(subject).addAll(noteStudent);
            }
        }

        Map<Subject, Double> medii = new HashMap<>();
        for (Map.Entry<Subject, List<Double>> entry : colectieNote.entrySet()) {
            Subject subject = entry.getKey();
            List<Double> note = entry.getValue();

            double avg = note.stream()
                    .mapToDouble(d -> d)
                    .average()
                    .orElse(0);
            medii.put(subject, avg);
        }
        return medii;
    }
}