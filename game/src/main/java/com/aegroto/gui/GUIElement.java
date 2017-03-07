/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author lorenzo
 */
public abstract class GUIElement {
    protected AssetManager aM;
    protected Node mN;
    @Getter protected boolean active=false;
    @Getter protected Vector2f scale,pos;
    @Getter protected GUINode parentNode; 
    @Getter @Setter protected Object userObject;
    @Getter @Setter protected float zOffset = 0;
    
    public abstract void activate(boolean active);
    
    public void centerX() { }  
    public void update(float tpf) { }
    
    public void setParentNode(GUINode parentNode) {
        this.parentNode=parentNode;
        if(parentNode!=null) {
            setPos(pos);
            setZOffset(zOffset);
        }    
    }
    
    public float getGlobalZOffset() {
        float parentGlobalZOffset = this.getParentNode() == null ? 0 : this.getParentNode().getGlobalZOffset();
        
        //System.out.println("Called getGlobalZOffset on "+this.getClass().getSimpleName()+" ("+ this.getParentNode() + ") = " + zOffset + parentGlobalZOffset);
        return zOffset + parentGlobalZOffset;
    }
    
    public void setPos(Vector2f pos) { System.err.println("setPos not supported on "+getClass().getCanonicalName()); }
    public void setScale(Vector2f scale) { System.err.println("setScale not supported on "+getClass().getCanonicalName()); }
}
