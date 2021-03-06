/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.state;

import com.aegroto.climberball.chunk.TerrainChunk;
import com.aegroto.climberball.entity.EntityBall;
import com.aegroto.climberball.entity.pickup.EntityPickup;
import com.aegroto.climberball.menu.InGameMenu;
import com.aegroto.climberball.skin.Skin;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import com.aegroto.gui.states.GuiAppState;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javafx.scene.input.MouseButton;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author lorenzo
 */
public final class PlayerAppState extends BaseAppState implements ActionListener {
    private EntityBall ball;
    private final Skin skin;
    private final Node rootNode;
    protected LinkedList<TerrainChunk> chunkList;
    @Getter protected ArrayList<EntityPickup> pickupList;
    protected ScheduledThreadPoolExecutor executor;

    protected SoundAppState soundAppState;
    protected GuiAppState guiAppState;

    @Getter
    @Setter
    protected boolean gameLost = false;

    private Vector3f targetPos;
    //protected float xSpeed, ySpeed;

    @Getter
    protected int score;

    public PlayerAppState(Node rootNode,
            ScheduledThreadPoolExecutor executor,
            LinkedList<TerrainChunk> chunkList,
            ArrayList<EntityPickup> pickupList,
            GuiAppState guiAppState,
            Skin skin) {
        this.rootNode = rootNode;
        this.skin = skin;
        this.executor = executor;
        this.chunkList = chunkList;
        this.pickupList = pickupList;
        this.guiAppState = guiAppState;
    }

    @Override
    protected void initialize(Application app) {
        ball = new EntityBall(rootNode, app.getAssetManager(), skin);

        ball.setPos(new Vector2f(0f, chunkList.getFirst().getTargetVector().y));

        soundAppState = app.getStateManager().getState(SoundAppState.class);

        app.getInputManager().addListener(this, "Touch");
        app.getInputManager().addMapping("Touch", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    }

    @Override
    protected void cleanup(Application app) {
        ball.destroy();
    }

    @Override
    protected void onEnable() {
        ball.setXSpeed(Helpers.INITIAL_PLAYER_SPEED);
        ball.setYSpeed(Helpers.INITIAL_PLAYER_SPEED * .5f);

        //executor.execute(asynchronousTick);
    }

    @Override
    protected void onDisable() {

    }

    private boolean keepUpdating = true;
    private TerrainChunk lastChunk;

    /*private final Runnable asynchronousTick = new Runnable() {
        @Override
        public void run() {
            //try {  
            if (!gameLost) {
                final TerrainChunk chunk = chunkList.get((int) (ball.getPos().x / Helpers.getTerrainChunkSize()) + 1);

                ball.setXSpeed(chunk.elaborateSpeedOnSurface(ball.getXSpeed(), ball.getCurrentForm()));

                targetPos = chunk.getTargetVector();
                float xAdd = ball.getPos().x > Helpers.getBallMaxX() ? 0 : ball.getXSpeed(),
                        yAdd = FastMath.abs(ball.getPos().y - targetPos.y) > ball.getYSpeed()
                        ? ball.getPos().y > targetPos.y ? -ball.getYSpeed() : ball.getYSpeed()
                        : 0;

                ball.safeSetPos(new Vector2f(
                        ball.getPos().x + xAdd - Helpers.INITIAL_SPEED,
                        ball.getPos().y + yAdd
                ));

                if (ball.getPos().x <= Helpers.getBallMinX()) {
                    gameLost = true;
                }

                if (chunk != lastChunk) {
                    score++;
                    lastChunk = chunk;
                    //System.out.println("Score: "+score);
                }
            }

            if (keepUpdating) {
                executor.schedule(this, Helpers.UPDATE_TIME, TimeUnit.MILLISECONDS);
            }
        }
    };*/

    @Override
    public void update(float tpf) {
        ball.setRotationSpeed(ball.getXSpeed() * .075f);
        ball.update(tpf);
        
        if (!gameLost) {
            final TerrainChunk chunk;
            if(ball.getPos().x >= 0) 
                chunk = chunkList.get((int) (ball.getPos().x / Helpers.getTerrainChunkSize()));
            else 
                chunk = chunkList.getFirst();
            
            ball.setXSpeed(chunk.elaborateSpeedOnSurface(ball.getXSpeed(), ball.getCurrentForm()));

            targetPos = chunk.getTargetVector();
            float xAdd = ball.getPos().x > Helpers.getBallMaxX() ? 0 : ball.getXSpeed(),
                    yAdd = FastMath.abs(ball.getPos().y - targetPos.y) > ball.getYSpeed()
                    ? ball.getPos().y > targetPos.y ? -ball.getYSpeed() : ball.getYSpeed()
                    : 0;

            ball.safeSetPos(new Vector2f(
                    ball.getPos().x + xAdd - Helpers.INITIAL_SPEED,
                    ball.getPos().y + yAdd
            ));

            if (ball.getPos().x <= Helpers.getBallMinX()) {
                gameLost = true;
            }

            if (chunk != lastChunk) {
                score++;
                lastChunk = chunk;
            }
        }
    }

    public void useSecondChance() {
        gameLost = false;
        ball.safeSetPos(new Vector2f(0f, chunkList.getFirst().getTargetVector().y));
        
        ball.switchForm(EntityBall.FORM_PLAIN);
        
        ball.setXSpeed(Helpers.INITIAL_PLAYER_SPEED * 7.5f);
    }
    
    public void switchForm(byte form) {
        ball.switchForm(form);
        
        soundAppState.stopSound("effect_switch");
        soundAppState.playSound("effect_switch");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch (name) {
            case "Touch":
                if (!isPressed && !gameLost) {
                    // boolean canSwitchForm = true;                    
                    Vector2f mousePos = getApplication().getInputManager().getCursorPosition();
                    
                    for(EntityPickup pickup:pickupList) {
                        if(Helpers.pointInArea(mousePos,
                                               pickup.getPickupZoneMin(),
                                               pickup.getPickupZoneMax()) &&
                            !pickup.isPicked()) {
                            guiAppState.getMenu(InGameMenu.class)
                                    .showInfoText("Whoa! You've got a " + pickup.getName() + " !");
                            pickup.applyOnBall(ball);
                            
                            // canSwitchForm = false;
                            break;
                        }
                    }
                    
                    /*if(canSwitchForm) {
                        if(Helpers.pointInArea(mousePos, switchingScreenBlocks[0], switchingScreenBlocks[1]))
                            ball.switchForm(EntityBall.FORM_PLAIN);
                        else if(Helpers.pointInArea(mousePos, switchingScreenBlocks[2], switchingScreenBlocks[3]))
                            ball.switchForm(EntityBall.FORM_ROCK);
                        else if(Helpers.pointInArea(mousePos, switchingScreenBlocks[4], switchingScreenBlocks[5]))
                            ball.switchForm(EntityBall.FORM_SAND);
                        else if(Helpers.pointInArea(mousePos, switchingScreenBlocks[6], switchingScreenBlocks[7]))
                            ball.switchForm(EntityBall.FORM_GRASS);

                        soundAppState.stopSound("effect_switch");
                        soundAppState.playSound("effect_switch");
                    }*/
                }
                break;
        }
    }
}
