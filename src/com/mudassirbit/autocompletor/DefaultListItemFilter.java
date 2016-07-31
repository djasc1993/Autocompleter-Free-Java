/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mudassirbit.autocompletor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author mudassir
 */
class DefaultListItemFilter implements ListItemFilter {

    public DefaultListItemFilter() {
    }

    @Override
    public List<Object> filter(List<Object> itemList, Object item) {
        List<Object> result = new ArrayList<>();
        if(itemList == null || item == null)
            return result;
        for(Object itemInList : itemList)
        {
            if(item.toString().isEmpty() || itemInList.toString().toLowerCase().startsWith(item.toString().toLowerCase()))
                result.add(itemInList);
        }
         Collections.sort(result, new Comparator<Object>() {
            @Override
            public int compare(Object t, Object t1) {
                return t.toString().compareTo(t1.toString());
            }
        });
        return result;
    }
    
}
