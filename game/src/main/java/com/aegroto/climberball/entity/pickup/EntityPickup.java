/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.pickup;

import com.aegroto.climberball.entity.Entity;
import com.aegroto.climberball.entity.EntityBall;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author lorenzo
 */
public abstract class EntityPickup extends Entity {
    protected final float rotationSpeed;
    protected final Vector3f floatingDirection;
    
    protected final float[] rotation = {0f, 0f, 0f};
    
    public EntityPickup(Node terrainNode,Vector3f spawnPos,AssetManager assetManager) {
        node=new Node();
        
        this.terrainNode=terrainNode;
        
        this.terrainNode.attachChild(node);
        
        this.floatingDirection=new Vector3f(
                Coordinate2D.xConvert(FastMath.nextRandomInt(-1, 1) / 5000f), 
                Coordinate2D.yConvert(FastMath.nextRandomInt(-1, 1) / 5000f),
                0f);
        
        this.rotationSpeed=FastMath.QUARTER_PI/256f * FastMath.nextRandomFloat() * FastMath.nextRandomInt(-1, 1);
        
        this.size = new Vector2f(Helpers.getPickupSize(),Helpers.getPickupSize());
        
        geom=new Geometry("Pickup Geometry",new Quad(size.x, size.y));
        
        node.setLocalTranslation(spawnPos.add(0,Coordinate2D.yConvert(.2f + FastMath.nextRandomInt(0, 30) / 100f),0f));
    }

    //private Geometry testQuadMin,testQuadMax;
    
    public abstract void onPick(EntityBall ball);
    
    public boolean checkForBarrage(float xBarrage) {
        if(node.getLocalTranslation().x < xBarrage) {    
            destroyed = true;
            return true;
        }
        
        return false;
    }
    
    @Override
    public void update(float tpf) {
        rotation[2]-=rotationSpeed;
        
        //geom.setLocalRotation(new Quaternion().fromAngles(rotation));
        //geom.setLocalTranslation(geom.getLocalTranslation().add(floatingDirection));
        
        if(rotation[2] <= -FastMath.TWO_PI) rotation[2]=0f;
    }
    
    public Vector2f getPickupZoneMin() {
        Vector3f zoneWithRot=geom.getLocalRotation().mult(new Vector3f(
                node.getLocalTranslation().x + terrainNode.getLocalTranslation().x,
                node.getLocalTranslation().y + terrainNode.getLocalTranslation().y,
                0f
        ));
        
        return new Vector2f(zoneWithRot.x, zoneWithRot.y);
    }
    
    public Vector2f getPickupZoneMax() {
        Vector3f zoneWithRot=geom.getLocalRotation().mult(new Vector3f(
                getPickupZoneMin().x + size.x,
                getPickupZoneMin().y + size.y,
                0f
        ));
        return new Vector2f(zoneWithRot.x, zoneWithRot.y);
    }
}
