/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aegroto.climberball;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author lorenzo
 */
public class CacheManager {    
    @Getter @Setter private File cacheFile;
    private Yaml yaml;
    
    private HashMap<String, Object> cacheBlocks;
    
    public CacheManager(String location) {
        cacheFile = new File(location);
        yaml = new Yaml();  
            
        try {
            cacheBlocks = yaml.loadAs(new FileInputStream(cacheFile), HashMap.class);
        } catch(FileNotFoundException e) {
            System.err.println("Unable to load cache blocks from current cache file");
        }
        
        if(cacheBlocks == null)             
            cacheBlocks = new HashMap<>();
        
        try {
            yaml.load(new FileInputStream(cacheFile));
        } catch (FileNotFoundException ex) {
            try {
                cacheFile.createNewFile();
            } catch (IOException ex1) {
                System.err.println("ERROR! Unable to create or write on cache file");
            }
        }
    }
    
    public void setCacheBlock(String id,Object obj) {
        cacheBlocks.put(id, obj);
    }
    
    public Object getCacheBlock(String id) {
        return cacheBlocks.get(id);
    }
    
    public boolean saveCacheOnFile() {
        try {
            FileWriter writer = new FileWriter(cacheFile);            
            yaml.dump(cacheBlocks, writer);            
            writer.close();
        } catch(IOException e) {
            return false;
        }
        
        return true;
    }
    
}
