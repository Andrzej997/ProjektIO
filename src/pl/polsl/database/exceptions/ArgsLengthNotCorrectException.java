package pl.polsl.database.exceptions;

public class ArgsLengthNotCorrectException extends Exception{
    
    public ArgsLengthNotCorrectException(){
        super();
    }
    
    public ArgsLengthNotCorrectException(String message){
        super(message);
    }
}
