package pl.polsl.database.exceptions;

/**
 *
 * @author matis
 */
public class OperatorNotFoundException extends Exception{
    
    public OperatorNotFoundException(){
        super();
    }
    
    public OperatorNotFoundException(String message){
        super(message);
    }
}
