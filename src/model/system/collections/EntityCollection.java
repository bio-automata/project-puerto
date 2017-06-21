package model.system.collections;


import java.util.ArrayList;
import java.util.List;

import model.entities.Entity;

/**
    Entitycolection

    this is a data structure designed for store
    non consumable entity items, in other words,
    an iten persists into data estructure after 
    it was accessed by the get method
 */

public class EntityCollection {
    private List<Entity> entityCollection;

    public EntityCollection(){
        this.entityCollection = new ArrayList<>();
    }

    public void addEntity(Entity entity){
        this.entityCollection.add(entity);
    }

    public Entity getEntity(int i){
        return this.entityCollection.get(i);
    }

    public Entity destroyEntity(int i){
        return this.entityCollection.remove(i);
    }
}