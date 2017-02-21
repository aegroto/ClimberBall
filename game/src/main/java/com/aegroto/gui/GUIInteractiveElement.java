/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.aegroto.common.Helpers;
import com.jme3.math.Vector2f;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public abstract class GUIInteractiveElement extends GUINode {
    @Getter protected GUIImage img;
    protected ArrayList<GUIInteractiveElement> interactiveGUIsList;
    
    /*@Override
    public void setPos(Vector2f pos) {
        img.setPos(pos.x, pos.y);
        this.pos=pos;
    }    
    
    @Override
    public Vector2f getScale() {
        return scale;
    }
      
    @Override
    public Vector2f getPos() {
        return pos;
    }*/   
        
    public boolean isClickedOn(Vector2f point) {
        return Helpers.pointInArea(point,
                getGlobalPos(),
                getGlobalPos().add(scale));
    }
    
    public abstract void funcClicked(Vector2f mousePos);   
    public abstract void funcLeft(Vector2f mousePos);
    
    public void funcClickedContinously(Vector2f mousePos) {
        
    }  
}
