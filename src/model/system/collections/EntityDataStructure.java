package model.system.collections;

import java.util.ArrayList;
import java.util.List;

import model.entities.Entity;

/**
    EntityList

    this is a data structure designed for store
    consumable entity items, in other words,
    once an item was accessed by the get method
    it is purged from the entity list.
 */

public class EntityDataStructure {
    public static final int LIST = 0;
    public static final int QUEUE = 1;
    public static final int STACK = 2;
    public static final int SET = 3;

    private int policy;
    private List<Entity> entityList;

    public EntityDataStructure(){
        this.policy = LIST;
        this.entityList = new ArrayList<>();
    }

    public EntityDataStructure(int policy){
        this.policy = policy;
        this.entityList = new ArrayList<>();
    }

    public void add(Entity entity){
        this.entityList.add(entity);
    }

    public void add(int i, Entity entity){
        if(this.policy==LIST){
            this.entityList.add(i, entity);
        }
        else{
        	this.add(entity);
        }
    }

    public Entity get(){
        if(this.policy==QUEUE){
            return this.unstack();
        }
        else if(this.policy==STACK){
            return this.dequeue();
        }
        else{
        	return this.get(0);
        }
    }

    public Entity get(int i){
        if(this.policy==LIST){
            if(this.entityList.size()>=i){
                Entity entity = this.entityList.get(i);
                this.entityList.remove(i);
                return entity;
            }
        }
        else if(this.policy==SET){
        	if(this.entityList.size()>=i){
                return this.entityList.get(i);
            }
        }
        else{
        	this.get();
        }
        
        return null;
    }

    private Entity dequeue(){
        if(this.entityList.size()>0){
            Entity entity = this.entityList.get(0);
            this.entityList.remove(0);
            return entity;
        }
        else{
            return null;
        }
    }
    private Entity unstack(){
        if(this.entityList.size()>0){
            Entity entity = this.entityList.get(this.entityList.size()-1);
            this.entityList.remove(this.entityList.size()-1);
            return entity;
        }
        else{
            return null;
        }
    }

    
    public void remove(int i){
        this.entityList.remove(i);
    }
    
    public void remove(Entity entity){
    	for(int i=0;i<this.entityList.size();i++){
    		if(this.entityList.get(i).equals(entity)){
    			this.entityList.remove(i);
    		}
    	}
    }
    
    //retorna se possui entidades
    public boolean available(){
        return this.entityList.size()>0;
    }
}
