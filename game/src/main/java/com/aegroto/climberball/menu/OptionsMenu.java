/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.menu;

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
        
        if(musicCheckbox == null) {
            musicCheckbox=new GUICheckbox(
                new Coordinate2D(.8f,.65f).toVector(),
                new Coordinate2D(.1f, .1f).toVector(),
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode(),
                guiAppState.getInteractiveGUIsList()
            );

            musicCheckbox.centerX();
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
                new Coordinate2D(.1f, .1f).toVector(),
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
