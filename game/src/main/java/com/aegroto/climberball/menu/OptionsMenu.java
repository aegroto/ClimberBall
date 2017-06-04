/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.menu;

import com.aegroto.climberball.CacheManager;
import com.aegroto.climberball.Main.UpdateSwitches;
import com.aegroto.common.Coordinate2D;
import com.aegroto.gui.GUIButton;
import com.aegroto.gui.GUICheckbox;
import com.aegroto.gui.GUIImage;
import com.aegroto.gui.GUIText;
import com.aegroto.gui.menu.Menu;
import com.aegroto.gui.states.GuiAppState;
import com.jme3.math.Vector2f;
import lombok.Setter;

/**
 *
 * @author lorenzo
 */
public class OptionsMenu extends Menu {
    private GUIImage background;
    private GUIButton backButton;
    
    private GUIText musicText,
                    effectsText;
    
    private GUICheckbox musicCheckbox, 
                        effectsCheckbox;
    
    @Setter private Menu onBackMenu;
    
    private CacheManager cacheManager;
    
    public OptionsMenu(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    @Override
    public void onAttach(final GuiAppState guiAppState) {
        super.onAttach(guiAppState);
        
        final OptionsMenu thisMenu = this;
        
        if(background == null) {
            background=new GUIImage(
                new Vector2f(0f,0f),
                new Coordinate2D(1f, 1f).toVector(),
                "gui/light_background.png", 
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode());
        }
        
        if(backButton == null) {
            backButton=new GUIButton(
                    new Coordinate2D(0,.05f).toVector(),
                    new Coordinate2D(.35f, .15f).toVector(),
                    "Back", .4f,
                    guiAppState.getGuiFont(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode(),
                    guiAppState.getInteractiveGUIsList()
            ) {
                @Override
                public void execFunction() {
                    guiAppState.removeMenu(thisMenu);
                    
                    float musicVolumeValue = musicCheckbox.isChecked() ? 1f : 0f;
                    float effectsVolumeValue = effectsCheckbox.isChecked() ? 1f : 0f;
                    
                    cacheManager.setCacheBlock("MusicVolume", musicVolumeValue);
                    cacheManager.setCacheBlock("EffectsVolume", effectsVolumeValue);
                    cacheManager.saveCacheOnFile();
                    
                    UpdateSwitches.loadOptions = true;
                    
                    guiAppState.addMenu(onBackMenu);
                }
            };

            backButton.centerX();
        }
        
        if(musicText == null) {
            musicText=new GUIText(
                    new Coordinate2D(.075f, .75f).toVector(),
                    "Music", .5f, 
                    guiAppState.getGuiFont(), 
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        /*double effectsVolume = 0, musicVolume = 0;
        if(cacheManager.getCacheBlock("EffectsVolume") instanceof Float) {
                effectsVolume = (float) cacheManager.getCacheBlock("EffectsVolume");
                musicVolume = (float) cacheManager.getCacheBlock("MusicVolume");
        } else if(cacheManager.getCacheBlock("EffectsVolume") instanceof Double) {
                effectsVolume = (double) cacheManager.getCacheBlock("EffectsVolume");
                musicVolume = (double) cacheManager.getCacheBlock("MusicVolume");
        }*/
        
        Vector2f checkboxScale = new Coordinate2D(.07f, .1f).toVector();
        
        if(musicCheckbox == null) {
            musicCheckbox=new GUICheckbox(
                new Coordinate2D(.8f,.65f).toVector(),
                checkboxScale,
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode(),
                guiAppState.getInteractiveGUIsList()
            );

            musicCheckbox.centerX();
            //musicCheckbox.setChecked(effectsVolume == 1f);
        }
         
        if(effectsText == null) {
            effectsText=new GUIText(
                    new Coordinate2D(.075f, .6f).toVector(),
                    "Effects", .5f, 
                    guiAppState.getGuiFont(), 
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        if(effectsCheckbox == null) {
            effectsCheckbox=new GUICheckbox(
                new Coordinate2D(.8f,.5f).toVector(),
                checkboxScale,
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode(),
                guiAppState.getInteractiveGUIsList()
            );
        }

        effectsCheckbox.centerX();
        
        attachElement(background);
        
        attachElement(backButton);
        
        attachElement(musicText);        
        attachElement(effectsText);
        
        attachElement(musicCheckbox);
        attachElement(effectsCheckbox);
    }
}
