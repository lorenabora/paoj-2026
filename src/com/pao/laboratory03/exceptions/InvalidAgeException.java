package com.pao.laboratory03.exceptions;

public class InvalidAgeException extends RuntimeException {
        private final int age;

        public InvalidAgeException(int age) {
            super("Nu ai cum sa ai varsta de "+age+" ani.");
            this.age=age;
        }

        public int getAge() { return age; }
}
