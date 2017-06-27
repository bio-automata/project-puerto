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

public class EntityList {
    public static int LIST = 0;
    public static int QUEUE = 1;
    public static int STACK = 2;

    private int policy;
    private List<Entity> entityList;

    public EntityList(){
        this.policy = this.LIST;
        this.entityList = new ArrayList<>();
    }

    public EntityList(int policy){
        this.policy = policy;
        this.entityList = new ArrayList<>();
    }

    public void addEntity(Entity entity){
        this.entityList.add(entity);
    }

    public void addEntity(int i, Entity entity){
        if(this.policy==this.LIST){
            this.entityList.add(i, entity);
        }
    }

    public Entity getEntity(){ /// rever o nome do metodo
        if(this.policy==this.STACK){
            return this.unstack();
        }
        else{
            return this.dequeue();
        }
    }

    public Entity getEntity(int i){
        if(this.policy==this.LIST){
            if(this.entityList.size()>=i){
                Entity entity = this.entityList.get(i);
                this.entityList.remove(i);
                return entity;
            }
            else{
                return null;
            }
        }
        else if(this.policy==this.QUEUE){
            return this.dequeue();
        }
        else if(this.policy==this.STACK){
            return this.unstack();
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

    //retorna se possui entidades
    public boolean available(){
        return this.entityList.size()>0;
    }
    
    public int size(){
    	return this.entityList.size();
    }
}
