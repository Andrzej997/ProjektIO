package pl.polsl.database.exceptions;

/**
 * Operator not found exception
 * 
 * @author Mateusz Sojka
 * @version 1.5
 */
public class OperatorNotFoundException extends Exception{
    
    public OperatorNotFoundException(){
        super();
    }
    
    public OperatorNotFoundException(String message){
        super(message);
    }
}
