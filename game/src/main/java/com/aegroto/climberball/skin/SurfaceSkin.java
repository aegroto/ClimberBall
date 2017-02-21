/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.skin;

import com.jme3.material.Material;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
@AllArgsConstructor
public final class SurfaceSkin {
    @Getter private final byte id;    
    @Getter private final Material material;        
}