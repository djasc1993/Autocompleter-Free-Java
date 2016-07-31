/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mudassirbit.autocompletor;

/**
 *
 * @author mudassir
 */
public class MAutoCompleteException extends RuntimeException{
    String message;

    private MAutoCompleteException() {
        
    }
    
    public MAutoCompleteException(String msg)
    {
        this.message = msg;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}
