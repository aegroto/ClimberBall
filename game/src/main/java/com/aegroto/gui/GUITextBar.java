/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author andrea
 */
public class GUITextBar extends GUIInteractiveElement{
    protected GUIText textOnBar;
    //protected GUIButton activeSpace;
    protected boolean hidden,parseNumbers,showPadIfActive;
    @Getter protected boolean isActiveTextBar=false;
    @Getter protected String realText;
    
    protected BitmapFont gF;
    
    public GUITextBar(Vector2f pos,Vector2f sc,boolean hidden,boolean parseNumbers,boolean showPadIfActive,
                      AssetManager aM,BitmapFont gF,Node mN,ArrayList iGuiList) {
        this.aM=aM;
        this.gF=gF;
        this.mN=mN;
        this.interactiveGUIsList=iGuiList;
        
        this.scale=sc;
        this.pos=pos;

        float textSize=scale.y/150;
        textOnBar=new GUIText(Vector2f.ZERO,"",textSize,gF,mN);

        //textOnBar.setZOffset(1);
        setText("");
        
        img=new GUIImage(this.pos,scale,"gui/buttons.png",aM,mN);
        img.attach(textOnBar);
        
        //img.activate(false);

        this.hidden=hidden;
        realText="";
        
        this.parseNumbers=parseNumbers;
        this.showPadIfActive=showPadIfActive;
        
        //interactiveGUIsList.add(this);
        //activate(true);
    }
    
    public void setText(String newText) {
        realText=newText;
        String shownText;
        
        if(!hidden) shownText=realText;
        else shownText=censureString(realText);
               
        if(isActiveTextBar) shownText+="_";
        
        textOnBar.setText(shownText);
        
        if(newText.isEmpty()) onEmptyText();                
        textOnBar.center(this);
    }
    
    /*public String getRealText() {
        return realText;
    }*/
    
    private String censureString(String str) {
        return str.replaceAll(".", "+");
    }
    
    public void addChar(String letter) {
        if(parseNumbers) {
            try {
                Integer.parseInt(letter);
            } catch(NumberFormatException e) { return; }
        }
        
        if(textOnBar.getText().length()<20) {
            setText(realText+letter);
        }
        onAddChar(letter);       
    }
    
    public void setActiveTextBar(boolean state) {
        isActiveTextBar=state;
        if(state) {
            textOnBar.setText(realText+"_");
            textOnBar.center(this);
        } else {
            textOnBar.setText(textOnBar.getText().replace("_", ""));
            if(realText.isEmpty()) onEmptyText();
            onIsNoMoreActiveTextBar();
        }
    }
    
    public void deleteLastChar() {
        if(!realText.isEmpty()) {
            realText=realText.substring(0, realText.length()-1);
            setText(realText);
        } else onEmptyText();
        onDeleteLastChar();
    }
    
    /*public boolean isActive() {
        return active;
    }*/
    
    @Override
    public void setPos(Vector2f pos) {
        this.pos=pos;
        Vector2f basePos=parentNode!=null?parentNode.getPos():new Vector2f(0,0);
        
        img.setPos(basePos.add(pos));
        textOnBar.setPos(basePos.add(pos));
        
        textOnBar.center(img);
    }
    
    @Override
    public String toString() {
        return "GUITextBar at index "+interactiveGUIsList.indexOf(this);
    }
    
    @Override
    public void activate(boolean state) {
        active=state;
        img.activate(state);
        textOnBar.activate(state);
        if(state) {
            interactiveGUIsList.add(this);
        } else {
            interactiveGUIsList.remove(this);
        }
    }
    
    protected void onEmptyText() { }
    
    protected void onAddChar(String letter) { }
    
    protected void onDeleteLastChar() { }
    
    protected void onIsNoMoreActiveTextBar() { }

    @Override
    public void funcClicked(Vector2f mousePos) {
        
    }

    @Override
    public void funcLeft(Vector2f mousePos) {
        //if(showPadIfActive) showPad();
    }
}
