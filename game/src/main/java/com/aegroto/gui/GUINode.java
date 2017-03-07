/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.jme3.math.Vector2f;
import com.aegroto.common.Coordinate2D;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public class GUINode<E extends GUIElement> extends GUIElement{
    @Getter protected ArrayList<E> elements=new ArrayList<>();
    
    /*public GUINode() {
        this.pos=new Vector2f(0,0);
        this.scale=new Coordinate2D(1f,1f).toVector();
    }*/
    
    public GUINode() {
        this.pos=new Vector2f(0,0);
        this.scale=new Coordinate2D(1f,1f).toVector();
    }
    
    public void attach(E element) {
        elements.add(element);
        element.setParentNode(this);
        
        //System.out.println("Attaching " + element + " to " + this + " " + element.getParentNode().getGlobalZOffset() + " " + element.getGlobalZOffset());
    }
    
    public void detach(E element) {
        elements.remove(element);
        element.setParentNode(null);
    }
    
    public E get(int id) {
        return elements.get(id);
    }
    
    @Override
    public void activate(boolean active) { 
        this.active=active;
        for(E element:elements) element.activate(active); 
    }
    
    @Override public void centerX() { for(E element:elements) element.centerX(); }  
    @Override public void update(float tpf) { 
        for(E element:elements) {
            if(element.isActive()) element.update(tpf);
        } 
    } 

    @Override public void setPos(Vector2f pos) { 
        this.pos=pos;
        for(E element:elements) element.setPos(element.getPos());
    }
    
    @Override public void setScale(Vector2f scale) { 
        this.scale=scale;
        for(E element:elements) element.setScale(new Vector2f(this.scale.x*scale.x,this.scale.y*scale.y));  
    }    
    
    public Vector2f getGlobalPos() {
        if(parentNode!=null) return this.pos.add(parentNode.getGlobalPos());
        else return this.pos;
    }
}
