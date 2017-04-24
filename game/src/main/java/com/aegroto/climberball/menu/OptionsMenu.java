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
import com.aegroto.gui.menu.Menu;
import com.aegroto.gui.states.GuiAppState;
import com.jme3.math.Vector2f;

/**
 *
 * @author lorenzo
 */
public class OptionsMenu extends Menu {
    private GUIImage background;
    private GUIButton backButton;
    
    private GUICheckbox musicCheckbox;
    
    @Override
    public void onAttach(GuiAppState guiAppState) {
        super.onAttach(guiAppState);
        
        attachElement(background=new GUIImage(
                new Vector2f(0f,0f),
                new Coordinate2D(1f, 1f).toVector(),
                "gui/light_background.png", 
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode()));
        
        attachElement(backButton=new GUIButton(
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
                
            }
        });
        
        backButton.centerX();
       
        attachElement(musicCheckbox=new GUICheckbox(
            new Coordinate2D(.45f,.45f).toVector(),
            new Coordinate2D(.1f, .1f).toVector(),
            guiAppState.getAssetManager(),
            guiAppState.getGuiConjunctionNode(),
            guiAppState.getInteractiveGUIsList())
        );
          
        musicCheckbox.centerX();
    }
}
