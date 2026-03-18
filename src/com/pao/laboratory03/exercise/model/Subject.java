package com.pao.laboratory03.exercise.model;

public enum Subject {
    //    - Constante: PAOJ, BD, SO, RC (sau alte materii)
// *    - Câmpuri: String fullName, int credits
// *    - Constructor privat, getteri
// *    - toString() → "PAOJ (Programare Avansată pe Obiecte, 6 credite)"
//
    PAOJ("Programare Avansata pe Obiecte", 6) {
        @Override
        public String toString() {
            return "PAOJ ("+this.getFullName()+", "+this.getCredits()+" credite)";
        }
    },
    SO("Sisteme de Operare", 4) {
        @Override
        public String toString() {
            return "SO ("+this.getFullName()+", "+this.getCredits()+" credite)";
        }
    },
    RC("Retele si Calculatoare", 4) {
        @Override
        public String toString() {
            return "RC ("+this.getFullName()+", "+this.getCredits()+" credite)";
        }
    },
    IA("Inteligenta Artificiala", 5) {
        @Override
        public String toString() {
            return "IA ("+this.getFullName()+", "+this.getCredits()+" credite)";
        }
    };

    private final String fullName;
    private final int credits;

    Subject(String fullName, int credits) {
        this.fullName = fullName;
        this.credits = credits;
    }

    public String getFullName() {
        return fullName;
    }

    public int getCredits() {
        return credits;
    }

    public abstract String toString();
}
