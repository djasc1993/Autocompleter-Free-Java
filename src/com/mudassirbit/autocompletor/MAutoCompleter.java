/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mudassirbit.autocompletor;

import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 *
 * @author mudassir
 */
public interface MAutoCompleter extends ActionListener, FocusListener, KeyListener, MouseListener, ComponentListener{
    void configure() throws MAutoCompleteException; 
}
