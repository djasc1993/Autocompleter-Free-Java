/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mudassirbit.autocompletor;

import java.util.List;

/**
 *
 * @author mudassir API contains only one method that filters the suggestion
 * list. You can modify the suggestion list result for your purpose. By default
 * it will filter the items that are starting with given text field value
 */
public interface ListItemFilter {

    /**
     * Implement this method to alter the suggestion filter list. method accepts two parameters and returns the resultant list
     * @param itemList list of available items . 
     * @param keyword  is the search keyword
     * @return  the list of resultant item list that shown in suggestion list
     */
    List<Object> filter(List<Object> itemList, Object keyword);
}
