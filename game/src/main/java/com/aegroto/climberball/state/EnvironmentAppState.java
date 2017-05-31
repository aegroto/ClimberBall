/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.state;

import com.aegroto.climberball.chunk.GrassChunk;
import com.aegroto.climberball.chunk.PlainChunk;
import com.aegroto.climberball.chunk.RockChunk;
import com.aegroto.climberball.chunk.SandChunk;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import java.util.LinkedList;
import com.aegroto.climberball.chunk.TerrainChunk;
import com.aegroto.climberball.entity.pickup.EntityPickup;
import com.aegroto.climberball.entity.pickup.EntityPickupSmoother;
import com.aegroto.climberball.entity.pickup.EntityPickupSpeed;
import com.aegroto.climberball.skin.Skin;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import static com.aegroto.common.Helpers.safeAttachChild;
import static com.aegroto.common.Helpers.safeRemoveFromParent;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author lorenzo
 */
public final class EnvironmentAppState extends BaseAppState {    
    protected Node rootNode,terrainNode;
    protected Material chunkMat;
    @Getter protected LinkedList<TerrainChunk> chunkList;
    @Getter protected ArrayList<EntityPickup> pickupList;
    protected ScheduledThreadPoolExecutor executor;
    
    protected int minChunks;
    protected ArrayDeque<Integer> chunkGenerationQueue;
    
    protected Skin skin;
    
    @Setter protected PlayerAppState playerAppState = null;
    
    protected float 
            xBarrage,
            
            speed,
            speedVariationEnhancer,
            maxSpeed,
            
            pickupSpawningFactor,
            pickupSpawningVariation,
            pickupSpawningVariationEnhancer,
            
            changeSurfaceTypeFactor,
            changeSurfaceVariation,
            changeSurfaceVariationEnhancer;
    
    public EnvironmentAppState(Node rootNode, ScheduledThreadPoolExecutor executor, Skin skin) {
        this.rootNode = rootNode;        
        this.executor = executor;
        this.skin = skin;
        
        this.chunkList = new LinkedList();  
        this.pickupList = new ArrayList();
    }
    
    @Override
    protected void initialize(Application app) {   
        chunkMat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        chunkMat.setColor("Color", ColorRGBA.Black);
             
        terrainNode=new Node();
    }

    @Override
    protected void cleanup(Application app) {
        
    }

    @Override
    protected void onEnable() {
        xBarrage=-Helpers.getTerrainChunkSize();
        
        speed = Helpers.INITIAL_SPEED;
        maxSpeed = Helpers.INITIAL_MAX_SPEED;
        speedVariationEnhancer = 0f;
        
        pickupSpawningFactor = 10f;
        pickupSpawningVariation = Helpers.INITIAL_PICKUP_SPAWNING_VARIATION;  
        pickupSpawningVariationEnhancer = 0f;
        
        changeSurfaceTypeFactor = 1.3f;
        changeSurfaceVariation = Helpers.INITIAL_CHANGE_SURFACE_VARIATION;  
        changeSurfaceVariationEnhancer = 0f;
                
        rootNode.attachChild(terrainNode);
        minChunks=(int) ((Coordinate2D.getSettings().getWidth() / Helpers.getTerrainChunkSize()) * 1.5f) + 1;
        
        this.chunkGenerationQueue = new ArrayDeque(minChunks);
        
        while(chunkList.size() < minChunks) { 
            generateChunk(nextChunkSurface());
        }
        
        // queueChunkGeneration(initialChunks, -1);
    }
    
    @Override
    protected void onDisable() {
        tryToClearChunks();
        terrainNode.removeFromParent();
        //backgroundGeom.removeFromParent();
    }   
    
    private void tryToClearChunks() {
        try {
            while(chunkList.size()>0) {
                chunkList.getFirst().destroy();
                chunkList.removeFirst();
            }
        } catch(ConcurrentModificationException e) {
           tryToClearChunks();
        }        
    }
    
    protected int nextChunkSurface() {
        int nextChunkSurface;
        
        if(FastMath.nextRandomFloat() > changeSurfaceTypeFactor) {
            nextChunkSurface=FastMath.nextRandomInt(0,3);                    
            
            // System.out.println("Changing surface type!");
            
            changeSurfaceVariation = Helpers.INITIAL_CHANGE_SURFACE_VARIATION - changeSurfaceVariationEnhancer;            
            changeSurfaceTypeFactor = 1f;
            changeSurfaceVariationEnhancer -= Helpers.CHANGE_SURFACE_VARIATION_ENHANCING;
            
            changeSurfaceVariation*=2;
        } else {
            if(chunkList.size()>0) {        
                nextChunkSurface=chunkList.getLast().getSurfaceType();
            } else {
                nextChunkSurface=0;
            }            
        }
        
        return nextChunkSurface;
        // generateChunk(nextChunkSurface);
    }
    
    protected void generateChunk(int nextChunkSurface) {
        Vector3f startPoint;
        int nextPickupType = 0;
        
        if(chunkList.size()>0) {
            startPoint=chunkList.getLast().getJointVector();          
        } else {
            startPoint=Helpers.getStartVector();
        }
        
        TerrainChunk newChunk = null;
        
        if(FastMath.nextRandomFloat() > pickupSpawningFactor) {
            nextPickupType=FastMath.nextRandomInt(1, 2);
            
            pickupSpawningVariation=Helpers.INITIAL_PICKUP_SPAWNING_VARIATION-pickupSpawningVariationEnhancer;            
            pickupSpawningFactor=1f;
            pickupSpawningVariationEnhancer-=Helpers.PICKUP_SPAWNING_VARIATION_ENHANCING;
        }
        
        switch(nextChunkSurface) {
            case 1:
                newChunk=new RockChunk(startPoint,terrainNode,skin,chunkMat);
                break;
            case 2:
                newChunk=new SandChunk(startPoint,terrainNode,skin,chunkMat);
                break;
            case 3:
                newChunk=new GrassChunk(startPoint,terrainNode,skin,chunkMat);
                break;
            default:
                newChunk=new PlainChunk(startPoint,terrainNode,skin,chunkMat);
        }
        
        chunkList.addLast(newChunk);
        
        switch(nextPickupType) {
            case 0: break;
            case 1: 
                pickupList.add(new EntityPickupSpeed(terrainNode, newChunk.getJointVector(), skin, getApplication().getAssetManager())); 
                break;
            case 2:
                pickupList.add(new EntityPickupSmoother(terrainNode, newChunk.getJointVector(), this, skin, getApplication().getAssetManager())); 
                break;                 
        }
        
        pickupSpawningFactor -= pickupSpawningVariation;
        pickupSpawningVariation*=2;
        
        changeSurfaceTypeFactor -= changeSurfaceVariation;
    }
    
    public void tailQueueChunkGeneration(int n, int surfaceType) {
        if(surfaceType == -1) {
            for(int i = 0; i < n; i++) 
                chunkGenerationQueue.addLast(nextChunkSurface());
        } else {
            for(int i = 0; i < n; i++) 
                chunkGenerationQueue.addLast(surfaceType);
        }
    }
    
    public void headQueueChunkGeneration(int n, int surfaceType) {
        if(surfaceType == -1) {
            for(int i = 0; i < n; i++) 
                chunkGenerationQueue.addFirst(nextChunkSurface());
        } else {
            for(int i = 0; i < n; i++) 
                chunkGenerationQueue.addFirst(surfaceType);
        }
    }
    
    protected void checkMaxSpeed(float score) {
        if(score >= 350f)
            maxSpeed = 9f;
        else if(score >= 200f)
            maxSpeed = 8f;
        else if(score >= 50f)
            maxSpeed = 6f;        
    }
    
    @Override
    public void update(float tpf) {
        if(!chunkGenerationQueue.isEmpty() && chunkList.size() < minChunks) {
            // System.out.println(chunkGenerationQueue + "\t" + changeSurfaceTypeFactor);
            generateChunk(chunkGenerationQueue.pop());
        }
        
        TerrainChunk first = chunkList.getFirst();
        
        if(first.isDestroyed()) {
            first.destroy();
            // generateChunk();
            if(chunkGenerationQueue.isEmpty())
                tailQueueChunkGeneration(1, -1);
            chunkList.removeFirst();
        }
        
        EntityPickup toBeRemovedPickup = null;
        for(EntityPickup pickup:pickupList) {
            if(pickup.isDestroyed()) 
                toBeRemovedPickup=pickup;
            else if(!pickup.checkForBarrage(xBarrage)) {              
                pickup.update(tpf);
            }
        }
        
        if(toBeRemovedPickup != null) 
            pickupList.remove(toBeRemovedPickup);
        
        if(playerAppState != null) {
            checkMaxSpeed(playerAppState.getScore());
        }
        
        if(speed < maxSpeed)         
            speed *= (Helpers.SPEED_VARIATION + Helpers.SPEED_VARIATION_ENHANCING);
        
        xBarrage += speed;
            
        terrainNode.setLocalTranslation(terrainNode.getLocalTranslation().x - speed, 0f, 5f);

        try {
            chunkList.getFirst().checkForBarrage(xBarrage);
        } catch(NoSuchElementException e) { 
            tailQueueChunkGeneration(1, -1); 
        }
    }
}
