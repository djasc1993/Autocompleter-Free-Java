package org.autocompletor.free;

import java.awt.Color;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author @djasc1993
 */
public class Theme {
    
   public Color backgroundPanel;
   public Color colorTextItem;

    public Theme(Color backgroundPanel, Color colorTextItem) {
        this.backgroundPanel = backgroundPanel;
        this.colorTextItem = colorTextItem;
    }

    void setColorBackground(JPanel jPanel, JList jList) {
        jPanel.setBackground(this.backgroundPanel);
        jList.setBackground(this.backgroundPanel);
        jList.setForeground(this.colorTextItem);
    }
   
    
}
