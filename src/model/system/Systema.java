package model.system;

import java.util.HashMap;
import java.util.Map;



import model.entities.Entity;
import model.events.Event;
import model.random.Random;
import model.random.time.RandomTimeGenerator;
import model.random.time.TimeDistribution;
import model.system.collections.EntityCollection;
import model.system.collections.EntityList;
import model.system.collections.EntityQueueSet;
import model.system.collections.EntitySet;

/**
    Systema

    entidades:

    wharf
    equip
    cranes

    ships

 */

public class Systema {
    //hash Table com as variaveis de entrada do sistema
	private static Systema instance = null;

    private double clock;                       //relógio do sistema

    private Map<String,Double> variables;
    private EntitySet entitySet;                //conjunto de entidades do sistema
    private EntityQueueSet entityQueueSet;      //conjunto de filas do sistema
    private FutureEventList futureEventList;    //FEL
    private boolean simulating;                 //flag de status do sistema
    
    public RandomTimeGenerator randomTimeGenerator;
    public Random random;

    private Systema(){
        this.clock = 0;
        this.variables = new HashMap<>();
        this.entitySet = new EntitySet();
        this.entityQueueSet = new EntityQueueSet();
        this.futureEventList = new FutureEventList();
        this.simulating = false;
        this.random = new Random();
        this.randomTimeGenerator = new RandomTimeGenerator();
    }
    
    public static Systema getInstance(){
    	if(instance==null){
    		instance = new Systema();
    	}
    	
    	return instance;
    }

    
    public double getClock() {
        return clock;
    }

    public void setClock(double clock) {
        this.clock = clock;
    }

    public void incrementClock(double increment) {
        this.clock = this.clock+increment;
    }


    /* Metodos para manipular as variaveis do sistema */
    public void setVariable(String variable, Double value){
        this.variables.put(variable, value);
    }

    public void incrementVariable(String variable, Double value){
        this.variables.put(variable, this.variables.get(variable)+value);
    }

    public Double getVariable(String variable){
        if (this.variables.containsKey(variable)){
            return this.variables.get(variable);
        }

        return null;
    }

    /*manipulação da entidades do sistema*/


    public EntitySet getEntitySet() {
        return entitySet;
    }

    public void setEntitySet(EntitySet entitySet) {
        this.entitySet = entitySet;
    }

    public EntityQueueSet getEntityQueueSet() {
        return entityQueueSet;
    }

    public void setEntityQueueSet(EntityQueueSet entityQueueSet) {
        this.entityQueueSet = entityQueueSet;
    }


    //lista de eventos futuros
    public void agendFutureEvent(Event event) {
        this.futureEventList.addEvent(event);
    }

    public Event getNextImediateEvent() {
        return futureEventList.getEvent();
    }

    public FutureEventList getFutureEventList() {
        return futureEventList;
    }

    public void setFutureEventList(FutureEventList futureEventList) {
        this.futureEventList = futureEventList;
    }


    public RandomTimeGenerator getRandomTimeGenerator(){
        return this.randomTimeGenerator;
    }
    
    
    
    
    
    
    //atalhos e manipulaçẽs direta do sistema
    /*
    public Entity getEntitys(String key){
        return model.system.getEntitySet().getEntity(key);
    }*/
    
    public void addTimeDistribution(String key, TimeDistribution distribution){
    	this.getRandomTimeGenerator().addTimeDistribution(key, distribution);
    }
    
    public Double getEventDuration(String key){
    	return this.getRandomTimeGenerator().getTime(key);
    }

    
    public void addEntityInEntitySet(String key, Entity entity){
        this.getEntitySet().addEntity(key, entity);
    }
    
    public void addEntityInQueue(String key, Entity entity){
        this.getEntityQueueSet().addEntity(key, entity);
    }

    
    public Entity getEntityFromSet(String key, int id){
        return this.getEntitySet().getCollection(key).getEntity(id);
    }
    
    public Entity getEntityFromQueue(String key){
        return this.getEntityQueueSet().getEntityQueue(key).getEntity();
    }

    private EntityList getQueue(String key){
        return this.getEntityQueueSet().getEntityQueue(key);
    }
    
    public Boolean hasEntityAvailableInQueue(String key){
    	EntityList entityList = this.getEntityQueueSet().getEntityQueue(key); 
    	
    	if(entityList!=null){
    		return this.getQueue(key).available();
    	}
    	else{
    		return false;
    	}
    }
    
    public boolean thereIsSet(String key){
    	EntityCollection entityCollection = this.getEntitySet().getCollection(key); 
    	
    	//return this.getEntitySet()
    	if(entityCollection!=null){
    		//return this.getQueue(key).available();
    		return true;
    	}
    	else{
    		return false;
    	}
    }


    //-------------------------------------------//

    public void startSimulation(){
        this.simulating = true;
    }

    public boolean simulating(){
        return this.simulating;
    }
    
    public void report(String news){
    	System.out.printf("%.4f: %s\n", this.getClock(), news);
    }
}
