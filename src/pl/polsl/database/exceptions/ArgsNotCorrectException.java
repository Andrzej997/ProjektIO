package pl.polsl.database.exceptions;

/**
 * Args not correct exception
 * 
 * @author Mateusz Sojka
 * @version 1.0
 */
public class ArgsNotCorrectException extends RuntimeException{
    
    public  ArgsNotCorrectException(){
        super();
    }
    
    public ArgsNotCorrectException(String message){
        super(message);
    }
    
}
