package pl.polsl.database.exceptions;

/**
 * Args length not correct exception
 * 
 * @author Mateusz Sojka
 * @version 1.0
 */
public class ArgsLengthNotCorrectException extends Exception{
    
    public ArgsLengthNotCorrectException(){
        super();
    }
    
    public ArgsLengthNotCorrectException(String message){
        super(message);
    }
}
