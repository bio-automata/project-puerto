package model.system;

import java.util.HashMap;
import java.util.Map;

import model.entities.Entity;
import model.events.Event;
import model.random.Random;
import model.random.RandomDistribution;
import model.random.StochasticFunctionSet;
import model.system.collections.EntityDataStructure;
import model.system.collections.EntityDataStructureSet;
import model.system.collections.EntityPackager;


/**
    System
 
    
 */

public class System {
	private static System instance = null;

	//system clock
    private double clock;
    private FutureEventList futureEventList;     //FEL
        
    //system variables
    private Map<String,String> textVariables;
    private Map<String,Double> numericVariables;
    
    //system entity collections, all system's data structures
    private EntityPackager entityPackager;
    
    //Stochastic generator
    public Random random;
    public StochasticFunctionSet stochasticFunctionSet;
    
    //simulaing tag
    private boolean isSimulating;
    private boolean verbose;

    
    
    private System(){
        this.clock = 0;
        this.textVariables = new HashMap<>();
        this.numericVariables = new HashMap<>();
        this.entityPackager = new EntityPackager();
        this.futureEventList = new FutureEventList();
        this.stochasticFunctionSet = new StochasticFunctionSet(new Random());
        this.isSimulating = false;
    }
    
    public static System getInstance(){
    	if(instance==null){
    		instance = new System();
    	}
    	
    	return instance;
    }

    
    public double getClock() {
        return clock;
    }

    public void setClock(double clock) {
        this.clock = clock;
    }

    
    /*sistem interface*/
    
    //sistem variables
    public void setTextVariable(String variable, String value){
        this.textVariables.put(variable, value);
    }

    public String getTextVariable(String variable){
        if (this.textVariables.containsKey(variable)){
            return this.textVariables.get(variable);
        }

        return null;
    }
    
    
    public void setNumericVariable(String variable, Double value){
        this.numericVariables.put(variable, value);
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

    
    //Future Event List
    public void agendEvent(Event event) {
        this.futureEventList.addEvent(event);
    }

    public void executeNextEvent() {
        futureEventList.getEvent().execute();
    }

    //Stochastic function set
    public void addStatisticalVariable(String key, RandomDistribution distribution){
    	this.stochasticFunctionSet.add(key, distribution);
    }
    
    public Double getStatisticalVariableValue(String key){
    	return this.stochasticFunctionSet.generate(key);
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
    
    public void startSimulation(){
        this.isSimulating = true;
    }

    public boolean isSimulating(){
        return this.isSimulating;
    }
    
    public void report(String news){
    	if(verbose){
    		java.lang.System.out.printf("%.4f: %s\n", this.getClock(), news);
    	}
    }
}