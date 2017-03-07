/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui.menu;


import com.aegroto.gui.GUIElement;
import com.aegroto.gui.GUINode;
import lombok.Getter;
import com.aegroto.gui.states.GuiAppState;

/**
 *
 * @author lorenzo
 */
public abstract class Menu {
    
    protected GuiAppState guiAppState;
    @Getter protected GUINode menuNode;
    
    public Menu() {
        this.menuNode=new GUINode();
    }   
    
    public void onAttach(GuiAppState guiAppState) {
        this.guiAppState=guiAppState;
    }
    
    public void onDetach() {
        this.guiAppState=null;
    }
    
    public void attachElement(GUIElement element) {
        menuNode.attach(element);
    }
    
    public void detachElement(GUIElement element) {
        menuNode.detach(element);
        element.activate(false);
    }
    
    public void enableGUI() {
        menuNode.activate(true);
    }
    
    public void disableGUI() {
        menuNode.activate(false);
    }
    
    public void update(float tpf) {
        menuNode.update(tpf);
    }
}
