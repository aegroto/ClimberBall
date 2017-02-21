/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.entity;

import com.aegroto.climberball.chunk.TerrainChunk;
import com.aegroto.climberball.skin.Skin;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture2D;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author lorenzo
 */
public class EntityBall extends Entity {
    protected Geometry borderGeom;
    protected Material borderMaterial,particleMat;
    protected ParticleEmitter particleEmitter;
    
    protected Node bodyNode;
    @Getter @Setter protected byte currentForm;
    
    public EntityBall(Node rootNode,AssetManager assetManager,Skin skin) {
        this.rootNode=rootNode;
        this.assetManager=assetManager;
        this.skin=skin;
        
        node=new Node();
        bodyNode=new Node();
        
        node.attachChild(bodyNode);
        //Core
        geom=new Geometry("Ball Border Geometry",new Quad(Helpers.getBallSize(),Helpers.getBallSize()));     
        geom.setLocalTranslation(-Helpers.getBallSize()/2f,-Helpers.getBallSize()/2f,1f);
        
        material=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha); 
        //material.setTexture("ColorMap", skin.getBallSkin().getBaseTexture());
        
        geom.setMaterial(material);
        
        bodyNode.attachChild(geom);
        
        //Border
        borderGeom=new Geometry("Ball Border Geometry",new Quad(Helpers.getBallSize()*1.3f,Helpers.getBallSize()*1.3f)); 
        borderGeom.setLocalTranslation(geom.getLocalTranslation().add(
                new Vector3f(-Helpers.getBallSize()*.15f, -Helpers.getBallSize()*.15f, -geom.getLocalTranslation().z)));
        
        borderMaterial=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        borderMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);  
         
        borderGeom.setMaterial(borderMaterial);  
        
        bodyNode.attachChild(borderGeom); 
        
        //Particles
        /*particleEmitter=new ParticleEmitter("Ball Debris", ParticleMesh.Type.Triangle, 30);
        particleMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        particleMat.setTexture("Texture", skin.getRockParticles());    
        particleEmitter.setMaterial(particleMat);
        particleEmitter.setImagesX(2);
        particleEmitter.setImagesY(2); // 2x2 texture animation
        particleEmitter.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));   // red
        particleEmitter.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.3f);
        particleEmitter.setSelectRandomImage(true);
        
        particleEmitter.setLocalTranslation(0f,0f,3f);*/
            /** Explosion effect. Uses Texture from jme3-test-data library! */ 
        particleEmitter = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10);
        Material debrisMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        debrisMat.setTexture("Texture", assetManager.loadTexture("Debris.png"));
        particleEmitter.setMaterial(debrisMat);
        particleEmitter.setImagesX(3); particleEmitter.setImagesY(3); // 3x3 texture animation
        particleEmitter.setRotateSpeed(4);
        particleEmitter.setSelectRandomImage(true);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        particleEmitter.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f));
        particleEmitter.setGravity(0f,6f,0f);
        particleEmitter.getParticleInfluencer().setVelocityVariation(.60f);
        particleEmitter.setNumParticles(500);
        particleEmitter.setStartSize(.05f);
        particleEmitter.setEndSize(.05f);
        particleEmitter.emitAllParticles();

        updateParticles(null, (byte) 0);
        
        bodyNode.attachChild(particleEmitter);
        
        rootNode.attachChild(node);
        
        currentForm=-1;
        switchForm();
    }
    
    //private final Quaternion rotation=new Quaternion().fromAngles(0f,0f,FastMath.QUARTER_PI);
    @Setter private float rotationSpeed=FastMath.QUARTER_PI/16f;
    private final float[] rotation={0f,0f,0f};
    
    private final float borderScalingSpeed=.2f,particlesSpeed=.05f;
    private float borderScaling=1f,particlesX=0f,particlesY=0f;
    private boolean switchingForm=false;
    
    @Override
    public void update(float tpf) {
        rotation[2]-=rotationSpeed;
        
        bodyNode.setLocalRotation(new Quaternion().fromAngles(rotation));
        
        if(rotation[2] <= -FastMath.TWO_PI) rotation[2]=0f;
        
        if(switchingForm) {
            borderScaling-=borderScalingSpeed;
            if(borderScaling<=.4f) switchingForm=false;
            
            bodyNode.setLocalScale(borderScaling);
        } else if(borderScaling<1f) {
            borderScaling+=borderScalingSpeed;
            
            bodyNode.setLocalScale(borderScaling);
        }
        
        if(particlesX <= 0f || particlesY >= 1f) {
            particlesX=1f;
            particlesY=0f;
        } else {
            particlesX-=particlesSpeed;
            particlesY+=particlesSpeed;
        }
    }
    
    public void updateParticles(TerrainChunk rootingChunk,byte surfaceType) {
        //particleEmitter.emitAllParticles();
        /*switch(surfaceType) { 
            case 1: 
                particleMat.setTexture("Texture", skin.getRockParticles());
                particleEmitter.emitAllParticles();
                break;
            case 2:             
                particleMat.setTexture("Texture", skin.getGrassParticles());
                particleEmitter.emitAllParticles();
                break;
        }*/
    }

    @Override
    public void destroy() {
    }
        
    public void setPos(Vector2f pos) {
        node.setLocalTranslation(pos.x,pos.y,2f);
    }
    
    public void safeSetPos(Vector2f pos) {
        Helpers.safeLocalTranslation(node,new Vector3f(pos.x,pos.y,2f));
    }
    
    public int switchForm() {
        currentForm++;
        if(currentForm==4) currentForm=0;
        
        switch(currentForm) {
            case 1:
                material.setTexture("ColorMap", skin.getBallSkin().getCoreRockTexture());
                borderMaterial.setTexture("ColorMap", skin.getBallSkin().getRockTexture());
                break;
            case 2:
                material.setTexture("ColorMap", skin.getBallSkin().getCoreSandTexture());
                borderMaterial.setTexture("ColorMap", skin.getBallSkin().getSandTexture());
                break;
            case 3:
                material.setTexture("ColorMap", skin.getBallSkin().getCoreGrassTexture());
                borderMaterial.setTexture("ColorMap", skin.getBallSkin().getGrassTexture());
                break;
            default:
                material.setTexture("ColorMap", skin.getBallSkin().getCorePlainTexture());
                borderMaterial.setTexture("ColorMap", skin.getBallSkin().getPlainTexture());
        }
        
        switchingForm=true;
        return currentForm;
    }
}
