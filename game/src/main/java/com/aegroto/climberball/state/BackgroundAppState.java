/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.state;

import com.aegroto.climberball.skin.Skin;
import com.aegroto.common.Coordinate2D;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;

/**
 *
 * @author lorenzo
 */
public class BackgroundAppState extends BaseAppState {    
    protected Geometry backgroundGeom;
    protected Node rootNode;
    
    protected Skin skin;
    
    public BackgroundAppState(Node rootNode, Skin skin) {
        this.rootNode = rootNode;
        this.skin = skin;
    }
    
    @Override
    protected void initialize(Application app) { 
        backgroundGeom=new Geometry("Environment Background",
                new Quad(Coordinate2D.xConvert(1f), Coordinate2D.yConvert(1f)));
        backgroundGeom.setMaterial(skin.getBackgroundMaterial());
        backgroundGeom.getMaterial().setFloat("SlidingSpeed", 0.5f);
    }

    @Override
    protected void cleanup(Application app) { }

    @Override
    protected void onEnable() {
        rootNode.attachChild(backgroundGeom);
        
        backgroundGeom.setLocalTranslation(0f, 0f, -5f);
    }

    @Override
    protected void onDisable() { }
}
