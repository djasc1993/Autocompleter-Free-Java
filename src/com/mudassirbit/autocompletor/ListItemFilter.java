/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mudassirbit.autocompletor;

import java.util.List;

/**
 *
 * @author mudassir
 */
public interface ListItemFilter {
   List<Object> filter(List<Object> itemList, Object item);
}
