/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.anim;

import com.aegroto.climberball.entity.Entity;
import com.aegroto.common.Helpers;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import lombok.Setter;

/**
 *
 * @author lorenzo
 */
public class EntityAnimationTransition extends EntityAnimation {
    protected Entity targetEntity = null;
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
    
    public EntityAnimationTransition(Entity entity, Vector3f startPos, Entity targetEntity, float speed) {
        this(entity, startPos, targetEntity.getPos(), speed);
        this.targetEntity = targetEntity;
    }
        
    @Override
    public void onUpdate() {
        if(targetEntity != null) 
            targetPos = targetEntity.getGeom().getWorldTranslation();
        
        //System.out.println(currentPos + "\t\t" + targetPos + "\t\t" + currentPos.distance(targetPos));
        //System.out.println(currentPos.distance(targetPos) + " " + speed);
        
        if(currentPos.distance(targetPos) > speed) {
            float addX = 0f,addY = 0f,
                  xDist = FastMath.abs(currentPos.x - targetPos.x),
                  yDist = FastMath.abs(currentPos.y - targetPos.y),
                  speedBalance = xDist / yDist;
            
            if(speedBalance > .9f) speedBalance = .9f;
            
            if(xDist > speed && currentPos.x > targetPos.x) {
                addX = -speed;
            } else {
                addX = speed;
            }
            
            if(yDist > speed && currentPos.y > targetPos.y) {
                addY = -speed;
            } else {
                addY = speed;
            }
            
            currentPos.addLocal(addX * speedBalance, addY * (1-speedBalance), 0);
        } else {
            finished = true;
            onFinish();
            currentPos = targetPos;
        }
        
        if(entity != null)
            entity.setGlobalPos(currentPos);
    }        
}
