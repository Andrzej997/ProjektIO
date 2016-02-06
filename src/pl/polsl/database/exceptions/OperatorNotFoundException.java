/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
