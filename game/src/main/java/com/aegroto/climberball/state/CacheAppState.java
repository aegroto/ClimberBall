/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.state;

import com.aegroto.climberball.CacheManager;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public class CacheAppState extends BaseAppState {  
    @Getter private CacheManager cacheManager;
    private String cacheLocation = "";
    
    public CacheAppState(String cacheLocation) {
        this.cacheLocation = cacheLocation;
    }
    
    @Override
    protected void initialize(Application aplctn) { 
        cacheManager = new CacheManager(cacheLocation);
    }

    @Override
    protected void cleanup(Application aplctn) { }

    @Override
    protected void onEnable() {
        checkAndRepairCache();
    }

    @Override
    protected void onDisable() { }    
    
    private void checkAndRepairCache() {
        boolean repaired = false;
        if(cacheManager.getCacheBlock("BestScore") == null) {
            cacheManager.setCacheBlock("BestScore", 0);
            repaired = true;
        }
        if(cacheManager.getCacheBlock("MusicVolume") == null) {
            cacheManager.setCacheBlock("MusicVolume", 1f);
            repaired = true;
        }
        if(cacheManager.getCacheBlock("EffectsVolume") == null) {
            cacheManager.setCacheBlock("EffectsVolume", 1f);
            repaired = true;
        }
        
        if(repaired) {
            System.out.println("Repaired, saving cache");
            cacheManager.saveCacheOnFile();
        }
    }
    
    public Object getCacheBlock(String id) {
        return cacheManager.getCacheBlock(id);
    }
    
}
