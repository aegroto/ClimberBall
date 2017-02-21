/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.common;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public class Coordinate2D {
    public float x,y;
    @Getter private static AppSettings settings;
    
    public static void init(AppSettings _settings) {
        settings=_settings;
    }
    
    public Coordinate2D() { }
    
    public Coordinate2D(final float X,final float Y) {
        x=settings.getWidth()*X;
        y=settings.getHeight()*Y;      
    }
    
    public Vector2f fromVector(Vector2f vec) {
        return new Vector2f(
                vec.x/settings.getWidth(),
                vec.y/settings.getHeight());
    }
    
    public Vector2f toVector() {
        return new Vector2f(x,y);
    }
    
    public Vector3f toVector3f() {
        return new Vector3f(x,y,0);
    }
    
    public static float xConvert(final float X) {
        return settings.getWidth()*X;
    }
    
    public static float yConvert(final float Y) {
        return settings.getHeight()*Y;
    }
    
    public static float getFactorFromX(final float X) {
        //float xFac=800/X;
        return X/settings.getWidth();
    }

    public static float getFactorFromY(final float Y) {
        //float yFac=600/Y;
        return Y/settings.getHeight();
    }
    //public float getX() { return x; }
    //public float getY() { return y; }   
}
