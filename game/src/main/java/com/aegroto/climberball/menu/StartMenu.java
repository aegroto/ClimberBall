/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.menu;

import com.aegroto.common.Coordinate2D;
import com.aegroto.gui.GUIButton;
import com.aegroto.gui.GUIImage;
import com.aegroto.gui.GUIMultipleLineText;
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
public class StartMenu extends Menu {
    private GUIButton background;
    private GUIText titleText;
    private GUIMultipleLineText explainationText;
    
    private GUIImage
            ballPlainImage,ballRockImage,ballSandImage,ballGrassImage,
            terrainPlainImage,terrainRockImage,terrainSandImage,terrainGrassImage;
    
    private final GUIImage[] arrow=new GUIImage[3];
    
    private final Callable resetGameCallable;
    private final SimpleApplication app;
    
    public StartMenu(Callable resetGameRunnable,SimpleApplication app) {
        super();
        
        this.resetGameCallable=resetGameRunnable;
        this.app=app;
    }
    
    @Override
    public void onAttach(GuiAppState guiAppState) {
        super.onAttach(guiAppState);   
        
        attachElement(background=new GUIButton(
                new Vector2f(0f,0f),
                new Coordinate2D(1f, 1f).toVector(),
                "","gui/light_background.png",
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
        
        attachElement(titleText=new GUIText(
                new Coordinate2D(.5f, .95f).toVector(),
                "CLIMBERBALL", .5f, 
                guiAppState.getGuiFont(), 
                guiAppState.getGuiConjunctionNode()
        ));
        
        titleText.centerX();
        
        attachElement(explainationText=new GUIMultipleLineText(
                new Coordinate2D(0f, .65f).toVector(),
                  "Switch between the 4 forms (plain,rock,sand,grass) to adapt the terrain \n"
                + "Pay attention because walking with an unfit form makes you slow,until \n"
                + "the ball falls into the darkness (game over).",
                .2f, Coordinate2D.yConvert(.035f),
                guiAppState.getGuiFont(), 
                guiAppState.getGuiConjunctionNode()
        ));
        
        Vector2f imageScale=new Coordinate2D(.06f,.1f).toVector();
        
        attachElement(ballPlainImage=new GUIImage(
                new Coordinate2D(.15f,.425f).toVector(),
                imageScale,
                "base/ball/core_plain.png",
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode()
        ));
        
        attachElement(ballRockImage=new GUIImage(
                new Coordinate2D(.35f,.425f).toVector(),
                imageScale,
                "base/ball/core_rock.png",
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode()
        ));
        
        attachElement(ballSandImage=new GUIImage(
                new Coordinate2D(.55f,.425f).toVector(),
                imageScale,
                "base/ball/core_sand.png",
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode()
        ));
        
        attachElement(ballGrassImage=new GUIImage(
                new Coordinate2D(.75f,.425f).toVector(),
                imageScale,
                "base/ball/core_grass.png",
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode()
        ));
        
        attachElement(terrainPlainImage=new GUIImage(
                new Coordinate2D(.15f,.225f).toVector(),
                imageScale,
                "base/plain.png",
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode()
        ));
        
        attachElement(terrainRockImage=new GUIImage(
                new Coordinate2D(.35f,.225f).toVector(),
                imageScale,
                "base/rock.png",
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode()
        ));
        
        attachElement(terrainSandImage=new GUIImage(
                new Coordinate2D(.55f,.225f).toVector(),
                imageScale,
                "base/sand.png",
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode()
        ));
        
        attachElement(terrainGrassImage=new GUIImage(
                new Coordinate2D(.75f,.225f).toVector(),
                imageScale,
                "base/grass.png",
                guiAppState.getAssetManager(),
                guiAppState.getGuiConjunctionNode()
        ));
        
        for(int i=0;i<arrow.length;i++) {
            attachElement(arrow[i]=new GUIImage(
                    new Coordinate2D(.25f+(i*.2f),.425f).toVector(),
                    new Coordinate2D(.08f,.08f).toVector(),
                    "help_arrow.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            ));
        }
        explainationText.centerX();
    }
}
