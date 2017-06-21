package model.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dicus on 07/05/17.
 */
public class Entity {
    private Map<String,String> textVariables;
    private Map<String,Double> numericVariables;
    private Map<String,Entity> dependences;

    public Entity(){
    	
    	this.textVariables = new HashMap<>(); 
    	this.numericVariables = new HashMap<>();
    	this.dependences = new HashMap<>();
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
    
    
    public void setDependence(String dependence, Entity entity){
        this.dependences.put(dependence, entity);
    }

    public Entity getDependence(String dependence){
        if (this.textVariables.containsKey(dependence)){
            return this.dependences.get(dependence);
        }

        return null;
    }
}

