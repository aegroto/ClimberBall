/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import lombok.Setter;

/**
 *
 * @author lorenzo
 */
public class GUIText extends GUIElement{
    private final BitmapText text;
    //@Setter private Vector2f offset;
    
    private GUIText(Vector2f pos,String txt,BitmapFont font,Node mn) {
        mN=mn;
        this.pos=pos;
        
        text=new BitmapText(font,false);
        text.setLocalTranslation(this.pos.x, this.pos.y, 1);
        text.setText(txt);
        text.setColor(ColorRGBA.White);
        
        activate(false);
        //mN.attachChild(text);
    }
    
    public GUIText(Vector2f pos,String txt,float sizeFactor,BitmapFont font,Node mn) {
        this(pos,txt,font,mn);
        text.setSize(Helpers.getBaseTextSize()*sizeFactor);
        
        /*mN=mn;
        this.pos=pos;
        //offset=Vector2f.ZERO;
        
        text=new BitmapText(font,false);
        text.setLocalTranslation(this.pos.x, this.pos.y, 1);
        text.setText(txt);
        text.setSize(Helpers.getBaseTextSize()*sizeFactor);
        
        text.setColor(ColorRGBA.White);
                
        activate(false);*/
    } 
    
    @Override
    public void setPos(Vector2f newPos) {
        pos=newPos;
        Vector2f basePos=parentNode!=null?parentNode.getGlobalPos():new Vector2f(0,0);

        text.setLocalTranslation(
                basePos.x+pos.x,//+offset.x,
                basePos.y+pos.y,//+offset.y,
                text.getLocalTranslation().z);
    }
    
    public void setText(String newTxt) {
        text.setText(newTxt);
    }
    
    public void setSize(float size) {
        text.setSize(size);
    }
    
    public String getText() {
        return text.getText();
    }
    
    public void setZOffset(int z) {
        text.setLocalTranslation(text.getLocalTranslation().x, text.getLocalTranslation().y, z);
    }
    
    public BitmapText getBitmapText() {
        return text;
    }
    
    @Override
    public void centerX() {
        setPos(pos.setX(Coordinate2D.xConvert(.5f)-getBitmapText().getLineWidth()/2));
    }
    
    public float centerX(GUIElement container) {
        float xOffset=(container.getScale().x/2-getBitmapText().getLineWidth()/2);
        setPos(pos.set(
                /*container.getPos().x+*/xOffset,
                /*container.getPos().y*/0f));
        return xOffset;
    }
    
    public void centerY() {
        setPos(pos.setY(Coordinate2D.yConvert(.5f)-getBitmapText().getLineHeight()/2));
    }
 
    public float centerY(GUIElement container) {
        float yOffset=container.getScale().y-getBitmapText().getLineHeight()/2;
        setPos(pos.set(
                /*container.getPos().x*/0f,
                /*container.getPos().y+*/yOffset));        
        return yOffset;
    }
    
    public Vector2f center(GUIElement container) {
        Vector2f offset=new Vector2f(
                container.getScale().x/2-getBitmapText().getLineWidth()/2,
                container.getScale().y-getBitmapText().getLineHeight()/2);
        
        /*setPos(new Vector2f(
                container.getPos().x+offset.x,
                container.getPos().y+offset.y));   */
        setPos(offset);
        
        return offset;
    }
    
    @Override
    public void activate(boolean state) {
        active=state;
        
        if(active) {
            mN.attachChild(text);
        } else {
            mN.detachChild(text);
        }
    }

}
