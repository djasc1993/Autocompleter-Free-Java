/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.autocompletor.free;

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

/**
 *
 * @author @djasc1993
 */
public class Listeners implements ActionListener, FocusListener, KeyListener, MouseListener, ComponentListener {
    private final AutoCompleter context;
    private Object current_item = "";
    
    public Listeners(AutoCompleter context) {
        this.context = context;
    }

    @Override
    public final void componentResized(ComponentEvent ce) {
        this.context.getjList().setSize((int) this.context.getjTextField().getSize().getWidth(), this.context.NUMBER_OF_ITEMS * this.context.CELL_HEIGHT);
        this.context.getjPanel().setSize((int) this.context.getjTextField().getSize().getWidth(), this.context.NUMBER_OF_ITEMS * this.context.CELL_HEIGHT + 6);

    }

    @Override
    public final void componentMoved(ComponentEvent ce) {
        double xPointOfTextField = this.context.getjTextField().getLocation().getX();
        double yPointOfTextField = this.context.getjTextField().getLocation().getY();

        double heightOfTextField = this.context.getjTextField().getSize().getHeight();

        int x = (int) Math.ceil(xPointOfTextField);
        int y = (int) Math.ceil(yPointOfTextField + heightOfTextField);

        this.context.getjPanel().setLocation((int) (x), (int) (y));

        this.context.getjList().setLocation(this.context.PANEL_BORDER_SIZE, this.context.PANEL_BORDER_SIZE);

    }

    @Override
    public final void componentShown(ComponentEvent ce) {
    }

    @Override
    public final void componentHidden(ComponentEvent ce) {
        this.context.getjPanel().setVisible(false);
    }

    @Override
    public final void mouseClicked(MouseEvent me) {
        int indexOfTheItemClicked = me.getY() / this.context.CELL_HEIGHT;
        this.current_item = this.context.getItemFromResultAtIndex(indexOfTheItemClicked);
        this.context.selectedAValue(this.current_item);
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
        this.current_item = this.context.getjList().getSelectedValue();
        this.context.selectedAValue(this.current_item);
    }

    @Override
    public final void keyTyped(KeyEvent ke) {
    }

    @Override
    public final void keyPressed(KeyEvent ke) {
    }

    @Override
    public final void keyReleased(KeyEvent ke) {
        switch (ke.getExtendedKeyCode()) {
            case 38:
                //up key
                if (this.context.getjList().getSelectedIndex() > 0) {
                    this.context.getjList().setSelectedIndex(this.context.getjList().getSelectedIndex() - 1);
                }
                break;
            case 40:
                //down key
                if (this.context.getjList().getSelectedIndex() < this.context.getjList().getLastVisibleIndex()) {
                    this.context.getjList().setSelectedIndex(this.context.getjList().getSelectedIndex() + 1);
                }
                break;
            case 10:
                //down key Enter
                 //this.context.getjPanel().setVisible(false);
                break;
            default:
                this.context.updateTheJListValue();
                break;
        }

    }

    @Override
    public final void focusGained(FocusEvent fe) {
        if(!current_item.toString().equals(this.context.getjTextField().getText()))
            this.context.updateTheJListValue();
    }

    @Override
    public final void focusLost(FocusEvent fe) {
        this.context.getjPanel().setVisible(false);

    }
}
