/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author lorenzo
 */
public final class SoundAppState extends BaseAppState {
    private final String soundFolder;
    private final Node rootNode;
    private final HashMap<String,AudioNode> soundList=new HashMap();
    
    @Getter private float musicVolume=0f,effectsVolume=0f;
    
    public SoundAppState(Node rootNode,String skinName) {
        this.rootNode=rootNode;
        soundFolder=skinName+"/sound/";
    }
    
    @Override
    protected void initialize(Application app) { 
        AudioNode background=new AudioNode(app.getAssetManager(), soundFolder+"music_background.ogg");
        background.setPositional(false);
        background.setLooping(true);
        background.setVolume((float) musicVolume);
        soundList.put("music_background", background);
        
        rootNode.attachChild(soundList.get("music_background"));
 
        loadSoundEffect("effect_switch",.4f);
        /*loadSoundEffect("plain");
        loadSoundEffect("rock");
        loadSoundEffect("sand");
        loadSoundEffect("grass");*/
    }
    
    private AudioNode loadSoundEffect(String name) {
        AudioNode node=new AudioNode(getApplication().getAssetManager(), soundFolder+name+".ogg");
        node.setPositional(false);
        node.setLooping(false);
        node.setUserData("RelativeVolume", 1f);
        soundList.put(name, node);    
        
        rootNode.attachChild(soundList.get(name));
        
        return soundList.get(name);
    }
    
    private AudioNode loadSoundEffect(String name,float volume) {
        AudioNode node=loadSoundEffect(name);
        node.setVolume(volume*effectsVolume);
        node.setUserData("RelativeVolume", volume);
        
        return node;
    }

    @Override
    protected void cleanup(Application app) { }

    @Override
    protected void onEnable() { 
        playSound("music_background");
    }
    
    public void setMusicVolume(double musicVolume) {
        this.musicVolume = (float) musicVolume;
        
        for(String soundKey:soundList.keySet()) {
            if(soundKey.split("_")[0].equals("music")) 
                soundList.get(soundKey).setVolume(this.musicVolume);
        }
    }
    
    public void setEffectsVolume(double effectsVolume) {
        this.effectsVolume = (float) effectsVolume;
        
        for(String soundKey:soundList.keySet()) {
            if(soundKey.split("_")[0].equals("effect")) 
                soundList.get(soundKey).setVolume(
                        (float) soundList.get(soundKey).getUserData("RelativeVolume") * this.effectsVolume);
        }
    }

    @Override
    protected void onDisable() { }
    
    public void playSound(String name) {
        soundList.get(name).play();
    }   
    
    public void stopSound(String name) {
        soundList.get(name).stop();
    }
    
    public void pauseSound(String name) {
        soundList.get(name).pause();
    }
    
    public void playSoundInstance(String name) {
        soundList.get(name).playInstance();
    }
}
