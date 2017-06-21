package model.system.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.entities.Entity;

/**
 EntitySet is a class to group all model.system's model.entities
 in a set of model.entities collections
 */

public class EntitySet {
    private Map<String,EntityCollection> entitySet;

    public EntitySet(){
        this.entitySet = new HashMap<>();
    }

    public void addCollection (String key, EntityCollection collection){
        this.entitySet.put(key,collection);
        
    }

    public void addEntity(String key, Entity entity){
        // verifica se existe a fila
        if (this.entitySet.containsKey(key)){
            this.entitySet.get(key).addEntity(entity); // .addEntity(entity);
        }else{
            // adciona uma fila nova e ja adiciona a entity
        	this.entitySet.put(key, new EntityCollection());
            this.entitySet.get(key).addEntity(entity);
        }
    }

    public EntityCollection getCollection (String key){
        return this.entitySet.get(key);
    }
    public Entity getEntity(String key, int i){
        return this.getCollection(key).getEntity(i);
    }

    public void destroyCollection(int i){
        this.entitySet.remove(i);
    }
    public void destroyEntity(String key, int i){
        this.getCollection(key).destroyEntity(i);
    }
}
