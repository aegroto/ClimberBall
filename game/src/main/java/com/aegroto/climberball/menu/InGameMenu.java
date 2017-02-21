/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.menu;

import com.aegroto.common.Coordinate2D;
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
    
    @Override
    public void onAttach(GuiAppState guiAppState) {
        super.onAttach(guiAppState);
        
        attachElement(scoreText=new GUIText(
                new Coordinate2D(.05f,.95f).toVector(),
                "Score: ", .3f, 
                guiAppState.getGuiFont(), 
                guiAppState.getGuiNode()
        ));
    }
 
    public void setScoreText(int score) {
        scoreText.setText("Score: "+score);
    }
}
