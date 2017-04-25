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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public final class InGameMenu extends Menu {
    private GUIText scoreText, infoText;
    
    @Override
    public void onAttach(GuiAppState guiAppState) {
        super.onAttach(guiAppState);
        
        if(scoreText == null) {
            scoreText=new GUIText(
                    new Coordinate2D(.05f,.95f).toVector(),
                    "Score: ", .3f, 
                    guiAppState.getGuiFont(), 
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        if(infoText == null) {
            infoText=new GUIText(
                    new Coordinate2D(0f,.85f).toVector(),
                    "", .3f,
                    guiAppState.getGuiFont(), 
                    guiAppState.getGuiConjunctionNode()
            );

            infoText.centerX();
        }
        
        attachElement(scoreText);
        attachElement(infoText);
    }
    
    public void showInfoText(String text) {
        infoText.setText(text);
        infoText.centerX();
        
        //final Timer timer = new Timer();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                infoText.setText("");
            }
        }, 2000);
    }
 
    public void setScoreText(int score) {
        scoreText.setText("Score: "+score);
    }
}
