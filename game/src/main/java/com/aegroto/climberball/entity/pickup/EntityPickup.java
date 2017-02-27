/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.pickup;

import com.aegroto.climberball.entity.Entity;
import com.aegroto.common.Helpers;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author lorenzo
 */
public abstract class EntityPickup extends Entity {

    public EntityPickup() {
        node=new Node();
        
        geom=new Geometry("Pickup Geometry",new Quad(Helpers.getBallSize(),Helpers.getBallSize()));     
        geom.setLocalTranslation(-Helpers.getBallSize()/2f,-Helpers.getBallSize()/2f,1f);
    }

    public abstract void onPick();
}
