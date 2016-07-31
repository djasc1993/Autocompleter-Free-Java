/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mudassirbit.autocompletor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
 * @author mudassir Class for text field auto completer
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
    private Listners listners;

    private MTextFieldAutoCompleter() {
        jList = new JList();
        jPanel = new JPanel();
        resultList = new ArrayList<>();
        //jPopupMenu = new JPopupMenu();
        this.items = new ArrayList<>();
        listItemFilter = new DefaultListItemFilter();
        showSuggestionBoxDefault = true;
        listners = new Listners();

    }

    /**
     * constructor that accepts JTextField object for which suggestions to be
     * shown
     *
     * @param jTextField
     */
    public MTextFieldAutoCompleter(JTextField jTextField) {
        this();
        this.jTextField = jTextField;

    }

    /**
     * Constructor that accepts JTextField and list of items
     *
     * @param jTextField is the object for which suggestions to be shown
     * @param items is the list of items that are to be shown in suggestion. You
     * no need to re assign when value changes, Suggestion will automatically
     * filtered on value in the text field.
     */
    public MTextFieldAutoCompleter(JTextField jTextField, List<Object> items) {
        this();
        this.jTextField = jTextField;
        this.items.addAll(items);

    }

    /**
     *
     * @param jTextField is the object for which suggestions to be shown
     * @param items is the list of items that are to be shown in suggestion. You
     * no need to re assign when value changes, Suggestion will automatically
     * filtered on value in the text field.
     * @param listItemFilter is the optional search API which you can configure
     * the suggestion list. If you want your own set of results on values in the
     * text filed then you can implement this API
     */
    public MTextFieldAutoCompleter(JTextField jTextField, List<Object> items, ListItemFilter listItemFilter) {
        this();
        this.jTextField = jTextField;
        this.items.addAll(items);
        this.listItemFilter = listItemFilter;

    }

    /**
     *
     * @param jTextField is the object for which suggestions to be shown
     * @param items is the list of items that are to be shown in suggestion. You
     * no need to re assign when value changes, Suggestion will automatically
     * filtered on value in the text field.
     * @param numOfItemsToShowInPopup is the number of items to be showed in the
     * suggestion list
     */
    public MTextFieldAutoCompleter(JTextField jTextField, List<Object> items, int numOfItemsToShowInPopup) {
        this();
        this.jTextField = jTextField;
        this.items.addAll(items);
        this.numOfItems = numOfItemsToShowInPopup;

    }

    /**
     *
     * @param jTextField is the object for which suggestions to be shown
     * @param items is the list of items that are to be shown in suggestion. You
     * no need to re assign when value changes, Suggestion will automatically
     * filtered on value in the text field.
     * @param listItemFilter is the optional search API which you can configure
     * the suggestion list. If you want your own set of results on values in the
     * text filed then you can implement this API
     * @param numOfItemsToShowInPopup is the number of items to be showed in
     * the suggestion list
     */
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
        if (existingComp != null) {
            existingComp.tearDown();
            map.remove(jTextField);
        }

        //add this to map
        map.put(jTextField, this);

        //adding all listners
        this.jTextField.addKeyListener(listners);
        this.jTextField.addActionListener(listners);
        this.jTextField.addFocusListener(listners);
        jList.addMouseListener(listners);

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
    public int getNumOfItems() {
        return numOfItems;
    }

    @Override
    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    @Override
    public void addItems(List<Object> items) {
        this.items.addAll(items);
    }

    @Override
    public void removeAllItems() {
        this.items.clear();
    }

    @Override
    public void removeItem(Object o) {
        this.items.remove(o);
    }

    @Override
    public void removeItem(int i) {
        this.items.remove(i);
    }

    @Override
    public void setListItemFilter(ListItemFilter listItemFilter) {
        this.listItemFilter = listItemFilter;
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
    public boolean isShowSuggestionBoxAtBegin() {
        return showSuggestionBoxDefault;
    }

    @Override
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
        this.jTextField.removeActionListener(listners);
        this.jTextField.removeMouseListener(listners);
        this.jTextField.removeFocusListener(listners);
        this.jTextField.removeKeyListener(listners);
        this.jTextField.removeComponentListener(listners);

    }

    private class Listners implements ActionListener, FocusListener, KeyListener, MouseListener, ComponentListener {

        @Override
        public final void componentResized(ComponentEvent ce) {
            jList.setSize((int) jTextField.getSize().getWidth(), numOfItems * CELL_HEIGHT);
            jPanel.setSize((int) jTextField.getSize().getWidth(), numOfItems * CELL_HEIGHT + 6);

        }

        @Override
        public final void componentMoved(ComponentEvent ce) {
            double xPointOfTextField = jTextField.getLocation().getX();
            double yPointOfTextField = jTextField.getLocation().getY();

            double heightOfTextField = jTextField.getSize().getHeight();

            int x = (int) Math.ceil(xPointOfTextField);
            int y = (int) Math.ceil(yPointOfTextField + heightOfTextField);

            jPanel.setLocation((int) (x), (int) (y));

            jList.setLocation(PANEL_BORDER_SIZE, PANEL_BORDER_SIZE);

        }

        @Override
        public final void componentShown(ComponentEvent ce) {
        }

        @Override
        public final void componentHidden(ComponentEvent ce) {
            jPanel.setVisible(false);
        }

        @Override
        public final void mouseClicked(MouseEvent me) {

            int indexOfTheItemClicked = me.getY() / CELL_HEIGHT;
            Object val = getItemFromResultAtIndex(indexOfTheItemClicked);

            selectedAValue(val);
        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public final void mouseReleased(MouseEvent me) {
        }

        @Override
        public final void mouseEntered(MouseEvent me) {
        }

        @Override
        public final void mouseExited(MouseEvent me) {
        }

        @Override
        public final void actionPerformed(ActionEvent ae) {
            selectedAValue(jList.getSelectedValue());
        }

        @Override
        public final void keyTyped(KeyEvent ke) {
        }

        @Override
        public final void keyPressed(KeyEvent ke) {
        }

        @Override
        public final void keyReleased(KeyEvent ke) {
            System.out.println("typed");
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
        public final void focusGained(FocusEvent fe) {
            showPanelBasedOnVal();
            updateTheJListValue();

        }

        @Override
        public final void focusLost(FocusEvent fe) {
            jPanel.setVisible(false);

        }
    }

}
