/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autocompletor.free;

import java.util.List;

/**
 *
 * @author mudassir Auto completer interface
 * @modified @djasc1993
 */
public interface AutoCompleterInterface {

    /*************PARAMETERS*************/
    public static final int MODE_SEARCH_CHAR_INIT = 1;
    public static final int MODE_SEARCH_CHAR_CONTAINS = 2;
    public static final int MODE_SEARCH_CHAR_END = 3;
    
    /**
     * methods that configures the auto completer for the given Component;
     * Currently only text field is implemented. After creating the object you
     * must call this configure method to see the suggestion for the text field
     *
     * @throws AutoCompleteException
     */
    void configure() throws AutoCompleteException;

    /**
     * Returns the number of items that shows in the suggestion list
     *
     * @return
     */
    int getNumberItems();

    /**
     * sets the number of items to be showed in the suggestion list
     *
     * @param numOfItems
     */
    void setNumberItems(int numOfItems);

    /**
     * Adds list of items to the existing suggestion list; it will append to
     * existing list. If you want replace the list then remove all the existing
     * items first then add. remove the items usign #removeAllItems()
     *
     * @param items
     */
    void addItems(List<Object> items);

    /**
     * will remove all the existing items in the list
     */
    void removeAllItems();

    /**
     * will remove given item from the suggestion list
     *
     * @param o
     */
    void removeItem(Object o);

    /**
     * will remove ith item from the suggestion list
     *
     * @param i
     */
    void removeItem(int i);

    /**
     * sets the ListItemFilter API. if you want to customize the list suggestion
     * of your own then implement the this interface and override the filter
     * method of your purpose.By default the filter is based on normal search
     * For more details read its API
     *
     * @param listItemFilter
     */
    void setListItemFilter(ListItemFilter listItemFilter);

    /**
     * returns true if initialy suggestions are shown
     *
     * @return
     */

}
