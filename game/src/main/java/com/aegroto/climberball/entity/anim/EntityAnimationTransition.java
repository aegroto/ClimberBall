/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.anim;

import com.aegroto.climberball.entity.Entity;
import com.aegroto.common.Helpers;
import com.jme3.math.Vector3f;

/**
 *
 * @author lorenzo
 */
public class EntityAnimationTransition extends EntityAnimation {
    protected Vector3f currentPos, targetPos;
    protected float speed, speedVariation = 1f;
    
    public EntityAnimationTransition(Entity entity, Vector3f startPos, Vector3f targetPos, float speed) {
        super(entity);
        this.currentPos = startPos;
        this.targetPos = targetPos;
        this.speed = speed;
    }
    
    public EntityAnimationTransition(Entity entity, Vector3f startPos, Vector3f targetPos, float speed, float speedVariation) {
        this(entity, startPos, targetPos, speed);
        this.speedVariation = speedVariation;
    }
        
    @Override
    public void onUpdate() {
        if(currentPos.distance(targetPos) > Helpers.getEpsilon()) {
           
        } else {
            finished = true;
            currentPos = targetPos;
        }
        
        entity.setPos(currentPos);
    }    
}
