/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autocompletor.free;

/**
 *
 * @author mudassir
 */
public class AutoCompleteException extends RuntimeException{
    String message;

    private AutoCompleteException() {
        
    }
    
    public AutoCompleteException(String msg)
    {
        this.message = msg;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}
