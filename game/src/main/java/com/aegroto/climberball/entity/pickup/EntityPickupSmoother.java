/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.pickup;

import com.aegroto.climberball.entity.EntityBall;
import com.aegroto.climberball.skin.Skin;
import com.aegroto.climberball.state.EnvironmentAppState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author lorenzo
 */
public class EntityPickupSmoother extends EntityPickup {
    private final EnvironmentAppState environmentAppState;
    
    public EntityPickupSmoother(Node rootNode,Vector3f spawnPos, EnvironmentAppState environmentAppState, Skin skin, AssetManager assetManager) {
        super(rootNode,spawnPos,skin,assetManager);
        
        this.environmentAppState = environmentAppState;
        
        geom.setMaterial(skin.getSmootherPickupMaterial());        
        node.attachChild(geom);
    }
    @Override
    public void onPick(EntityBall ball) {
        environmentAppState.headQueueChunkGeneration(10, 0);
        
        picked = true;
        destroy();
    }

    @Override
    public String getName() {
        return "Smoother way";
    }

    @Override
    public void destroy() {
        node.removeFromParent();
        destroyed = true;
    }
    
}
