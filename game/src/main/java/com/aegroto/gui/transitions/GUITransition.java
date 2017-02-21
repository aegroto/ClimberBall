/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.gui.transitions;

/**
 *
 * @author lorenzo
 */
public abstract class GUITransition {
    protected boolean finished;
    
    public abstract boolean update();
    protected void onFinish() { }
}
