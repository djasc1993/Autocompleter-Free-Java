/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autocompletor.free;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author mudassir
 * @modified @djasc1993
 * @version 2.0.0
 */
class DefaultListItemFilter implements ListItemFilter {

    public DefaultListItemFilter() {
    }

    @Override
    public List<Object> filter(List<Object> itemList, Object item, int optionSearch) {
        List<Object> result = new ArrayList<>();
        if(itemList == null || item == null)
            return result;
        AutoCompleterExtra.pDebug("Search: "+item.toString());
        for(Object itemInList : itemList)
        {
            switch(optionSearch){
                case AutoCompleterInterface.MODE_SEARCH_CHAR_INIT:
                    if(item.toString().isEmpty() || itemInList.toString().toLowerCase().startsWith(item.toString().toLowerCase()))
                        result.add(itemInList);
                    break;
                case AutoCompleterInterface.MODE_SEARCH_CHAR_CONTAINS:
                    if(item.toString().isEmpty() || itemInList.toString().toLowerCase().contains(item.toString().toLowerCase()))
                        result.add(itemInList);
                    break;
                case AutoCompleterInterface.MODE_SEARCH_CHAR_END:
                    if(item.toString().isEmpty() || itemInList.toString().toLowerCase().endsWith(item.toString().toLowerCase()))
                        result.add(itemInList);
                    break;
            }
            
        }
        Collections.sort(result, (Object t, Object t1) -> t.toString().compareTo(t1.toString()));
        return result;
    }
    
}
