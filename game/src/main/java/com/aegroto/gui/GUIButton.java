/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.material.RenderState;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author Lorenzo
 */
public abstract class GUIButton extends GUIInteractiveElement {
    @Getter protected GUIMultipleLineText text;
    protected float textSize;
    
    //protected Vector2f textOffset;//,initialPos;
    protected BitmapFont gF;
    
    protected String imagePath,imagePressedPath;
    //private boolean pressed = false;
    
    protected GUIButton() { }

    public GUIButton(Vector2f pos,Vector2f sc,String txt,BitmapFont gf,
                     AssetManager am,Node mn,ArrayList iGuiList) {
        this(pos,sc,txt,"gui/buttons.png",gf,am,mn,iGuiList);
    }    
    
    public GUIButton(Vector2f pos,Vector2f sc,String txt,String imagePath,float textSizeFactor,
            BitmapFont gF,AssetManager am,Node mn,ArrayList iGuiList) {       
        this(pos,sc,txt,imagePath,gF,am,mn,iGuiList);
        
        textSize=(textSizeFactor);        
        text.setSize(Helpers.getBaseTextSize()*textSize);
        
        text.center(img);
    }
    public GUIButton(Vector2f pos,Vector2f sc,String txt,String imagePath,
                    BitmapFont gF,AssetManager am,Node mn,ArrayList iGuiList) {
        this.aM=am;
        this.mN=mn;
        this.gF=gF;
        this.active=false;
        this.scale=sc;
        
        this.imagePath=imagePath;
        this.imagePressedPath=imagePath.split("\\.")[0]+"_pressed.png";
        
        this.pos=pos;
        
        img=new GUIImage(Vector2f.ZERO,scale,imagePath,aM,mN);
        textSize=(scale.y*0.0065f); 
         
        text=new GUIMultipleLineText(Vector2f.ZERO,txt,textSize,scale.y/4,gF,mN);                      
        text.center(img);
        
        attach(img);
        img.attach(text);   
        
        interactiveGUIsList=iGuiList;
        

    }
    
    public GUIButton(Vector2f pos,Vector2f sc,String txt,float textSizeFactor,
            BitmapFont gF,AssetManager am,Node mn,ArrayList iGuiList) {       
        this(pos,sc,txt,gF,am,mn,iGuiList); 
        
        textSize=Helpers.getBaseTextSize()*textSizeFactor;
        text.setSize(textSize);
        
        text.center(img);
        //textOffset=text.getLines().get(0).center(img);
    }
    
    public void setText(String txt) {
        text.setText(txt, gF, mN);
        text.setSize(textSize);
        
        text.center(img);
    }
    
    @Override
    public void setPos(Vector2f pos) {
        super.setPos(pos);    
        //Vector2f basePos=parentNode!=null?parentNode.getGlobalPos():new Vector2f(0,0);
        
        //img.setPos(img.getPos());
        text.center(img);
    }
    
    public void setZOffset(int newZ) {
        img.setZOffset(newZ);
        text.setZOffset(newZ+1);
    }
    
    public abstract void execFunction();
    
    @Override
    public void funcClicked(Vector2f mousePos) {
        turnPressImg(true);
    }
    
    @Override
    public void funcLeft(Vector2f mousePos) {
        turnPressImg(false);
        execFunction();
    }
    
    public void turnPressImg(boolean pressed) {
        if(isActive()) {
            if(pressed) {
                img.getPicture().setImage(aM,imagePressedPath,false);
                img.getPicture().getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            } else {
                img.getPicture().setImage(aM,imagePath,false);
                img.getPicture().getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            }
        }
    }
    
    public float centerX(GUIElement container) {
        float xOffset=container.getScale().x/2-scale.x/2;
        img.setPos(new Vector2f(container.getPos().x+xOffset,pos.y)); 
        return xOffset;
    }
    
    @Override
    public void centerX() {
        float xOffset=scale.x/2-scale.x/2;
        setPos(new Vector2f(scale.x+xOffset,pos.y));
    }
    
    @Override
    public void activate(boolean state) {        
        active=state;
        super.activate(active);
        
        //img.activate(state);
        //text.activate(state);
        
        turnPressImg(false);
        if(state) {
            if(!interactiveGUIsList.contains(this)) interactiveGUIsList.add(this);
        } else {
            interactiveGUIsList.remove(this);
        }
    }    
    
    @Override
    public String toString() {
        return "GUIButton at index "+interactiveGUIsList.indexOf(this);
    }
}
