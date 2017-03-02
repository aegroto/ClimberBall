/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.pickup;

import com.aegroto.climberball.entity.Entity;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author lorenzo
 */
public abstract class EntityPickup extends Entity {
    protected final float rotationSpeed = FastMath.QUARTER_PI/64f,
                          floatationSpeed;
    
    protected final float[] rotation = {0f, 0f, 0f};
    
    public EntityPickup(Node rootNode,Vector3f spawnPos) {
        node=new Node();
        this.rootNode=rootNode;
        
        this.rootNode.attachChild(node);
        
        this.floatationSpeed=Coordinate2D.yConvert(FastMath.nextRandomInt(-1, 1) / 100f);
        
        geom=new Geometry("Pickup Geometry",new Quad(Helpers.getBallSize(),Helpers.getBallSize()));     
        geom.setLocalTranslation(spawnPos.add(0,Coordinate2D.yConvert(.2f + FastMath.nextRandomInt(0, 30) / 100f),0f));
    }

    public abstract void onPick();
    
    @Override
    public void update(float tpf) {
        rotation[2]-=rotationSpeed;
        
        node.setLocalRotation(new Quaternion().fromAngles(rotation));
        
        if(rotation[2] <= -FastMath.TWO_PI) rotation[2]=0f;
    }
}
