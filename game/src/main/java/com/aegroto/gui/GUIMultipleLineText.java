/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.jme3.font.BitmapFont;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 *
 * @author andrea
 */
public class GUIMultipleLineText extends GUINode<GUIText> {
    //@Getter private ArrayList<GUIText> lines=new ArrayList<>();
    //private Vector2f initialPos;
    private final float sizeFactor,lineDistance;
    
    public GUIMultipleLineText(Vector2f pos,String txt,float sizeFactor,float lineDistance,BitmapFont font,Node mn) {
        mN=mn;
        this.pos=pos;
        //initialPos=this.pos;
        
        active=false;
        this.sizeFactor=sizeFactor;
        this.lineDistance=lineDistance;
        
        String[] txt_s=txt.split("\n");
        for(String text:txt_s) {
            attach(new GUIText(new Vector2f(0f,-lineDistance*elements.size()),text,sizeFactor,font,mn));
        }     
    }
    
    public ArrayList<GUIText> getLines() {
        return elements;
    }
    
    public void setColor(ColorRGBA newColor) {
        for(GUIText line:elements) {
            line.getBitmapText().setColor(newColor);
        }
    }
    
    public void setText(String txt,BitmapFont font,Node mn) {
        boolean wasActive=active;
        
        activate(false);
        elements.clear();
        
        String[] txt_s=txt.split("\n");
        for(String text:txt_s) {
            attach(new GUIText(new Vector2f(0,-lineDistance*elements.size()),text,sizeFactor,font,mn));
        }   
        
        if(wasActive) activate(wasActive);
    }
    
    @Override public void setPos(Vector2f pos) { 
        this.pos=pos;
        for(GUIText element:elements) element.setPos(element.getPos());
    }
    
    /*@Override
    public void setPos(Vector2f newPos) {
        pos=newPos;
        Vector2f basePos=parentNode!=null?parentNode.getGlobalPos():new Vector2f(0,0);
        
        for(GUIText line:elements) {
            //line.setPos(pos);
            line.setPos(basePos.add(pos));
            //line.setPos(basePos.add(new Vector2f(0f,-lineDistance*(elements.indexOf(line)))));
        }
    }*/
    
    /*public void setOffset(Vector2f offset) { 
        for(GUIText line:lines) {
            line.setOffset(offset);
        }        
    }*/
    
    public void setSize(float size) {
        for(GUIText line:elements) {
            line.getBitmapText().setSize(size);
        }        
    }
    
    public void setZOffset(int zOffset) {
        for(GUIText line:elements) {
            line.setZOffset(zOffset);
        }
    }
    
    @Override
    public void centerX() {
        for(GUIText line:elements) {
            line.centerX();
        }
    }
    
    public void centerX(GUIElement container) {
        for(GUIText line:elements) {
            line.centerX(container);
        }
    }
    
    public void centerY() {
        for(GUIText line:elements) {
            line.centerY();
        }
    }
 
    public void centerY(GUIElement container) {
        for(GUIText line:elements) {
            line.centerY(container);
            line.setPos(line.getPos().add(new Vector2f(
                    0f,
                    -lineDistance*(elements.indexOf(line))))); 
        }
    }
    
    public void center(GUIElement container) {
        for(GUIText line:elements) {
            line.center(container);
            line.setPos(line.getPos().add(new Vector2f(0f,-lineDistance*(elements.indexOf(line)))));
        }
        //centerX(container);
        //centerY(container);
    }    
    
    @Override
    public void activate(boolean state) {
        active=state;
        
        for(GUIText line:elements) {
            line.activate(active);
        }
    }
    
}
