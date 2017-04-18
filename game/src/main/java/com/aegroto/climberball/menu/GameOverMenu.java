/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.menu;

import com.aegroto.common.Coordinate2D;
import com.aegroto.gui.GUIButton;
import com.aegroto.gui.GUIImage;
import com.aegroto.gui.GUIText;
import com.aegroto.gui.menu.Menu;
import com.aegroto.gui.states.GuiAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector2f;
import java.util.concurrent.Callable;

/**
 *
 * @author lorenzo
 */
public class GameOverMenu extends Menu {
    private GUIText scoreText,tipsText;
    private GUIButton background,
                      secondChanceButton;
    
    private final int score;
    private final boolean hasSecondChance;
    
    private final Callable resetGameCallable,secondChanceCallable;
    private final SimpleApplication app;
    
    public GameOverMenu(Callable resetGameRunnable,Callable secondChanceCallable,
                        SimpleApplication app,int score, boolean hasSecondChance) {
        super();
        
        this.score=score;
        this.resetGameCallable=resetGameRunnable;
        this.secondChanceCallable=secondChanceCallable;
        this.app=app;
        this.hasSecondChance = hasSecondChance;
    }
    
    @Override
    public void onAttach(GuiAppState guiAppState) {
        super.onAttach(guiAppState);
        
        attachElement(background=new GUIButton(
                new Vector2f(0f,0f),
                new Coordinate2D(1f, 1f).toVector(),
                "","alpha_background.png",
                guiAppState.getGuiFont(),
                guiAppState.getAssetManager(), 
                guiAppState.getGuiConjunctionNode(),
                guiAppState.getInteractiveGUIsList()
        ) {
            @Override
            public void execFunction() {
                app.enqueue(resetGameCallable);
            }
        });
        
        background.setZOffset(-1);
        
        if(hasSecondChance) {
            attachElement(secondChanceButton=new GUIButton(
                    new Coordinate2D(0f,.4f).toVector(),
                    new Coordinate2D(.35f, .2f).toVector(),
                    "Second Chance", .4f,
                    guiAppState.getGuiFont(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode(),
                    guiAppState.getInteractiveGUIsList()
            ) {
                @Override
                public void execFunction() {
                    app.enqueue(secondChanceCallable);
                }
            });

            secondChanceButton.centerX();
        }
                
        attachElement(scoreText=new GUIText(
                new Coordinate2D(.5f,.75f).toVector(),
                "Ouch! Your final score is "+this.score, .5f, 
                guiAppState.getGuiFont(), 
                guiAppState.getGuiConjunctionNode()
        ));
        
        scoreText.centerX();
        
        attachElement(tipsText=new GUIText(
                new Coordinate2D(.5f,.25f).toVector(),
                "Tap anywhere to retry", .5f, 
                guiAppState.getGuiFont(), 
                guiAppState.getGuiConjunctionNode()
        ));
        
        tipsText.centerX();
    }
}
