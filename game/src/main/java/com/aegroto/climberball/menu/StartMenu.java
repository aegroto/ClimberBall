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
    private GUIMultipleLineText explaination0Text;
    
    private GUIImage
            ballPlainImage,ballRockImage,ballSandImage,ballGrassImage,
            terrainPlainImage,terrainRockImage,terrainSandImage,terrainGrassImage;
    
    private final GUIImage[] arrow=new GUIImage[3];
    
    private final OptionsMenu optionsMenu;
    
    private final Callable resetGameCallable;
    private final SimpleApplication app;
    
    private GUIButton optionsButton;
    
    public StartMenu(Callable resetGameRunnable,SimpleApplication app, OptionsMenu optionsMenu) {
        super();
        
        this.resetGameCallable=resetGameRunnable;
        this.app=app;
        this.optionsMenu = optionsMenu;
    }
    
    @Override
    public void onAttach(final GuiAppState guiAppState) {
        super.onAttach(guiAppState);   
        
        final StartMenu thisMenu = this;
        optionsMenu.setOnBackMenu(thisMenu);
        
        if(background == null) {
            background=new GUIButton(
                    new Coordinate2D(0f,.2f).toVector(),
                    new Coordinate2D(1f, .8f).toVector(),
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
            };
        }
        
        if(optionsButton == null) {
            optionsButton=new GUIButton(
                    new Coordinate2D(0f,.05f).toVector(),
                    new Coordinate2D(.35f, .15f).toVector(),
                    "Options", .4f,
                    guiAppState.getGuiFont(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode(),
                    guiAppState.getInteractiveGUIsList()
            ) {
                @Override
                public void execFunction() {
                    guiAppState.removeMenu(thisMenu);
                    guiAppState.addMenu(optionsMenu);
                }
            };
        
            optionsButton.centerX();
        }
        
        if(titleText == null) {
            titleText=new GUIText(
                    new Coordinate2D(.5f, .95f).toVector(),
                    "CLIMBERBALL", .5f, 
                    guiAppState.getGuiFont(), 
                    guiAppState.getGuiConjunctionNode()
            );
        
            titleText.centerX();
        }
        
        if(explaination0Text == null) {
            explaination0Text=new GUIMultipleLineText(
                    new Coordinate2D(0f, .85f).toVector(),
                      "Switch between the 4 forms (plain,rock,sand,grass) to adapt the terrain \n"
                    + "Pay attention because walking with an unfit form makes you slow,until \n"
                    + "the ball falls into the darkness (game over). \n\n\n"
                    + "Use the four buttons in the corners to switch between various forms.\n"
                    + "They're called switchers, surprisingly.",
                    .2f, Coordinate2D.yConvert(.035f),
                    guiAppState.getGuiFont(), 
                    guiAppState.getGuiConjunctionNode()
            );           
        
            explaination0Text.centerX();
        }

        Vector2f imageScale=new Coordinate2D(.06f,.1f).toVector();
        
        if(ballPlainImage == null) {
            ballPlainImage=new GUIImage(
                    new Coordinate2D(.15f,.425f).toVector(),
                    imageScale,
                    "base/ball/core_plain.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        if(ballRockImage == null) {
            ballRockImage=new GUIImage(
                    new Coordinate2D(.35f,.425f).toVector(),
                    imageScale,
                    "base/ball/core_rock.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            );
        }

        if(ballSandImage == null) {
            ballSandImage=new GUIImage(
                    new Coordinate2D(.55f,.425f).toVector(),
                    imageScale,
                    "base/ball/core_sand.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        if(ballGrassImage == null) {
            ballGrassImage=new GUIImage(
                    new Coordinate2D(.75f,.425f).toVector(),
                    imageScale,
                    "base/ball/core_grass.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        if(terrainPlainImage == null) {
            terrainPlainImage=new GUIImage(
                    new Coordinate2D(.15f,.25f).toVector(),
                    imageScale,
                    "base/plain.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        if(terrainRockImage == null) {
            terrainRockImage=new GUIImage(
                    new Coordinate2D(.35f,.25f).toVector(),
                    imageScale,
                    "base/rock.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        if(terrainSandImage == null) {
            terrainSandImage=new GUIImage(
                    new Coordinate2D(.55f,.25f).toVector(),
                    imageScale,
                    "base/sand.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        if(terrainGrassImage == null) {
            terrainGrassImage=new GUIImage(
                    new Coordinate2D(.75f,.25f).toVector(),
                    imageScale,
                    "base/grass.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            );
        }
        
        for(int i=0;i<arrow.length;i++) {
            attachElement(arrow[i]=new GUIImage(
                    new Coordinate2D(.25f+(i*.2f),.425f).toVector(),
                    new Coordinate2D(.08f,.08f).toVector(),
                    "help_arrow.png",
                    guiAppState.getAssetManager(),
                    guiAppState.getGuiConjunctionNode()
            ));
        }
        
        attachElement(background);
        
        attachElement(optionsButton);
        
        attachElement(titleText);
        attachElement(explaination0Text);
        
        attachElement(ballPlainImage);
        attachElement(ballRockImage);
        attachElement(ballSandImage);
        attachElement(ballGrassImage);
        attachElement(terrainPlainImage);
        attachElement(terrainRockImage);
        attachElement(terrainSandImage);
        attachElement(terrainGrassImage);
    }
}
