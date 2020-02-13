/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autocompletor.test;

/**
 *
 * @author jordy
 */

public class ObjectCustom{
        public String action;
        private final String title;

        public ObjectCustom(String title,String action) {
            this.title = title;
            this.action = action;
        }

        public void executeAction(){
            System.out.println(" Se ha ejecutado la Accion de CallBack "+action+"  | "+title);
                       
        }
        
        @Override
        public String toString() {
            return this.title;
        }

    }
