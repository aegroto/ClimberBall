/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui;

import com.aegroto.common.Coordinate2D;
import com.jme3.asset.AssetManager;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

/**
 *
 * @author Lorenzo
 */
public class GUIImage extends GUINode {
    private final Picture img;
    private String picPath;
    
    public GUIImage(Vector2f pos,Vector2f sc,String pic,AssetManager am,Node mn) {
        aM=am;
        mN=mn;
        
        scale=sc;
        img = new Picture("Image");
        this.pos=pos;
        
        img.setImage(aM,pic,false);
        img.setWidth(scale.x);
        img.setHeight(scale.y);       
        img.setLocalTranslation(pos.x, pos.y, 0);
        
        img.getMaterial().getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        
        picPath=pic;
        //img.setQueueBucket(Bucket.Transparent);
        //mN.attachChild(img);        
    }
    
    public void mirrorVertically() {
        img.setLocalRotation(new Quaternion().fromAngles(0f,0f,FastMath.PI)); 
        img.setLocalTranslation(img.getLocalTranslation().add(
                getScale().x,getScale().y,0f));        
    }
    
    @Override
    public void setScale(Vector2f sc) {
        scale=sc;
        img.setWidth(scale.x);
        img.setHeight(scale.y);
    }
    
    public void setImage(String path) {
        picPath=path;
        img.setImage(aM, picPath, false);
    }
    
    public void setZOffset(int zOffset) {
        img.setLocalTranslation(getGlobalPos().x, getGlobalPos().y, zOffset);
    }
    
    public float getZOffset() {
        return img.getLocalTranslation().z;
    }
    
    public void setName(String name) {
        img.setName(name);
    }
    
    @Override
    public void setPos(Vector2f newPos) {
        super.setPos(pos);
        
        Vector2f basePos=parentNode!=null?parentNode.getPos():new Vector2f(0,0);
        img.setLocalTranslation(
                basePos.x+newPos.x,
                basePos.y+newPos.y,
                img.getLocalTranslation().z);
        pos=newPos;
    }
    
    public void setPos(float x,float y) {        
        setPos(new Vector2f(x,y));
        /*img.setLocalTranslation(x,y,img.getLocalTranslation().z);
        pos=new Vector2f(x,y);  */     
    }
    
    public Picture getPicture() {
        return img;
    }
    
    public Vector2f getPos() {
        return pos;
    }
    
    public Vector2f getScale() {
        return scale;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public float centerY(GUIElement container) {
        float yOffset=container.getScale().y/2-scale.y/2;
        setPos(new Vector2f(
                /*container.getPos().x*/pos.x,
                /*container.getPos().y+*/yOffset));        
        return yOffset;
    }
    
    @Override
    public void centerX() {
        super.centerX();
        
        setPos(new Coordinate2D(
            .5f,
            0f
        ).toVector().add(new Vector2f(-scale.x/2,pos.y)));
    }

    @Override
    public void activate(boolean state) {
        active=state;
        super.activate(active);
        
        if(state) {
            mN.attachChild(img);
        } else {
            mN.detachChild(img);
        }
    }
}

