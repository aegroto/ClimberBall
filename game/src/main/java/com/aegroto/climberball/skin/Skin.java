/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.skin;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public class Skin {    
    @Getter protected final BallSkin ballSkin;
    @Getter protected final Texture /*plainParticles, rockParticles, sandParticles, grassParticles,*/ blankTexture;
    @Getter protected final SurfaceSkin plainSkin, rockSkin, sandSkin, grassSkin;  
    @Getter protected final Material backgroundMaterial,
                                     speedPickupMaterial;
    @Getter protected final BitmapFont guiFont;
    
    private Texture initializeTexture(AssetManager assetManager,String location) {
        Texture tex=assetManager.loadTexture(location);
        tex.setAnisotropicFilter(3);
        tex.setMagFilter(Texture.MagFilter.Nearest);
        tex.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        return tex;
    }
        
    public Skin(AssetManager assetManager,
                       String assetsFolder) {
        Material mat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);        

        mat.setTexture("ColorMap", initializeTexture(assetManager,assetsFolder+"/plain.png"));
        plainSkin=new SurfaceSkin((byte) 0,mat.clone());     
        
        mat.setTexture("ColorMap", initializeTexture(assetManager,assetsFolder+"/rock.png"));
        rockSkin=new SurfaceSkin((byte) 1,mat.clone());    
        
        mat.setTexture("ColorMap", initializeTexture(assetManager,assetsFolder+"/sand.png"));
        sandSkin=new SurfaceSkin((byte) 2,mat.clone());
        
        mat.setTexture("ColorMap", initializeTexture(assetManager,assetsFolder+"/grass.png"));
        grassSkin=new SurfaceSkin((byte) 3,mat.clone());
        
        backgroundMaterial=mat.clone();
        backgroundMaterial.setTexture("ColorMap",initializeTexture(assetManager,assetsFolder+"/background.png"));
        
        speedPickupMaterial=mat.clone();
        speedPickupMaterial.setTexture("ColorMap",initializeTexture(assetManager,assetsFolder+"/pickups/speed.png"));
        
        /*plainParticles=initializeTexture(assetManager, assetsFolder+"/particles/particles_plain.png");
        rockParticles=initializeTexture(assetManager, assetsFolder+"/particles/particles_rock.png");
        sandParticles=initializeTexture(assetManager, assetsFolder+"/particles/particles_sand.png");
        grassParticles=initializeTexture(assetManager, assetsFolder+"/particles/particles_grass.png");*/
        
        ballSkin=new BallSkin(
                initializeTexture(assetManager,assetsFolder+"/ball/core_plain.png"),
                initializeTexture(assetManager,assetsFolder+"/ball/core_rock.png"),
                initializeTexture(assetManager,assetsFolder+"/ball/core_sand.png"),
                initializeTexture(assetManager,assetsFolder+"/ball/core_grass.png"),
                initializeTexture(assetManager,assetsFolder+"/ball/plain.png"),
                initializeTexture(assetManager,assetsFolder+"/ball/rock.png"),
                initializeTexture(assetManager,assetsFolder+"/ball/sand.png"),
                initializeTexture(assetManager,assetsFolder+"/ball/grass.png")
        );
        
        guiFont=assetManager.loadFont(assetsFolder+"/font/font.fnt");
        
        blankTexture=initializeTexture(assetManager,"blank.png");
    }
}
