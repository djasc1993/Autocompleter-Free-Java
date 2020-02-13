package org.autocompletor.free;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author djasc1993
 */
public class AutoCompleterExtra {
    
    public static boolean DEBUG = false;
    
    public static void pDebug(Object obj){
        if(DEBUG)
            System.out.println(obj.toString());
    }
   
}
