/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity.pickup;

import com.aegroto.climberball.skin.Skin;

/**
 *
 * @author lorenzo
 */
public class EntityPickupSpeed extends EntityPickup {
    
    public EntityPickupSpeed(Skin skin) {
        super();
        
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
