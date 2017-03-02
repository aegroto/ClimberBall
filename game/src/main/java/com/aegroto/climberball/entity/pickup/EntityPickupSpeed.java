/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.pickup;

import com.aegroto.climberball.skin.Skin;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author lorenzo
 */
public class EntityPickupSpeed extends EntityPickup {
    
    public EntityPickupSpeed(Node rootNode,Vector3f spawnPos, Skin skin) {
        super(rootNode,spawnPos);
        
        geom.setMaterial(skin.getSpeedPickupMaterial());
        
        node.attachChild(geom);
    }

    @Override
    public void onPick() { 
    }

    @Override
    public void update(float tpf) { }

    @Override
    public void destroy() { }
    
}
