package com.aegroto.climberball;

import com.aegroto.climberball.menu.GameOverMenu;
import com.aegroto.climberball.menu.InGameMenu;
import com.aegroto.climberball.menu.OptionsMenu;
import com.aegroto.climberball.menu.StartMenu;
import com.aegroto.climberball.state.BackgroundAppState;
import com.aegroto.climberball.state.CacheAppState;
import com.aegroto.climberball.state.EnvironmentAppState;
import com.aegroto.climberball.state.PlayerAppState;
import com.aegroto.climberball.state.SkinAppState;
import com.aegroto.climberball.state.SoundAppState;
import com.aegroto.common.Coordinate2D;
import com.aegroto.common.Helpers;
import com.aegroto.gui.GUIButton;
import com.aegroto.gui.states.GuiAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

public class Main extends SimpleApplication {  
    
    public static void initializeCacheAppState(String cacheLocation) {        
        cacheAppState = new CacheAppState(cacheLocation);
    }
    
    private static CacheAppState cacheAppState;
    
    public static void main(String[] args) {
        initializeCacheAppState("cache.yaml");
        
        // cacheManager.saveCacheOnFile();
        
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setWidth(50*16);
        settings.setHeight(50*9);
        settings.setFrameRate(60);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    //STATES
    private static BackgroundAppState backgroundAppState;
    private static EnvironmentAppState environmentAppState;
    private static PlayerAppState playerAppState;
    private static SkinAppState skinAppState;
    private static GuiAppState guiAppState;
    private static SoundAppState soundAppState;
    
    //MENUS
    private static InGameMenu inGameMenu;
    private static StartMenu startMenu;
    private static OptionsMenu optionsMenu;
    private static GameOverMenu gameOverMenu;
    
    private static ScheduledThreadPoolExecutor executor;

    @Setter private static boolean androidLaunch = false;
    
    public static final class UpdateSwitches {
        public static boolean
            initCacheAppState=false,
            initSkinAppState=false,
            initSoundAppState=false,
            initGuiAppState=false,
            initBackgroundAppState=false,
            initEnvironmentAppState=false,
            
            initOptionsMenu=false,
            initStartMenu=false,
            initPlayerAppState=false,
            initGameOverMenu=false,
            //Various
            resetGame=false,
            hasSecondChance=true,
        
            loadOptions=false;
    }
    
    @Getter private static final UpdateSwitches UPDATE_SWITCHES = new UpdateSwitches();
    
    /*private static boolean 
            //Initialization  
            initCacheAppState=false,
            initSkinAppState=false,
            initSoundAppState=false,
            initGuiAppState=false,
            initBackgroundAppState=false,
            initEnvironmentAppState=false,
            
            initOptionsMenu=false,
            initStartMenu=false,
            initPlayerAppState=false,
            initGameOverMenu=false,
            //Various
            resetGame=false,
            hasSecondChance=true;
    
    private @Setter static boolean
            loadOptions=false;*/
    
    //private SimpleApplication app;
    
    private final Callable resetGameCallable=new Callable<Object>() {
        @Override
        public Object call() {                
            UPDATE_SWITCHES.resetGame = true;
            return null;
        }
    };
    
    @Setter private static Callable secondChanceCallable=new Callable<Object>() {
        @Override
        public Object call() {
            useSecondChance();
            return null;
        }
    };
        
    private static void useSecondChance() {
        playerAppState.useSecondChance();
        guiAppState.removeMenu(gameOverMenu);
        UPDATE_SWITCHES.hasSecondChance=false;
    }
    
    @Override
    public void simpleInitApp() {
        this.setDisplayStatView(false);
        
        // checkAndRepairCache(cacheManager);
        
        inputManager.setSimulateMouse(true);
        
        executor=(ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        if(!androidLaunch) 
            assetManager.registerLocator("src/main/resources/_assets", FileLocator.class);
        
        Coordinate2D.init(settings);  
        Helpers.init(this, new Vector3f(-Coordinate2D.xConvert(.1f),Coordinate2D.yConvert(.5f),0));   
        
        flyCam.setEnabled(false);
        
        stateManager.attach(cacheAppState);
        
        UPDATE_SWITCHES.initSkinAppState=true; 
        UPDATE_SWITCHES.initSoundAppState=true;
        UPDATE_SWITCHES.initGuiAppState=true;
        UPDATE_SWITCHES.initBackgroundAppState=true;
        UPDATE_SWITCHES.initEnvironmentAppState=true;
        UPDATE_SWITCHES.initStartMenu=true;
        
        UPDATE_SWITCHES.loadOptions=true;
    }
    
    @Override 
    public void simpleUpdate(float tpf) {                
        //Regular update code here
        
        if(stateManager.hasState(playerAppState)) {
            if(playerAppState.isGameLost()) {                
                //stateManager.detach(playerAppState);
                if(!UPDATE_SWITCHES.initGameOverMenu && 
                   !guiAppState.hasMenu(gameOverMenu) &&
                   !guiAppState.hasMenu(optionsMenu)) UPDATE_SWITCHES.initGameOverMenu=true;
            } else {
                inGameMenu.setScoreText(playerAppState.getScore());
            }
        }
    }
    
    private void resetGame() {    
        //STATES
        if(stateManager.hasState(environmentAppState)) stateManager.detach(environmentAppState);
        if(stateManager.hasState(playerAppState)) stateManager.detach(playerAppState);
        
        UPDATE_SWITCHES.initEnvironmentAppState=true;
        UPDATE_SWITCHES.initPlayerAppState=true;
        
        //HUD RESET
        if(guiAppState.hasMenu(gameOverMenu)) guiAppState.removeMenu(gameOverMenu);        
        if(guiAppState.hasMenu(startMenu)) guiAppState.removeMenu(startMenu);
        if(guiAppState.hasMenu(inGameMenu)) guiAppState.removeMenu(inGameMenu);
        
        inGameMenu=new InGameMenu();
        guiAppState.addMenu(inGameMenu);
        
        UPDATE_SWITCHES.hasSecondChance = true;
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //STATES 
        if(UPDATE_SWITCHES.initSkinAppState) {
            skinAppState=new SkinAppState("base");
            stateManager.attach(skinAppState); 
            
            UPDATE_SWITCHES.initSkinAppState=false;
        } else if(UPDATE_SWITCHES.initSoundAppState) {
            soundAppState=new SoundAppState(guiNode,skinAppState.getCurrentSkinName());
            stateManager.attach(soundAppState); 
            
            UPDATE_SWITCHES.initSoundAppState=false;
        } else if(UPDATE_SWITCHES.initBackgroundAppState) {
            backgroundAppState = new BackgroundAppState(guiNode, skinAppState.getCurrentSkin());
            stateManager.attach(backgroundAppState);
            
            UPDATE_SWITCHES.initBackgroundAppState=false;
        } else if(UPDATE_SWITCHES.initEnvironmentAppState) {
            environmentAppState=new EnvironmentAppState(guiNode,executor,skinAppState.getCurrentSkin());
            stateManager.attach(environmentAppState);
            
            UPDATE_SWITCHES.initEnvironmentAppState=false;
        } else if(UPDATE_SWITCHES.initPlayerAppState) {
            playerAppState=new PlayerAppState(guiNode,
                                              executor,
                                              environmentAppState.getChunkList(),
                                              environmentAppState.getPickupList(),
                                              guiAppState,
                                              skinAppState.getCurrentSkin());
            stateManager.attach(playerAppState);
            
            UPDATE_SWITCHES.initPlayerAppState=false;
        } else if(UPDATE_SWITCHES.initGuiAppState) {                    
            guiAppState=new GuiAppState(skinAppState.getCurrentSkin().getGuiFont(), guiNode, executor);
            stateManager.attach(guiAppState); 
            
            UPDATE_SWITCHES.initGuiAppState = false;          
            
            UPDATE_SWITCHES.initOptionsMenu = true;
            UPDATE_SWITCHES.initStartMenu = true;
            UPDATE_SWITCHES.loadOptions = true;
        } else if(UPDATE_SWITCHES.resetGame) {
            resetGame();
            
            UPDATE_SWITCHES.resetGame=false;
        }
        
        //MENUS
        else if(UPDATE_SWITCHES.initOptionsMenu) {            
            optionsMenu = new OptionsMenu(cacheAppState.getCacheManager());
            
            UPDATE_SWITCHES.initOptionsMenu = false;
        } else if(UPDATE_SWITCHES.initStartMenu) {
            startMenu=new StartMenu(resetGameCallable,this,optionsMenu);
            guiAppState.addMenu(startMenu);
            
            UPDATE_SWITCHES.initStartMenu=false;
        } else if(UPDATE_SWITCHES.initGameOverMenu) {
            gameOverMenu=new GameOverMenu(
                    resetGameCallable,
                    secondChanceCallable,
                    optionsMenu,
                    this,
                    cacheAppState.getCacheManager(),
                    playerAppState.getScore(),
                    UPDATE_SWITCHES.hasSecondChance);               
            guiAppState.addMenu(gameOverMenu);
            
            UPDATE_SWITCHES.initGameOverMenu=false;
        } else if(UPDATE_SWITCHES.loadOptions) { 
            double effectsVolume = 0, musicVolume = 0;
            
            if(cacheAppState.getCacheBlock("EffectsVolume") instanceof Float) {
                effectsVolume = (float) cacheAppState.getCacheBlock("EffectsVolume");
                musicVolume = (float) cacheAppState.getCacheBlock("MusicVolume");
            } else if(cacheAppState.getCacheBlock("EffectsVolume") instanceof Double) {
                effectsVolume = (double) cacheAppState.getCacheBlock("EffectsVolume");
                musicVolume = (double) cacheAppState.getCacheBlock("MusicVolume");
            }
            
            soundAppState.setEffectsVolume(effectsVolume);
            soundAppState.setMusicVolume(musicVolume);
        }
     }
    
    @Override
    public void destroy() {
        super.destroy();
        executor.shutdownNow();
        System.gc();
    }
}
