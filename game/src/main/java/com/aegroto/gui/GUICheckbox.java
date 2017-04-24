/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public class GUICheckbox extends GUIInteractiveElement{
    @Getter private boolean checked=false;
    //private GUIButton infoButton;
    //private GUIMessageBox infoBox;
    //private final Material mat;
    
    public GUICheckbox(Vector2f pos,Vector2f sc,AssetManager am,Node mn,ArrayList iGuiList) {
        aM=am;
        mN=mn;
        scale=sc;
        this.pos=pos;
        interactiveGUIsList=iGuiList;
        
        img=new GUIImage(this.pos,scale,"gui/checkbox.png",aM,mN);
    }
    
    public void setChecked(boolean checkd) {
        if(!checkd) {
            checked=false;
            img.setImage("gui/checkbox.png");
        } else {
            checked=true;
            img.setImage("gui/checkbox_checked.png");
        }        
    }

    @Override
    public void funcClicked(Vector2f mousePos) { }

    @Override
    public void funcLeft(Vector2f mousePos) {
        setChecked(!checked);
    }
    
    @Override
    public void activate(boolean state) {
        active=state;
        img.activate(state);
            if(state) {
                interactiveGUIsList.add(this);
            } else {
                interactiveGUIsList.remove(this);
            }
    }    

    @Override
    public void setScale(Vector2f scale) { 
        img.setScale(scale);
    }
}
