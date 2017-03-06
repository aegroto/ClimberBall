/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity;

import com.aegroto.climberball.skin.Skin;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public abstract class Entity {
    protected AssetManager assetManager;
    protected Node terrainNode;
    
    @Getter protected Node node;
    @Getter protected Geometry geom;
    @Getter protected Material material;    
    
    @Getter protected boolean destroyed=false;
    
    @Getter protected Vector2f size;
    
    protected Skin skin;
    
    public abstract void update(float tpf);
    public abstract void destroy();
    
    public void setPos(Vector3f pos) {
        node.setLocalTranslation(pos);
    }
    
    public Vector3f getPos() {
        return node.getLocalTranslation();
    }
}
