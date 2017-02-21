/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui.transitions;

import com.jme3.math.Vector2f;
import com.aegroto.gui.GUIElement;

/**
 *
 * @author lorenzo
 */
public class GUIBasicTransition extends GUITransition{
    protected final GUIElement element;
    protected final Vector2f targetPos;
    protected final float distanceTolerance;
    protected float speed;
    
    public GUIBasicTransition(GUIElement element, Vector2f targetPos,float speed) {
        this.element=element;
        this.targetPos=targetPos;
        this.speed=speed;
        
        this.distanceTolerance=speed;
        
        this.finished=false;
    }
    
    @Override
    public boolean update() {
        if(!finished) {
            if(element.getPos().distance(targetPos)>speed) {
                Vector2f newPos=element.getPos().clone();
                if(newPos.x<targetPos.x) newPos.x+=speed; else if(newPos.x>targetPos.x) newPos.x-=speed;
                if(newPos.y<targetPos.y) newPos.y+=speed; else if(newPos.y>targetPos.y) newPos.y-=speed;  
                
                element.setPos(newPos);
                if(speed>.2f) speed/=1.1f;
                
                finished=false;
            } else {
                element.setPos(targetPos);
                onFinish();
                finished=true;
            }
        }
        return finished;
    }   
}
