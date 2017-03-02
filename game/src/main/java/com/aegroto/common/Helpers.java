/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.common;

import com.jme3.app.Application;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.concurrent.Callable;
import lombok.Getter;


public final class Helpers {
    @Getter private static float 
            terrainChunkSize,
            terrainSlopes,
            maxHeight,
            minHeight,
            ballSize,
            ballMaxX,
            ballMinX,
            epsilon,
            baseTextSize;
    
    @Getter private static Vector3f surfaceOffset,startVector;
    
    public static final int UPDATE_TIME=10;
    
    public static final float 
            INITIAL_SPEED=2f,
            
            INITIAL_CHANGE_SURFACE_VARIATION=.005f,   
            CHANGE_SURFACE_VARIATION_ENHANCING=.01f,
            
            INITIAL_PICKUP_SPAWNING_VARIATION=.00005f,   
            PICKUP_SPAWNING_VARIATION_ENHANCING=.0001f,
            
            INITIAL_PLAYER_SPEED=1.75f,
            MAX_BALL_SPEED=4.25f,
            MIN_BALL_SPEED=1.2f,
            
            SPEED_DIM_FACTOR_MIN=.99f,
            SPEED_DIM_FACTOR_MED=.985f,
            SPEED_DIM_FACTOR_HIGH=.98f,
            SPEED_DIM_FACTOR_HIGHER=.97f,
            SPEED_AUG_FACTOR=1.005f;
    
    private static Application app;
    
    public static void init(Application application,Vector3f startVector) {
        app=application;
        
        baseTextSize=Coordinate2D.xConvert(.1f);
        
        terrainChunkSize=Coordinate2D.xConvert(.075f);
        terrainSlopes=Coordinate2D.yConvert(.035f);
        
        maxHeight=Coordinate2D.yConvert(.3f);
        minHeight=Coordinate2D.yConvert(.2f);
        
        ballSize=Coordinate2D.yConvert(.08f);
        ballMaxX=Coordinate2D.xConvert(1f);
        ballMinX=-Coordinate2D.xConvert(.1f);
        
        epsilon=(Coordinate2D.xConvert(.01f)+Coordinate2D.yConvert(.01f))/2;
        
        surfaceOffset=new Vector3f(0f,Coordinate2D.yConvert(.065f),0f);
        
        Helpers.startVector=startVector;
    }
    
    public static boolean pointInArea(Vector2f point,Vector2f min,Vector2f max) {
        if((point.x>min.x&&point.x<max.x) &&
        (point.y>min.y&&point.y<max.y)) return true;
        else return false;
    }
    
    public static void safeAttachChild(final Node parent,final Spatial child) {
        app.enqueue(new Callable() {
            @Override
            public Object call() {
                parent.attachChild(child); 
                return null;
            }
        });
    }
    
    public static void safeRemoveChild(final Node parent,final Node child) {
        app.enqueue(new Callable() {
            @Override
            public Object call() {
                parent.detachChild(child); 
                return null;
            }
        });
    }
    
    public static void safeRemoveFromParent(final Spatial spatial) {
        app.enqueue(new Callable() {
            @Override
            public Object call() {
                spatial.removeFromParent();
                return null;
            }
        });
    }
    
    public static void safeLocalRotation(final Spatial spatial,final Quaternion rot) {
        app.enqueue(new Callable() {
            @Override
            public Object call() {
                spatial.setLocalRotation(rot);
                return null;
            }
        });
    }

    public static void safeLocalTranslation(final Spatial spatial,final Vector3f pos) {
        app.enqueue(new Callable() {
            @Override
            public Object call() {
                spatial.setLocalTranslation(pos);
                return null;
            }
        });
    }
    
    public static void safeLocalScale(final Spatial spatial,final Vector3f scale) {
        app.enqueue(new Callable() {
            @Override
            public Object call() {
                spatial.setLocalScale(scale);
                return null;
            }
        });
    }
    
    public static void safeLocalScale(final Spatial spatial,final float scale) {
        app.enqueue(new Callable() {
            @Override
            public Object call() {
                spatial.scale(scale);
                return null;
            }
        });
    }    
}
