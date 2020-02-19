package org.autocompletor.free;

import java.awt.BorderLayout;
import static org.autocompletor.free.AutoCompleterExtra.pDebug;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;


/**
 *
 * @author @mudassir
 * @modified @djasc1993 www.excellentsoft.net (2020)
 * @version 2.0.0
 * License:
 * This project has been cloned from https://github.com/mudassirbit/AutoCompleter, 
    * without forgetting to value @mudassir's effort, I have required to apply changes 
    * for the integration of my projects and since there has been no movement for 4 years With @mudassir,
    * I have uploaded a new repository
    * 
    * Note: Class names have been changes, just for simplicity of calling.
  *
  *Class for text field auto completer
 */
public class AutoCompleter implements AutoCompleterInterface {
    protected StringBuilder CURRENT_TEXT = new StringBuilder();
    protected final int PANEL_BORDER_SIZE = 1;
    private Theme theme;
    protected int CELL_HEIGHT;
    protected int NUMBER_OF_ITEMS = 5;
    
    private boolean CONCAT_SEARCH = false;
    private static Map<Component, AutoCompleter> map = new HashMap<>();
    private JTextField jTextField;
    private JPanel jPanel;
    private JScrollPane jScroll;
    private JList jList;
    private AutoCallback callback;
    private int opt_method_search = 2;
    private List<Object> items;
    private ListItemFilter listItemFilter;
    private List<Object> resultList;
    private Listeners listners;
    private String CHAR_SEPARATE = "";

    private AutoCompleter() {
        startComponents();
    }
    
    /**
     * constructor that accepts JTextField object for which suggestions to be
     * shown
     * @param jTextField
     */
    public AutoCompleter(JTextField jTextField) {
        this.jTextField = jTextField;
        startComponents();
    }
    
    /**
     * constructor that accepts JTextField object for which suggestions to be
     * shown
     *
     * @param jTextField
     * @param theme Can send Null for Default
     */
    public AutoCompleter(JTextField jTextField, Theme theme) {
        this.jTextField = jTextField;
        this.theme = theme;
        startComponents();
    }
    
    /**
     * constructor that accepts JTextField object for which suggestions to be
     * shown
     *
     * @param jTextField
     * @param callback On action clic o select Item
     * @param theme  Can send Null for Default
     */
    public AutoCompleter(JTextField jTextField,Theme theme,  AutoCallback callback) {
        this.jTextField = jTextField;
        this.callback = callback;
        this.theme = theme;
        startComponents();
    }

    /**
     * Constructor that accepts JTextField and list of items
     *
     * @param jTextField is the object for which suggestions to be shown
     * @param items is the list of items that are to be shown in suggestion. You
     * no need to re assign when value changes, Suggestion will automatically
     * filtered on value in the text field.
     */
    public AutoCompleter(JTextField jTextField, List<Object> items) {
        this.jTextField = jTextField;
        this.items.addAll(items);
        startComponents();

    }
    
    @Deprecated
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
    public AutoCompleter(JTextField jTextField, List<Object> items, ListItemFilter listItemFilter) {
        this.jTextField = jTextField;
        this.items.addAll(items);
        this.listItemFilter = listItemFilter;
        startComponents();

    }

    @Deprecated
    /**
     *
     * @param jTextField is the object for which suggestions to be shown
     * @param items is the list of items that are to be shown in suggestion. You
     * no need to re assign when value changes, Suggestion will automatically
     * filtered on value in the text field.
     * @param numOfItemsToShowInPopup is the number of items to be showed in the
     * suggestion list
     */
    public AutoCompleter(JTextField jTextField, List<Object> items, int numOfItemsToShowInPopup) {
        this.jTextField = jTextField;
        this.items.addAll(items);
        this.NUMBER_OF_ITEMS = numOfItemsToShowInPopup;
        startComponents();
    }
    
    @Deprecated
    /**
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
    public AutoCompleter(JTextField jTextField, List<Object> items, ListItemFilter listItemFilter, int numOfItemsToShowInPopup) {
        this();
        this.jTextField = jTextField;
        this.items.addAll(items);
        this.listItemFilter = listItemFilter;
        this.NUMBER_OF_ITEMS = numOfItemsToShowInPopup;
    }
    
    @Override
    public void configure() throws AutoCompleteException {
        
        if (this.jTextField == null) {
            throw new AutoCompleteException("JTextField object not set");
        }

        AutoCompleter existingComp = map.get(jTextField);
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
        //jPanel.setPreferredSize(new Dimension((int) jTextField.getSize().getWidth(), 5 * CELL_HEIGHT));
        jPanel.setSize(new Dimension((int) jTextField.getSize().getWidth(), NUMBER_OF_ITEMS * CELL_HEIGHT));
        theme.setColorBackground(jPanel, jList);
        jScroll.setViewportView(jList);
        ListModel defaultListModel = new DefaultListModel();
        jList.setModel(defaultListModel);
        jList.setListData(this.items.toArray());
        jList.setSelectedIndex(0);
        //jList.setFixedCellWidth((int) jTextField.getSize().getWidth() - 20);
        jScroll.setPreferredSize(new Dimension((int)jTextField.getSize().getWidth(), (NUMBER_OF_ITEMS * CELL_HEIGHT) - 10));
        //jList.setPreferredSize(new Dimension((int)jTextField.getSize().getWidth(), 1 * CELL_HEIGHT));
        //jList.setFixedCellHeight(CELL_HEIGHT);
       // jList.setVisibleRowCount(100);
       // jList.setVisible(true);
        jList.setLayoutOrientation(JList.VERTICAL);
        
        this.jTextField.getParent().add(jPanel, 2, 0);
        jPanel.add(jScroll, BorderLayout.PAGE_START);
        
        Border listBorder = BorderFactory.createLineBorder(this.theme.backgroundPanel, PANEL_BORDER_SIZE);
        jPanel.setBorder(listBorder);
        jPanel.setVisible(false);
    }
    
    @Deprecated
    private Object[] toArray(List<Object> items) {
        Object[] itemsObject = new Object[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemsObject[i] = items.get(i);
        }
        return itemsObject;
    }
    
    @Deprecated
    @Override
    public int getNumberItems() {
        return NUMBER_OF_ITEMS;
    }
    
    @Deprecated
    @Override
    public void setNumberItems(int numOfItems) {
        this.NUMBER_OF_ITEMS = numOfItems;
    }

    @Override
    public void addItems(List<Object> items) {
        this.items.addAll(items);
    }
    
    public void addItem(Object item) {
        if(this.items != null)
            this.items.add(item);
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

    /**!important: Get Object Select in the lis
     * @param obj is Object of selecction
     */
    protected void selectedAValue(Object obj) {
        if (obj != null && !obj.toString().isEmpty()) {
            if(!CONCAT_SEARCH){
                CURRENT_TEXT = new StringBuilder();
            }
            CURRENT_TEXT.append(obj.toString()).append(CHAR_SEPARATE);
            jTextField.setText(CURRENT_TEXT.toString());
            //CURRENT_TEXT.append(jTextField.getText());
            jTextField.transferFocus();
            jPanel.setVisible(false);
            if(this.callback != null ){
                this.callback.callBack(obj);
            }
        }
    }

    protected void updateTheJListValue() {
        if(jTextField.getText().trim().isEmpty()){
            jPanel.setVisible(false);
            CURRENT_TEXT = new StringBuilder();
            return;
        }
        resultList = listItemFilter.filter(items, jTextField.getText().replace(CURRENT_TEXT.toString(), ""), this.opt_method_search);
        if(resultList.size()>0){
            jList.setListData(toArray(resultList));
            jList.setSelectedIndex(0);

            int updatedHieght = 0;
            if ((resultList.size() + 1) > NUMBER_OF_ITEMS) {
                updatedHieght = NUMBER_OF_ITEMS;
            } else {
                updatedHieght = resultList.size() + 1;
            }

            updatedHieght = (int) (updatedHieght * CELL_HEIGHT);
            
            jScroll.setPreferredSize(new Dimension((int)jTextField.getSize().getWidth(), updatedHieght - 10));
            jPanel.setSize((int) Math.ceil(jPanel.getSize().getWidth()), updatedHieght);
            jList.setVisible(true);
            jPanel.setVisible(true);
        }else
            jPanel.setVisible(false);
    }

    protected Object getItemFromResultAtIndex(int index) {
        Object val = "";
        try {
            val = resultList.get(index);
        } catch (Exception e) {
            pDebug(e.getMessage());
        }
        return val;

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

    private void startComponents() {
        jList = new JList();
        jPanel = new JPanel();
        jScroll = new JScrollPane();
        resultList = new ArrayList<>();
        this.items = new ArrayList<>();
        listItemFilter = new DefaultListItemFilter();
        listners = new Listeners(this);
        if(theme == null)
            theme = new Theme(new Color(240,240,240),Color.BLACK);
        configure();
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public int getMethodSearch() {
        return opt_method_search;
    }

    public void setMethodSearch(int opt_method_search) {
        this.opt_method_search = opt_method_search;
    }

    public JTextField getjTextField() {
        return jTextField;
    }

    public JPanel getjPanel() {
        return jPanel;
    }

    public JList getjList() {
        return jList;
    }
    
    /**!important:  Method for concat text Select
    * @param CONCAT_SEARCH concatenate items where setConcatSearch() is True
    */
    public void setConcatSearch(boolean CONCAT_SEARCH) {
        this.CONCAT_SEARCH = CONCAT_SEARCH;
    }

    /**!important:  Method whit setConcatSearch(true)
     * @param CHAR_SEPARATE is String for separate items where setConcatSearch() is True
     */
    public void setCharSeparate(String CHAR_SEPARATE) {
        this.CHAR_SEPARATE = CHAR_SEPARATE;
    }

    
    
}
