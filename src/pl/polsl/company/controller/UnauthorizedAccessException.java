package pl.polsl.company.controller;

/**
 * Created by Krzysztof Stręk on 2016-02-09.
 */
public class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
