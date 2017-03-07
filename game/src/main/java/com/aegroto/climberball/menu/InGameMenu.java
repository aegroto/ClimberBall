/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.menu;

import com.aegroto.common.Coordinate2D;
import com.aegroto.gui.GUIMultipleLineText;
import com.aegroto.gui.GUIText;
import com.aegroto.gui.menu.Menu;
import com.aegroto.gui.states.GuiAppState;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public final class InGameMenu extends Menu {
    private GUIText scoreText;
    private GUIMultipleLineText infoText;
    
    @Override
    public void onAttach(GuiAppState guiAppState) {
        super.onAttach(guiAppState);
        
        attachElement(scoreText=new GUIText(
                new Coordinate2D(.05f,.95f).toVector(),
                "Score: ", .3f, 
                guiAppState.getGuiFont(), 
                guiAppState.getGuiNode()
        ));
        
        attachElement(infoText=new GUIMultipleLineText(
                new Coordinate2D(0f,.85f).toVector(),
                "", .3f, Coordinate2D.yConvert(.02f),
                guiAppState.getGuiFont(), 
                guiAppState.getGuiNode()
        ));
        
        infoText.centerX();
    }
    
    public void setInfoText(String text) {
        infoText.setText(text, guiAppState.getGuiFont(), guiAppState.getGuiNode());
        infoText.centerX();
    }
 
    public void setScoreText(int score) {
        scoreText.setText("Score: "+score);
    }
}
