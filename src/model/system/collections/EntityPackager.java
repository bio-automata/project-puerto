package model.system.collections;

import java.util.HashMap;
import java.util.Map;

import model.entities.Entity;

public class EntityPackager {
	private Map<Integer,EntityDataStructureSet> packages;
	
	public EntityPackager(){
		this.packages = new HashMap<>();        
        this.packages.put(EntityDataStructure.LIST, new EntityDataStructureSet(EntityDataStructure.LIST));
        this.packages.put(EntityDataStructure.SET, new EntityDataStructureSet(EntityDataStructure.SET));
        this.packages.put(EntityDataStructure.QUEUE, new EntityDataStructureSet(EntityDataStructure.QUEUE));
        this.packages.put(EntityDataStructure.STACK, new EntityDataStructureSet(EntityDataStructure.STACK));
	}
	
	
	public void addEntity(int structure, String key, Entity entity) {
		this.packages.get(structure).addEntity(key, entity);
	}
	
	public Entity getEntity(int structure, String key) {
        return this.packages.get(structure).getEntity(key);
    }
	
	public Entity getEntity(int structure, String key, int id) {
        return this.packages.get(structure).getEntity(key,id);
    }
	
	public boolean contains(String key){
    	for(int structure=0; structure<4;structure++){
        	if(this.packages.get(structure).contains(key)){
        		return true;
        	}
    	}
    	
    	return false;
    }
	
	public boolean available(String key) {
		for(int structure=0; structure<4;structure++){
        	if(this.packages.get(structure).contains(key)){
        		return this.packages.get(structure).available(key);
        	}
    	}
    	
    	return false;
    }
}
