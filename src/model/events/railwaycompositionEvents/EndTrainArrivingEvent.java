package model.events.railwaycompositionEvents;


import model.constants.EventConstants;
import model.constants.RainlwayConstants;
import model.constants.ShipConstants;
import model.constants.StackConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.entities.ships.ContainerShipFactory;
import model.events.Event;
import model.events.equipEvents.EndEquipDockingEvent;
import model.events.reachSteakerEvents.EndStackerMovingContainerToRailwayEvent;
import model.events.reachSteakerEvents.EndStackerMovingContainerToStackEvent;
import model.events.reachSteakerEvents.EndStackerMovingToStackEvent;
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
        	this.system.report("Trem irá carregar");
        	Entity railway = this.system.getEntityFromQueue("railway");
        	train.setDependence("railway", railway);        	
        	this.system.addEntityInQueue("train loading", train);
        	
        	if(this.system.hasEntityAvailableInQueue("stacker")){
        		Entity stacker = this.system.getEntityFromQueue("stacker");
        		stacker.setDependence("train", train);
            	train.setDependence("stacker", stacker);

            	int stackQuantity;
            	int stackindex; 
            	int i;

            	i = 20;			//procura por 20 vezes
            	stackQuantity = this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue();	//numero de pilhas no sistema
            	while(i>0&&stackQuantity>0){
            		i--;        	
            		stackindex = new java.util.Random().nextInt(stackQuantity);            
            	    Entity stack = this.system.getEntityFromSet(SystemConstants.CONTAINER_STAKS, stackindex);
            	    stack.setDependence("stacker", stacker);
            	                
            	    if(stack.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)>0){
            	    	this.system.report("Chama o stacker");
            	    	double eventTimeDuration = this.system.getClock()+stack.getNumericVariable(StackConstants.INDEX)*10;
            	    	
            	    	//retira container da pilha
            	    	//agenda o evento de movimentar para a pilha com intenção de descarregar
            	    	Event event = new EndStackerMovingToStackEvent(stack, system);
            	    	event.setOccurrenceTime(eventTimeDuration);
            	    	break;
            	    }
            	    else{
            	    	if(i==0){
            	    		this.system.report("Não foram enconcatada nenhuma pilha, reach stacker ocioso");
            	    		//enfleira stacker
            	    		this.system.addEntityInQueue("stacker", stacker);
            	    		break;
            	    	}
            	    	this.system.report("A pilha estava vazia, stacker procurará outra pilha para desempilhar");
            	    }
            	}
        	}
        }
        else{
        	this.system.addEntityInQueue("train waiting railway", train);
        	this.system.report("Trem aguardando estaçãos de descarga");
        }
    }
}