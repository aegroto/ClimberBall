/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.aegroto.common.Coordinate2D;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.Setter;

/**
 *
 * @author andrea
 */
public class GUIKeyboard extends GUIElement{
    protected HashMap<String,GUIKeyboardButton> buttons=new HashMap<>();
    @Setter protected GUITextBar activeTextBar;
    
    private final GUIImage background;
    
    public abstract class GUIKeyboardButton extends GUIButton {
        
        public GUIKeyboardButton(Vector2f pos,Vector2f sc,String txt,BitmapFont gF,
                     AssetManager am,Node mn,ArrayList iGuiList) {
            super(pos,sc,txt,gF,am,mn,iGuiList);
            setZOffset(6);
        }
    }
    
    public GUIKeyboard(AssetManager aM,BitmapFont gF,Node mN,ArrayList iGuiList) {
        this.aM=aM;
        this.mN=mN;
        
        background=new GUIImage(new Coordinate2D(0f,0f).toVector(),
                                new Coordinate2D(1f,.3f).toVector(),
                                "keyboard_background.png",aM,mN);
        background.setZOffset(5);
        
        Vector2f buttonSize=new Coordinate2D(.085f,.06f).toVector();
        
        String[] letters={"1","2","3","4","5","6","7","8","9","0","NL",
                         "q","w","e","r","t","y","u","i","o","p","NL",
                         "a","s","d","f","g","h","j","k","l","<-","NL",
                         "z","x","c","v","b","n","m"," "};
        int index=0,line=0;
        float xOffset=.02f;
        
        for(final String letter:letters) {
            if(letter.equals("NL")) {
                line++;
                index=0;
                xOffset+=.008f;               
            } else if(letter.equals("<-")) {
                buttons.put(letter,new GUIKeyboardButton(new Coordinate2D(
                                              xOffset+(.095f*index),
                                              .225f-(.07f*line)).toVector(), 
                                              new Vector2f(buttonSize.x*1.1f,buttonSize.y),
                                              letter,gF,aM,mN,iGuiList) {
                @Override
                public void execFunction() {
                    activeTextBar.deleteLastChar();
                }
                });
            } else if(letter.equals(" ")) {
                buttons.put(letter,new GUIKeyboardButton(new Coordinate2D(
                                              xOffset+(.095f*index),
                                              .225f-(.07f*line)).toVector(), 
                                              new Vector2f(buttonSize.x*3,buttonSize.y),
                                              letter,gF,aM,mN,iGuiList) {
                @Override
                public void execFunction() {
                    activeTextBar.addChar(letter);
                }
                });
            } else { buttons.put(letter, new GUIKeyboardButton(new Coordinate2D(
                                              xOffset+(.095f*index),
                                              .225f-(.07f*line)).toVector(), 
                                              buttonSize,
                                              letter,gF,aM,mN,iGuiList) {
                @Override
                public void execFunction() {
                    activeTextBar.addChar(letter);
                }
                });
                index++;
            }
        }
        
        //activate(false);
    }
    
    @Override
    public void activate(boolean active) {
        for(GUIKeyboardButton button:buttons.values()) button.activate(active);
        background.activate(active);
        if(!active) activeTextBar=null;
    }   

    /*@Override
    public void setPos(Vector2f pos) { debugPrint("SetPos not supported on GUIKeyboard"); }
    @Override
    public Vector2f getPos() { debugPrint("GetPos not supported on GUIKeyboard"); return null;}
    @Override
    public void setScale(Vector2f scale) { debugPrint("SetScale not supported on GUIKeyboard"); }
    @Override
    public Vector2f getScale() { debugPrint("GetScale not supported on GUIKeyboard"); return null; }*/
}
