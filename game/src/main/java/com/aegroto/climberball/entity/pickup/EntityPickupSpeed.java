/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.pickup;

import com.aegroto.climberball.entity.EntityBall;
import com.aegroto.climberball.entity.anim.EntityAnimationTransition;
import com.aegroto.climberball.skin.Skin;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author lorenzo
 */
public class EntityPickupSpeed extends EntityPickup {
    protected static float SPEED_BOOST = 3.0f;
    
    public EntityPickupSpeed(Node rootNode,Vector3f spawnPos, Skin skin, AssetManager assetManager) {
        super(rootNode,spawnPos,skin,assetManager);
        
        geom.setMaterial(skin.getSpeedPickupMaterial());
        
        node.attachChild(geom);
    }

    @Override
    public void onPick(EntityBall ball) {        
        ball.applyEffect(EntityBall.EFFECT_SPEED_PICKUP_BOOST);
        
        ball.setXSpeed(ball.getXSpeed() + SPEED_BOOST);
        picked = true;
        destroy();
    } 

    @Override
    public void destroy() {
        node.removeFromParent();
        destroyed = true;
    }    

    @Override
    public String getName() {
        return "Speed boost";
    }
}
