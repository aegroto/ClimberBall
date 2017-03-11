/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.anim;

import com.aegroto.climberball.entity.Entity;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public abstract class EntityAnimation {
    @Getter protected boolean finished = false;
    protected final Entity entity;
    
    public EntityAnimation(Entity entity) {
        this.entity = entity;
    }
    
    public abstract void onUpdate();
}
