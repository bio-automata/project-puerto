package model.system.collections;

import java.util.HashMap;
import java.util.Map;
import model.entities.Entity;

/**
 EntityDataStructureSet is an abstraction model for a set
 of data structures for store model.system's entities

 Each data structures is an especial form of an EntityDataStructure
 it behaviors according with it policy
 
 */

public class EntityDataStructureSet {
    //
	private int policy;
	private Map<String, EntityDataStructure> entityDataStructures;

    // constructor
    public EntityDataStructureSet(int policy){
    	this.policy = policy;
        this.entityDataStructures = new HashMap<>();
    }

    //add entity in a data structure, if key don't exists, create a new data structure for this key
    public void addEntity(String key, Entity entity){
        //verify if key exists, if not, create new key
        if (!this.entityDataStructures.containsKey(key)){
            this.entityDataStructures.put(key, new EntityDataStructure(this.policy));
        }
        
        //add entity
        this.entityDataStructures.get(key).add(entity);
    }


    public Entity getEntity(String key){
        if (this.entityDataStructures.containsKey(key)){
            return this.entityDataStructures.get(key).get();
        }
        return null;
    }
    
    public Entity getEntity(String key, int id){
        if (this.entityDataStructures.containsKey(key)){
            return this.entityDataStructures.get(key).get(id);
        }
        return null;
    }

    public EntityDataStructure getEntityDataStructure(String key){
        return this.entityDataStructures.get(key);
    }
    
    
    public boolean contains(String key){
    	return this.entityDataStructures.containsKey(key);
    }
    
    public boolean available(String key){
        return this.entityDataStructures.get(key).available();
    }
}
