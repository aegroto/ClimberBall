/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.state;

import com.aegroto.climberball.chunk.TerrainChunk;
import com.aegroto.climberball.entity.EntityBall;
import com.aegroto.climberball.skin.Skin;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
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
    protected ScheduledThreadPoolExecutor executor;
    
    protected SoundAppState soundAppState;
    
    @Getter @Setter protected boolean gameLost=false;
    
    private Vector3f targetPos;
    protected float xSpeed,ySpeed;
    
    @Getter protected int score;
    
    public PlayerAppState(Node rootNode,
            ScheduledThreadPoolExecutor executor,
            LinkedList<TerrainChunk> chunkList,
            Skin skin) {
        this.rootNode=rootNode;
        this.skin=skin;
        this.executor=executor;
        this.chunkList=chunkList;
    }
    
    @Override
    protected void initialize(Application app) {
        ball=new EntityBall(rootNode,app.getAssetManager(),skin);
        
        ball.setPos(new Vector2f(0f,chunkList.getFirst().getTargetVector().y));

        soundAppState=app.getStateManager().getState(SoundAppState.class);
        
        app.getInputManager().addListener(this, "Touch");
        app.getInputManager().addMapping("Touch", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    }

    @Override
    protected void cleanup(Application app) {
        ball.destroy();
    }

    @Override
    protected void onEnable() {
        xSpeed=Helpers.INITIAL_PLAYER_SPEED;
        ySpeed=Helpers.INITIAL_PLAYER_SPEED*.5f;
        
        executor.execute(asynchronousTick);
    }

    @Override
    protected void onDisable() {
        
    }  
    
    private boolean keepUpdating=true;
    private TerrainChunk lastChunk;
    
    private final Runnable asynchronousTick=new Runnable() {
        @Override
        public void run() {          
            //try {  
                if(!gameLost) {
                    final TerrainChunk chunk=chunkList.get((int) (ball.getPos().x / Helpers.getTerrainChunkSize()) + 1);

                    /*chunkList.get(chunkIndex).getDebugMaterial().setColor("Color", ColorRGBA.Red);
                    if(chunkIndex>0) chunkList.get(chunkIndex-1).getDebugMaterial().setColor("Color", ColorRGBA.Black);*/

                    xSpeed=chunk.elaborateSpeedOnSurface(xSpeed, ball.getCurrentForm());

                    targetPos=chunk.getTargetVector();
                    float xAdd=ball.getPos().x > Helpers.getBallMaxX() ? 0: xSpeed,
                          yAdd=FastMath.abs(ball.getPos().y-targetPos.y) > ySpeed ?
                                    ball.getPos().y > targetPos.y ? -ySpeed : ySpeed
                                    : 0;

                    ball.safeSetPos(new Vector2f(
                            ball.getPos().x+xAdd-Helpers.INITIAL_SPEED,
                            ball.getPos().y+yAdd
                    ));

                    if(ball.getPos().x <= Helpers.getBallMinX()) gameLost=true;

                    if(chunk != lastChunk) {
                        score++;
                        lastChunk=chunk;
                        getApplication().enqueue(new Callable<Object>() {
                            public Object call() {
                                ball.updateParticles(chunk, chunk.getSurfaceType());
                                return null;
                            }
                        });
                        //System.out.println("Score: "+score);
                    }               
                }
                
                if(keepUpdating) executor.schedule(this, Helpers.UPDATE_TIME, TimeUnit.MILLISECONDS);
            /*} catch(Exception e) {
                e.printStackTrace();
            }*/
        }          
    };
    
    @Override
    public void update(float tpf) {
        ball.setRotationSpeed(xSpeed*.075f);
        ball.update(tpf);
    }
    
    public void useSecondChance() {
        gameLost=false;
        ball.safeSetPos(new Vector2f(0f,chunkList.getFirst().getTargetVector().y));
        while(ball.getCurrentForm()!=0)
            ball.switchForm();
        xSpeed=Helpers.INITIAL_PLAYER_SPEED * 15f;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch(name) {
            case "Touch":
                if(!isPressed && !gameLost) {
                    ball.switchForm();
                    
                    soundAppState.stopSound("effect_switch");
                    soundAppState.playSound("effect_switch");
                }
                break;
        }
    }
}
