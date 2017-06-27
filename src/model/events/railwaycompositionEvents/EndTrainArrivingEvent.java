package model.events.railwaycompositionEvents;


import model.constants.EventConstants;
import model.constants.RainlwayConstants;
import model.constants.ShipConstants;
import model.entities.Entity;
import model.entities.ships.ContainerShipFactory;
import model.events.Event;
import model.events.equipEvents.EndEquipDockingEvent;
import model.events.reachSteakerEvents.EndStackerMovingContainerToRailwayEvent;
import model.random.Random;
import model.system.Systema;

/**
 TrainArrivingEvent

 */

public class EndTrainArrivingEvent extends Event{
    public EndTrainArrivingEvent(Systema system){
    	super(system);

    }

    public void execute(){
    	this.system.setClock(this.getOccurrenceTime());

    	/*
    	 	------chegada de navio---------
       		
       		sorteia o tipo do navio
    		sorteia a quantidade de containers entre 70 e 100 percent
    	*/
    	
        Entity train = new Entity();
    	int min = this.system.getVariable(EventConstants.TRAIN_MIN_WAGON_QUANTITY).intValue();
    	int max = this.system.getVariable(EventConstants.TRAIN_MAX_WAGON_QUANTITY).intValue();
    	int mean = min+new java.util.Random().nextInt(max-min);
    	
        train.setNumericVariable(RainlwayConstants.NUMBER_OF_WAGONS,mean);
        
        //reporta o que está acontecendo no sistema
        this.system.report("Chegada de um trem com "+train.getNumericVariable(RainlwayConstants.NUMBER_OF_WAGONS));
        
        
        if(this.system.hasEntityAvailableInQueue("railway")){
        	Entity railway = this.system.getEntityFromQueue("railway");
        	train.setDependence("railway", railway);
        	
        }
        else{
        	this.system.addEntityInQueue("train waiting railway", train);
        }
    }
}