/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball.shape;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import lombok.Getter;

/**
 *
 * @author lorenzo
 */
public class Polygon extends Mesh {
    @Getter protected Vector3f[] vertices;
    protected Vector2f[] texCoord;
    protected short[] indexes;
    
    public Polygon(Vector3f v0,Vector3f v1,Vector3f v2,Vector3f v3) {
        updateMesh(v0,v1,v2,v3);
    }
    
    public void updateMesh(Vector3f v0,Vector3f v1,Vector3f v2,Vector3f v3) {
        vertices=new Vector3f[4];
        vertices[0]=v0;
        vertices[1]=v1;
        vertices[2]=v2;
        vertices[3]=v3;
        
        texCoord = new Vector2f[4];
        texCoord[0] = new Vector2f(0,0);
        texCoord[1] = new Vector2f(1,0);
        texCoord[2] = new Vector2f(0,1);
        texCoord[3] = new Vector2f(1,1);
        
        short[] temp_indexes = { 2,0,1, 
                    1,3,2 };
                    
        indexes=temp_indexes.clone();     
        
        setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        setBuffer(Type.Index,    3, BufferUtils.createShortBuffer(indexes));
        updateBound();
    }
}
