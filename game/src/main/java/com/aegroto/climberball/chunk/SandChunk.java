/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.chunk;

import com.aegroto.climberball.entity.pickup.EntityPickup;
import com.aegroto.climberball.skin.Skin;
import com.aegroto.common.Helpers;
import static com.aegroto.common.Helpers.safeAttachChild;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author lorenzo
 */
public class SandChunk extends TerrainChunk {
    
    public SandChunk(Vector3f startPoint, Node rootNode, Skin environmentSkin,Material mat) {
        super(startPoint, rootNode, environmentSkin, mat);    
        this.surfaceType=2;
        
        surfaceGeom.setMaterial(this.skin.getSandSkin().getMaterial());
        
        safeAttachChild(node,surfaceGeom);
    }

    @Override
    public float elaborateSpeedOnSurface(float speed,byte ballForm) {
        return super.internalElaborateSpeedOnSurface(speed, ballForm, 
                (byte) 1,
                (byte) 3,
                (byte) 0);
    }    
}
