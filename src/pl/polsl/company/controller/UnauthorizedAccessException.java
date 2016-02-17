package pl.polsl.company.controller;

/**
 * Unautorized access exception
 *
 * Created by Krzysztof Stręk on 2016-02-09.
 */
public class UnauthorizedAccessException extends Exception {

    /**
     * Exception constructor
     *
     * @param message String with message
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
