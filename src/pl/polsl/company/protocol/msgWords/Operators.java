/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.company.protocol.msgWords;

/**
 *
 * @author matis
 */
public enum Operators {
    NotEqual("<>"),
    GreaterOrEqual(">="),
    Greater(">"),
    LowerOrEqual("<="),
    Lower("<"),
    Equal("="),
    Beetween("BEETWEEN"),
    Like("LIKE"),
    In("IN");
    
    private String operatorText;
    
    Operators(String text){
        operatorText = text;
    }
    
    String getOperatorText(){
        return operatorText;
    }
}
