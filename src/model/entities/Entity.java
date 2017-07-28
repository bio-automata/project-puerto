package model.entities;

import java.util.HashMap;
import java.util.Map;

import model.system.collections.EntityPackager;

/**
  
 */
public class Entity {
    private Map<String,String> textVariables;
    private Map<String,Double> numericVariables;
    private Map<String,Entity> dependences;
    private EntityPackager entityPackager;

    public Entity(){
    	this.textVariables = new HashMap<>(); 
    	this.numericVariables = new HashMap<>();
    	this.dependences = new HashMap<>();
    	this.entityPackager = new EntityPackager();
    }    

    
    public void setTextVariable(String variable, String value){
        this.textVariables.put(variable, value);
    }

    public String getTextVariable(String variable){
        if (this.textVariables.containsKey(variable)){
            return this.textVariables.get(variable);
        }

        return null;
    }


    public void setNumericVariable(String variable, int value){
        this.numericVariables.put(variable, (double)value);
    }
    
    public void setNumericVariable(String variable, Double value){
        this.numericVariables.put(variable, value);
    }

    public void incrementVariable(String variable, int value){
        this.numericVariables.put(variable, this.numericVariables.get(variable)+value);
    }
    
    public void incrementVariable(String variable, Double value){
        this.numericVariables.put(variable, this.numericVariables.get(variable)+value);
    }

    public Double getNumericVariable(String variable){
        if (this.numericVariables.containsKey(variable)){
            return this.numericVariables.get(variable);
        }

        return null;
    }
    
    
    //entity packeger
    public void addEntityInDataStructure(int structure, String key, Entity entity){
        this.entityPackager.addEntity(structure, key, entity);
    }

    public Entity getEntityFromDataStructure(int structure, String key){
        return this.entityPackager.getEntity(structure,key);
    }
    
    public Entity getEntityFromDataStructure(int structure, String key, int id){
        return this.entityPackager.getEntity(structure,key,id);
    }        
    
       
    public Boolean hasEntityAvailableInDataStructure(String key){
    	return this.entityPackager.available(key);
    }
    
    //-------------------------------------------//
    
    
    public void setDependence(String dependence, Entity entity){
        this.dependences.put(dependence, entity);
    }

    public Entity getDependence(String dependence){
        if (this.dependences.containsKey(dependence)){
            return this.dependences.get(dependence);
        }

        return null;
    }
}

