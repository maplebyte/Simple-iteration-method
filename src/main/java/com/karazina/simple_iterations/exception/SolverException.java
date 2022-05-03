package com.karazina.simple_iterations.exception;

/**
 * Загальний клас для помилок
 */
public class SolverException extends RuntimeException {

    public SolverException(String message) {
        super(message);
    }

    public SolverException(String message, Throwable e) {
        super(message, e);
    }

}
