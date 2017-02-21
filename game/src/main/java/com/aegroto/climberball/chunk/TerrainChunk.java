/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.chunk;

import com.aegroto.climberball.shape.Polygon;
import com.aegroto.climberball.skin.Skin;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import static com.aegroto.common.Helpers.safeAttachChild;
import static com.aegroto.common.Helpers.safeRemoveFromParent;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import java.util.ArrayList;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public abstract class TerrainChunk {
    protected final Skin skin;
    protected Geometry geom,surfaceGeom;
    protected Node rootNode;
    
    @Getter protected Node node;
    @Getter protected boolean destroyed=false;
    @Getter protected final Vector3f jointVector,targetVector;
    @Getter protected byte surfaceType;
    
    @Getter protected ArrayList<Geometry> particlesGeoms=new ArrayList<>();
    
    /*public TerrainChunk(Vector3f startPoint,Node rootNode,Material mat) {

    }*/
    
    protected TerrainChunk(Vector3f startPoint,Node rootNode,Skin environmentSkin,Material mat) { 
        this.rootNode=rootNode;
        this.skin=environmentSkin;
        
        this.node=new Node();
        
        int direction=FastMath.nextRandomInt(0, 1);
        direction=direction==0 ? -1 : 1;
        
        float yFactor=FastMath.nextRandomFloat()*direction*Helpers.getTerrainSlopes(),
              yPos=startPoint.y+yFactor;
        
        yPos=yPos > Helpers.getMaxHeight() ? 
                Helpers.getMaxHeight() : yPos < Helpers.getMinHeight() ? Helpers.getMinHeight() : yPos;
        
        jointVector=new Vector3f(
                startPoint.x+Helpers.getTerrainChunkSize(),
                yPos,
                0);
        
        Polygon p=new Polygon(
                new Vector3f(0, 0, 0),
                new Vector3f(Helpers.getTerrainChunkSize(), 0, 0),
                new Vector3f(0, startPoint.y, 0),
                jointVector.subtract(startPoint.x,0f,0f)
        );
        
        geom=new Geometry("TerrainChunk",p);
        
        geom.setMaterial(mat);
        
        Vector3f surfaceBaseOffset=new Vector3f(0f,startPoint.y,0f),
                 surfaceBaseVec0=p.getVertices()[2].subtract(surfaceBaseOffset),
                 surfaceBaseVec1=p.getVertices()[3].subtract(surfaceBaseOffset);
        
        surfaceGeom=new Geometry("TerrainChunk Surface",
                new Polygon(
                    surfaceBaseVec0,
                    surfaceBaseVec1,
                    surfaceBaseVec0.add(Helpers.getSurfaceOffset()),
                    surfaceBaseVec1.add(Helpers.getSurfaceOffset())
                ));  
        
        surfaceGeom.setLocalTranslation(0,startPoint.y,0f); 
        
        rootNode.attachChild(node);
        node.setLocalTranslation(startPoint.x, 0, 0f);
        
        safeAttachChild(node,geom);
        
        targetVector=jointVector.add(Helpers.getSurfaceOffset());
    }
    
    public void destroy() {
        geom.removeFromParent();
        surfaceGeom.removeFromParent();
        for(Geometry particleGeom:particlesGeoms)
            Helpers.safeRemoveFromParent(particleGeom);
    }

    public boolean checkForBarrage(float xBarrage) {
        if(node.getLocalTranslation().x < xBarrage) {            
            destroyed = true;
            return true;
        }
        
        return false;
    }
    
    protected float internalElaborateSpeedOnSurface(float speed, byte ballForm,final byte high,byte med,byte min) {
        float newSpeed;
        if(ballForm == high) 
            newSpeed=speed*Helpers.SPEED_DIM_FACTOR_HIGH;             
        else if(ballForm == med) 
            newSpeed=speed*Helpers.SPEED_DIM_FACTOR_MED; 
        else if(ballForm == min) 
            newSpeed=speed*Helpers.SPEED_DIM_FACTOR_MIN; 
        else {
            newSpeed=speed*Helpers.SPEED_AUG_FACTOR;
            if(newSpeed<Helpers.MIN_BALL_SPEED) newSpeed=Helpers.MIN_BALL_SPEED;
        }
        
        if(newSpeed>=Helpers.MAX_BALL_SPEED) return newSpeed*Helpers.SPEED_DIM_FACTOR_HIGHER;
        else return newSpeed;
    }
    
    public void setParticles(Geometry particlesGeom) {
        Helpers.safeAttachChild(node, particlesGeom);
        Helpers.safeLocalTranslation(particlesGeom,surfaceGeom.getLocalTranslation().add(0f,Helpers.getBallSize() * .25f,0f)); 
        
        particlesGeoms.add(particlesGeom);
    }
    
    public abstract float elaborateSpeedOnSurface(float speed, byte ballForm);
}
