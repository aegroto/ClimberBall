/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.skin;

import com.jme3.texture.Texture;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
@AllArgsConstructor
public class BallSkin {
    @Getter private final Texture
            corePlainTexture, coreRockTexture, coreSandTexture, coreGrassTexture,
            plainTexture, rockTexture, sandTexture, grassTexture,
            switcherPlainTexture, switcherRockTexture, switcherSandTexture, switcherGrassTexture;
}
