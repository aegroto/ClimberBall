/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.state;

import com.aegroto.climberball.skin.Skin;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author lorenzo
 */

public final class SkinAppState extends BaseAppState {
    @Getter private String currentSkinName;
    @Getter @Setter private Skin currentSkin;
    
    private AssetManager assetManager;
    
    public SkinAppState(String skinName) {
        this.currentSkinName=skinName;
    }
    
    @Override
    protected void initialize(Application app) {
        this.assetManager=app.getAssetManager();
        
        loadSkin(currentSkinName);
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }    
    
    public void loadSkin(String skinName) {
       this.currentSkinName=skinName;
       currentSkin=new Skin(assetManager,skinName);
    }
}
