/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui.states;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.scene.Node;
import java.util.ArrayList;
import lombok.Getter;
import com.aegroto.gui.GUIInteractiveElement;
import com.aegroto.gui.GUIKeyboard.GUIKeyboardButton;
import com.aegroto.gui.GUITextBar;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import com.aegroto.gui.menu.Menu;

/**
 *
 * @author lorenzo
 */
public class GuiAppState extends BaseAppState implements ActionListener,AnalogListener{
    protected InputManager inputManager;    
    protected AppStateManager stateManager;
        
    @Getter protected Node guiNode;
    @Getter protected BitmapFont guiFont;
    @Getter protected AssetManager assetManager;    
    
    @Getter protected ArrayList<GUIInteractiveElement> interactiveGUIsList = new ArrayList<>();
    protected ArrayList<Menu> menuList=new ArrayList<>();
    
    protected Runnable onSoftKeyboardShownCallback,onSoftKeyboardHiddenCallback;
    
    @Getter protected GUITextBar activeTextBar;
    //protected GUIKeyboard keyboard;
    
 
    public GuiAppState(BitmapFont guiFont,Node guiNode) {
        this.guiFont=guiFont;
        this.guiNode=guiNode;
    }
    
    @Override
    protected void initialize(Application app) {                   
        assetManager=getApplication().getAssetManager();
        stateManager=getApplication().getStateManager();
        inputManager=getApplication().getInputManager();        
        
        registerBaseInput();        
                
        inputManager.setSimulateMouse(true);
        inputManager.setSimulateKeyboard(false);
        
        //keyboard=new GUIKeyboard(aM,gF,gN,interactiveGUIsList);
    }

    @Override
    protected void cleanup(Application aplctn) { }
    
    
    public void registerBaseInput() {
        //INPUTS
        String[] letterMap = new String[37];
        letterMap[0]="q"; letterMap[1]="w"; letterMap[2]="e";
        letterMap[3]="r"; letterMap[4]="t"; letterMap[5]="y";
        letterMap[6]="u"; letterMap[7]="i"; letterMap[8]="o";
        letterMap[9]="p"; letterMap[10]="a"; letterMap[11]="s";
        letterMap[12]="d"; letterMap[13]="f"; letterMap[14]="g";
        letterMap[15]="h"; letterMap[16]="j"; letterMap[17]="k";
        letterMap[18]="l"; letterMap[19]="z"; letterMap[20]="x";
        letterMap[21]="c"; letterMap[22]="v"; letterMap[23]="b";
        letterMap[24]="n"; letterMap[25]="m"; letterMap[26]=" ";
        letterMap[27]="0"; letterMap[28]="1"; letterMap[29]="2";
        letterMap[30]="3"; letterMap[31]="4"; letterMap[32]="5";
        letterMap[33]="6"; letterMap[34]="7"; letterMap[35]="8"; 
        letterMap[36]="9";
        Map<String,Integer> keyMap = new HashMap<>();
        keyMap.put(letterMap[0], KeyInput.KEY_Q);
        keyMap.put(letterMap[1], KeyInput.KEY_W);
        keyMap.put(letterMap[2], KeyInput.KEY_E);
        keyMap.put(letterMap[3], KeyInput.KEY_R);
        keyMap.put(letterMap[4], KeyInput.KEY_T);
        keyMap.put(letterMap[5], KeyInput.KEY_Y);
        keyMap.put(letterMap[6], KeyInput.KEY_U);    
        keyMap.put(letterMap[7], KeyInput.KEY_I); 
        keyMap.put(letterMap[8], KeyInput.KEY_O); 
        keyMap.put(letterMap[9], KeyInput.KEY_P); 
        keyMap.put(letterMap[10], KeyInput.KEY_A); 
        keyMap.put(letterMap[11], KeyInput.KEY_S); 
        keyMap.put(letterMap[12], KeyInput.KEY_D); 
        keyMap.put(letterMap[13], KeyInput.KEY_F); 
        keyMap.put(letterMap[14], KeyInput.KEY_G); 
        keyMap.put(letterMap[15], KeyInput.KEY_H); 
        keyMap.put(letterMap[16], KeyInput.KEY_J); 
        keyMap.put(letterMap[17], KeyInput.KEY_K); 
        keyMap.put(letterMap[18], KeyInput.KEY_L); 
        keyMap.put(letterMap[19], KeyInput.KEY_Z); 
        keyMap.put(letterMap[20], KeyInput.KEY_X); 
        keyMap.put(letterMap[21], KeyInput.KEY_C); 
        keyMap.put(letterMap[22], KeyInput.KEY_V); 
        keyMap.put(letterMap[23], KeyInput.KEY_B); 
        keyMap.put(letterMap[24], KeyInput.KEY_N); 
        keyMap.put(letterMap[25], KeyInput.KEY_M); 
        keyMap.put(letterMap[26],KeyInput.KEY_SPACE);
        keyMap.put(letterMap[27], KeyInput.KEY_0);    
        keyMap.put(letterMap[28], KeyInput.KEY_1);    
        keyMap.put(letterMap[29], KeyInput.KEY_2);    
        keyMap.put(letterMap[30], KeyInput.KEY_3);    
        keyMap.put(letterMap[31], KeyInput.KEY_4);    
        keyMap.put(letterMap[32], KeyInput.KEY_5);   
        keyMap.put(letterMap[33], KeyInput.KEY_6); 
        keyMap.put(letterMap[34], KeyInput.KEY_7); 
        keyMap.put(letterMap[35], KeyInput.KEY_8); 
        keyMap.put(letterMap[36], KeyInput.KEY_9); 
        for(byte i=0;i<letterMap.length;i++) {
            inputManager.addMapping("Key_"+letterMap[i],new KeyTrigger(keyMap.get(letterMap[i])));
            inputManager.addListener(this,"Key_"+letterMap[i]);
        }
        
        inputManager.addMapping("Delete",new KeyTrigger(KeyInput.KEY_BACK));
        inputManager.addMapping("LeftShift", new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addMapping("MouseClick",new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        //im.addMapping("MouseMove",new MouseAxisTrigger(MouseInput.AXIS_X,true),new MouseAxisTrigger(MouseInput.AXIS_Y,true));
        inputManager.addListener(this, "MouseClick");
        inputManager.addListener(this, "MouseMove");
        inputManager.addListener(this, "Delete");
        inputManager.addListener(this, "LeftShift");
    }    
    
    @Override
    public void update(float tpf) {
        for(Menu menu:menuList) {
            menu.update(tpf);
        }
    }

    @Override
    protected void onEnable() {
        /*iM.addMapping("MouseClick", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        
        iM.addListener(this,"MouseClick");*/                
        enableMenus();
    }

    @Override
    protected void onDisable() {        
        inputManager.removeListener(this);
        disableMenus();
    }

    private GUIInteractiveElement activeElement;
            
    @Override
    public void onAction(String name, boolean pressed, float value) {
        if(name.contains("Key_")) {
            if(!pressed) {
                String character=name.replace("Key_", "");
                if(activeTextBar!=null) activeTextBar.addChar(character);
            }
        } else switch(name) {
            case "Delete": {
                if(activeTextBar!=null && !pressed) activeTextBar.deleteLastChar();
                break;
            }
            case "MouseClick": try {
                for(GUIInteractiveElement elm:interactiveGUIsList) {     
                    if(elm.isActive() && elm.isClickedOn(inputManager.getCursorPosition())) {
                        if(activeElement==null || 
                            activeElement.getImg().getZOffset()<elm.getImg().getZOffset()) {
                            activeElement=elm;
                        }
                    }
                }
                if(activeElement!=null) {
                    if(pressed) {
                        activeElement.funcClicked(inputManager.getCursorPosition());
                        onElementClicked(activeElement);
                    } else {
                        activeElement.funcLeft(inputManager.getCursorPosition());
                        onElementLeft(activeElement);
                    }
                    activeElement=null;
                } else if(activeTextBar!=null && pressed && !(activeElement instanceof GUIKeyboardButton)) {
                    hideKeyboard();
                }
            } catch(ConcurrentModificationException ex) { }
            break;
        }
    }
    
    public void addMenu(Menu menu) { 
        if(!hasMenu(menu)) {
            menuList.add(menu); 
            menu.onAttach(this);
            menu.enableGUI(); 
        }
    }
    
    public void removeMenu(Menu menu) {
        if(hasMenu(menu)) {
            menuList.remove(menu);
            menu.onDetach();
            menu.disableGUI(); 
        }
    }
    
    public boolean hasMenu(Menu menu) { return menuList.contains(menu); }
    
    protected void enableMenus() {
        for(Menu menu:menuList) {
            menu.enableGUI();
        }
    }
    
    protected void disableMenus() {
        for(Menu menu:menuList) {
            menu.disableGUI();
        }        
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        switch(name) {
            case "MouseClick":for(GUIInteractiveElement elm:interactiveGUIsList) {
                if(elm.isClickedOn(inputManager.getCursorPosition())) {
                    if(activeElement==null || 
                       activeElement.getImg().getZOffset()<elm.getImg().getZOffset()) {
                        activeElement=elm;
                    }
                }
            }
            if(activeElement!=null) {
                activeElement.funcClickedContinously(inputManager.getCursorPosition());
                onElementClickedContinously(activeElement);
                activeElement=null;
            }
            break;
        }
    }
    
    private void showKeyboard(GUIInteractiveElement element) {
            if(activeTextBar!=null) {
                activeTextBar.setActiveTextBar(false);
            }
            activeTextBar=(GUITextBar) element;
            activeTextBar.setActiveTextBar(true);
            
            //keyboard.setActiveTextBar(activeTextBar);
            //keyboard.activate(true);
            
            if(onSoftKeyboardShownCallback!=null) onSoftKeyboardShownCallback.run();        
    }
    
    private void hideKeyboard() {
            activeTextBar.setActiveTextBar(false);
            activeTextBar=null;
            
            //keyboard.activate(false);

            if(onSoftKeyboardHiddenCallback!=null) onSoftKeyboardHiddenCallback.run();
    }
    
    protected void onElementClickedContinously(GUIInteractiveElement element) { }
    protected void onElementClicked(GUIInteractiveElement element) { }
    protected void onElementLeft(GUIInteractiveElement element) {
        if(element instanceof GUITextBar) {
            showKeyboard(element);
        } else if(activeTextBar!=null && !(element instanceof GUIKeyboardButton)) {
            hideKeyboard();
        }
    }
}
