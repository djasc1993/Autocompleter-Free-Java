/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mudassirbit.autocompletor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.Border;

/**
 *
 * @author mudassir
 */
public class MTextFieldAutoCompleter implements MAutoCompleter {
    
    private static Map<Component, MTextFieldAutoCompleter> map = new HashMap<>();
    
    private JTextField jTextField;
    private JPanel jPanel;
    //private JPopupMenu jPopupMenu;
    private JList jList;
    private int numOfItems = 5;
    private int CELL_HEIGHT = 20;
    private final int PANEL_BORDER_SIZE = 1;
    private List<Object> items;
    private ListItemFilter listItemFilter;
    private List<Object> resultList;
    private boolean showSuggestionBoxDefault;

    private MTextFieldAutoCompleter() {
        jList = new JList();
        jPanel = new JPanel();
        resultList = new ArrayList<>();
        //jPopupMenu = new JPopupMenu();
        this.items = new ArrayList<>();
        listItemFilter = new DefaultListItemFilter();
        showSuggestionBoxDefault = true;

    }

    public MTextFieldAutoCompleter(JTextField jTextField) {
        this();
        this.jTextField = jTextField;

    }

    public MTextFieldAutoCompleter(JTextField jTextField, List<Object> items) {
        this();
        this.jTextField = jTextField;
        this.items.addAll(items);

    }

    public MTextFieldAutoCompleter(JTextField jTextField, List<Object> items, ListItemFilter listItemFilter) {
        this();
        this.jTextField = jTextField;
        this.items.addAll(items);
        this.listItemFilter = listItemFilter;

    }

    public MTextFieldAutoCompleter(JTextField jTextField, List<Object> items, int numOfItemsToShowInPopup) {
        this();
        this.jTextField = jTextField;
        this.items.addAll(items);
        this.numOfItems = numOfItemsToShowInPopup;

    }

    public MTextFieldAutoCompleter(JTextField jTextField, List<Object> items, ListItemFilter listItemFilter, int numOfItemsToShowInPopup) {
        this();
        this.jTextField = jTextField;
        this.items.addAll(items);
        this.listItemFilter = listItemFilter;
        this.numOfItems = numOfItemsToShowInPopup;

    }

    @Override
    public void configure() throws MAutoCompleteException {

        if (this.jTextField == null) {
            throw new MAutoCompleteException("JTextField object not set");
        }

        MTextFieldAutoCompleter existingComp = map.get(jTextField);
        if(existingComp != null)
        {
            existingComp.tearDown();
            map.remove(jTextField);
        }
        
        //add this to map
        map.put(jTextField, this);
        
        //adding all listners
        this.jTextField.addKeyListener(this);
        this.jTextField.addActionListener(this);
        this.jTextField.addFocusListener(this);
        jList.addMouseListener(this);

        CELL_HEIGHT = (int) jTextField.getSize().getHeight();
        double xPointOfTextField = jTextField.getLocation().getX();
        double yPointOfTextField = jTextField.getLocation().getY();

        double heightOfTextField = jTextField.getSize().getHeight();

        int x = (int) Math.ceil(xPointOfTextField);
        int y = (int) Math.ceil(yPointOfTextField + heightOfTextField);

        jPanel.setLocation((int) (x), (int) (y));
        jPanel.setSize((int) jTextField.getSize().getWidth(), numOfItems * CELL_HEIGHT + 6);

        jPanel.setBackground(new Color(240, 240, 240));
        this.jTextField.getParent().add(jPanel, 2, 0);
        jPanel.add(jList);

        jList.setSize((int) jTextField.getSize().getWidth(), numOfItems * CELL_HEIGHT);

        jList.setFixedCellWidth((int) jList.getParent().getSize().getWidth() - PANEL_BORDER_SIZE * 2);
        jList.setFixedCellHeight(CELL_HEIGHT);
        jList.setLocation(PANEL_BORDER_SIZE, PANEL_BORDER_SIZE);
        ListModel defaultListModel = new DefaultListModel();
        System.out.println("dm: " + defaultListModel.getSize());
        jList.setModel(defaultListModel);
        jList.setListData(toArray(this.items));
        jList.setSelectedIndex(0);
        jList.setVisible(true);
        System.out.println("yList: " + jList.getLocation().x);
        Border listBorder = BorderFactory.createLineBorder(Color.BLACK, PANEL_BORDER_SIZE);
        jPanel.setBorder(listBorder);
        jPanel.setVisible(false);
    }

    private Object[] toArray(List<Object> items) {
        Object[] itemsObject = new Object[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemsObject[i] = items.get(i);
        }
        return itemsObject;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        selectedAValue(jList.getSelectedValue());
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {

        if (ke.getExtendedKeyCode() == 38) {
            //up key
            if (jList.getSelectedIndex() > 0) {
                jList.setSelectedIndex(jList.getSelectedIndex() - 1);
            }
        } else if (ke.getExtendedKeyCode() == 40) {
            //down key
            if (jList.getSelectedIndex() < jList.getLastVisibleIndex()) {
                jList.setSelectedIndex(jList.getSelectedIndex() + 1);
            }
        } else {

            updateTheJListValue();
            showPanelBasedOnVal();

        }

    }

    @Override
    public void focusGained(FocusEvent fe) {
        showPanelBasedOnVal();
        updateTheJListValue();

    }

    @Override
    public void focusLost(FocusEvent fe) {
        jPanel.setVisible(false);

    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public void addItems(List<Object> items) {
        this.items.addAll(items);
    }

    public void removeAllItems() {
        this.items.clear();
    }

    public void removeItem(Object o) {
        this.items.remove(o);
    }

    public void removeItem(int i) {
        this.items.remove(i);
    }

    public void setListItemFilter(ListItemFilter listItemFilter) {
        this.listItemFilter = listItemFilter;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println("Cliecked: " + me.getX() + ":" + me.getY());

        int indexOfTheItemClicked = me.getY() / CELL_HEIGHT;
        System.out.println("index: " + indexOfTheItemClicked);
        Object val = getItemFromResultAtIndex(indexOfTheItemClicked);

        selectedAValue(val);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    private void selectedAValue(Object val) {
        if (val != null && !val.toString().isEmpty()) {
            jTextField.setText(val.toString());
            jTextField.transferFocus();
        }
    }

    private void updateTheJListValue() {
        resultList = listItemFilter.filter(items, jTextField.getText());
        //jList.removeAll();
        jList.setListData(toArray(resultList));
        jList.setSelectedIndex(0);

        int updatedHieght = 0;
        if ((resultList.size() + 1) > numOfItems) {
            updatedHieght = numOfItems;
        } else {
            updatedHieght = resultList.size() + 1;
        }

        updatedHieght = (int) (updatedHieght * CELL_HEIGHT);
        jPanel.setSize((int) Math.ceil(jPanel.getSize().getWidth()), updatedHieght);
    }

    private Object getItemFromResultAtIndex(int index) {
        Object val = "";
        try {
            val = resultList.get(index);
        } catch (Exception e) {

        }
        return val;

    }

    @Override
    public void componentResized(ComponentEvent ce) {
        jList.setSize((int) jTextField.getSize().getWidth(), numOfItems * CELL_HEIGHT);
        jPanel.setSize((int) jTextField.getSize().getWidth(), numOfItems * CELL_HEIGHT + 6);

    }

    @Override
    public void componentMoved(ComponentEvent ce) {
        double xPointOfTextField = jTextField.getLocation().getX();
        double yPointOfTextField = jTextField.getLocation().getY();

        double heightOfTextField = jTextField.getSize().getHeight();

        int x = (int) Math.ceil(xPointOfTextField);
        int y = (int) Math.ceil(yPointOfTextField + heightOfTextField);

        jPanel.setLocation((int) (x), (int) (y));

        jList.setLocation(PANEL_BORDER_SIZE, PANEL_BORDER_SIZE);

    }

    @Override
    public void componentShown(ComponentEvent ce) {
    }

    @Override
    public void componentHidden(ComponentEvent ce) {
        jPanel.setVisible(false);
    }

    public boolean isShowSuggestionBoxAtBegin() {
        return showSuggestionBoxDefault;
    }

    public void setShowSuggestionBoxAtBegin(boolean showSuggestionBoxDefault) {
        this.showSuggestionBoxDefault = showSuggestionBoxDefault;
    }

    private void showPanelBasedOnVal() {
        if (!showSuggestionBoxDefault && jTextField.getText().isEmpty()) {
            jPanel.setVisible(false);
        } else {
            jPanel.setVisible(true);
        }
    }

    private void tearDown() {
        this.items.clear();
        this.jList.setVisible(false);
        this.jList.invalidate();
        this.jPanel.setVisible(false);
        this.jPanel.invalidate();
        this.jTextField.removeActionListener(this);
        this.jTextField.removeMouseListener(this);
        this.jTextField.removeFocusListener(this);
        this.jTextField.removeKeyListener(this);
        this.jTextField.removeComponentListener(this);
        
    }

}
