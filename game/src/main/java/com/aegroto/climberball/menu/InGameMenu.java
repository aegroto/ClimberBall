/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.menu;

import com.aegroto.climberball.entity.EntityBall;
import com.aegroto.climberball.skin.Skin;
import com.aegroto.climberball.state.PlayerAppState;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import com.aegroto.gui.GUIButton;
import com.aegroto.gui.GUIImage;
import com.aegroto.gui.GUIMultipleLineText;
import com.aegroto.gui.GUIText;
import com.aegroto.gui.menu.Menu;
import com.aegroto.gui.states.GuiAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector2f;
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
    
    private GUIImage plainSwitcher, rockSwitcher, sandSwitcher, grassSwitcher;
    private GUIButton plainSwitchButton, rockSwitchButton, sandSwitchButton, grassSwitchButton;
    
    private final Skin skin;
    private final PlayerAppState playerAppState;
    private final Vector2f[] switcherScreenBlocks;
    
    public InGameMenu(Skin skin, PlayerAppState playerAppState) {
        this.skin = skin;
        this.playerAppState = playerAppState;
        
        this.switcherScreenBlocks = new Vector2f[8];
        
        float switcherOffset = 0f;
        
        this.switcherScreenBlocks[0] = 
                new Coordinate2D(switcherOffset, switcherOffset).toVector();            
        this.switcherScreenBlocks[1] = 
                new Coordinate2D(1f - Helpers.SWITCHING_SCREENSPACE_X - switcherOffset, switcherOffset).toVector();
        this.switcherScreenBlocks[2] = 
                new Coordinate2D(1f - Helpers.SWITCHING_SCREENSPACE_X - switcherOffset, 1f - Helpers.SWITCHING_SCREENSPACE_Y - switcherOffset).toVector();
        this.switcherScreenBlocks[3] = 
                new Coordinate2D(switcherOffset, 1f - Helpers.SWITCHING_SCREENSPACE_Y - switcherOffset).toVector();  
    }
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
        
        scoreText.centerX();
        
        if(infoText == null) {
            infoText=new GUIText(
                    new Coordinate2D(0f,.85f).toVector(),
                    "", .3f,
                    guiAppState.getGuiFont(), 
                    guiAppState.getGuiConjunctionNode()
            );

            infoText.centerX();
        }
        
        Material switcherMat=new Material(guiAppState.getAssetManager(), "materials/FloatingSprite/FloatingSprite.j3md");
        switcherMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);  
        
        if(plainSwitcher == null) {            
            plainSwitcher = new GUIImage(
                    switcherScreenBlocks[0],
                    new Coordinate2D(Helpers.SWITCHING_SCREENSPACE_X, Helpers.SWITCHING_SCREENSPACE_Y).toVector(),
                    skin.getBallSkin().getSwitcherPlainTexture(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode()
            );
            
            switcherMat.setTexture("atlas", 
                    guiAppState.getAssetManager().loadTexture(skin.getBallSkin().getSwitcherPlainTexture()));
            plainSwitcher.getPicture().setMaterial(switcherMat.clone());
        }
        
        if(rockSwitcher == null) {            
            rockSwitcher = new GUIImage(
                    switcherScreenBlocks[1],
                    new Coordinate2D(Helpers.SWITCHING_SCREENSPACE_X, Helpers.SWITCHING_SCREENSPACE_Y).toVector(),
                    skin.getBallSkin().getSwitcherRockTexture(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode()
            );
            
            switcherMat.setTexture("atlas", 
                    guiAppState.getAssetManager().loadTexture(skin.getBallSkin().getSwitcherRockTexture()));
            rockSwitcher.getPicture().setMaterial(switcherMat.clone());
        }
        
        if(sandSwitcher == null) {            
            sandSwitcher = new GUIImage(
                    switcherScreenBlocks[2],
                    new Coordinate2D(Helpers.SWITCHING_SCREENSPACE_X, Helpers.SWITCHING_SCREENSPACE_Y).toVector(),
                    skin.getBallSkin().getSwitcherSandTexture(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode()
            );
            
            switcherMat.setTexture("atlas", 
                    guiAppState.getAssetManager().loadTexture(skin.getBallSkin().getSwitcherSandTexture()));
            sandSwitcher.getPicture().setMaterial(switcherMat.clone());
        }
        
        if(grassSwitcher == null) {            
            grassSwitcher = new GUIImage(
                    switcherScreenBlocks[3],
                    new Coordinate2D(Helpers.SWITCHING_SCREENSPACE_X, Helpers.SWITCHING_SCREENSPACE_Y).toVector(),
                    skin.getBallSkin().getSwitcherGrassTexture(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode()
            );
            
            switcherMat.setTexture("atlas", 
                    guiAppState.getAssetManager().loadTexture(skin.getBallSkin().getSwitcherGrassTexture()));
            grassSwitcher.getPicture().setMaterial(switcherMat.clone());
        }
        
        String blankTexturePath = skin.getBlankTexture().getKey().getName();
        
        if(plainSwitchButton == null) {            
            plainSwitchButton = new GUIButton(
                    switcherScreenBlocks[0],
                    new Coordinate2D(Helpers.SWITCHING_SCREENSPACE_X, Helpers.SWITCHING_SCREENSPACE_Y).toVector(),
                    "", 
                    blankTexturePath,
                    blankTexturePath,
                    guiAppState.getGuiFont(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode(),
                    guiAppState.getInteractiveGUIsList()
            ) {
                @Override
                public void execFunction() { playerAppState.switchForm(EntityBall.FORM_PLAIN); }
            };
        }
        
        if(rockSwitchButton == null) {            
            rockSwitchButton = new GUIButton(
                    switcherScreenBlocks[1],
                    new Coordinate2D(Helpers.SWITCHING_SCREENSPACE_X, Helpers.SWITCHING_SCREENSPACE_Y).toVector(),
                    "", 
                    blankTexturePath,
                    blankTexturePath,
                    guiAppState.getGuiFont(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode(),
                    guiAppState.getInteractiveGUIsList()
            ) {
                @Override
                public void execFunction() { playerAppState.switchForm(EntityBall.FORM_ROCK); }
            };
        }
        
        if(sandSwitchButton == null) {            
            sandSwitchButton = new GUIButton(
                    switcherScreenBlocks[2],
                    new Coordinate2D(Helpers.SWITCHING_SCREENSPACE_X, Helpers.SWITCHING_SCREENSPACE_Y).toVector(),
                    "", 
                    blankTexturePath,
                    blankTexturePath,
                    guiAppState.getGuiFont(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode(),
                    guiAppState.getInteractiveGUIsList()
            ) {
                @Override
                public void execFunction() { playerAppState.switchForm(EntityBall.FORM_SAND); }
            };
        }
        
        if(grassSwitchButton == null) {            
            grassSwitchButton = new GUIButton(
                    switcherScreenBlocks[3],
                    new Coordinate2D(Helpers.SWITCHING_SCREENSPACE_X, Helpers.SWITCHING_SCREENSPACE_Y).toVector(),
                    "", 
                    blankTexturePath,
                    blankTexturePath,
                    guiAppState.getGuiFont(),
                    guiAppState.getAssetManager(), 
                    guiAppState.getGuiConjunctionNode(),
                    guiAppState.getInteractiveGUIsList()
            ) {
                @Override
                public void execFunction() { playerAppState.switchForm(EntityBall.FORM_GRASS); }
            };
        }
        
        attachElement(plainSwitcher);
        attachElement(rockSwitcher);
        attachElement(sandSwitcher);
        attachElement(grassSwitcher);
        attachElement(plainSwitchButton);
        attachElement(rockSwitchButton);
        attachElement(sandSwitchButton);
        attachElement(grassSwitchButton);
        
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
