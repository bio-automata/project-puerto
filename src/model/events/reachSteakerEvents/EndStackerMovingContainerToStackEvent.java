package model.events.reachSteakerEvents;

import java.util.Random;

import model.constants.EventConstants;
import model.constants.StackConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.events.Event;
import model.events.rtgEvents.EndRTGStackContainerEvent;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndStackerMovingContainerToStackEvent extends Event{
	Entity steaker;
    public EndStackerMovingContainerToStackEvent(Entity steaker, Systema system){
    	super(system);
    	this.steaker = steaker;
    }

    public void execute(){
        system.setClock(this.getDurationTime());
        

        //rtg seleciona uma pilha e agenda evento para movimentar até ela 
        Entity stak;
        //se não há pilhas no pátio, cria uma nova pilha
        if(!this.system.thereIsSet(SystemConstants.CONTAINER_STAKS)){
        	system.report("Não há pilhas no pátio, stacker criará a primeira pilha");
        	
        	//cria uma nova pilha e já empilha o container
        	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, 1.0);
        	
        	stak = new Entity();
        	stak.setNumericVariable(StackConstants.INDEX, 0);
        	stak.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 0);
        	stak.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
        	
        	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stak);
        }
        
        //se há pilha
        else{
        	//sorteia uma pilha
        	System.out.print("Numero de pilhas no pátio >>");
        	System.out.println(this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue());
        	
        	int stakQuantity = this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue();	//numero de pilhas no sistema
            int stakindex = new Random().nextInt(stakQuantity);
            
            stak = this.system.getEntityFromSet(SystemConstants.CONTAINER_STAKS, stakindex);
            System.out.println(stak.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS));
            
            if(stak.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)>=stak.getNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS)){
            	this.system.report("A pilha estava chiea, stacker criará nova pilha");
            	//cria uma nova pilha
            	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS)+1);
            	
            	stak = new Entity();
            	stak.setNumericVariable(StackConstants.INDEX, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS));
            	stak.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 0);
            	stak.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
            	
            	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stak);
            }
        }
            
        //se não há rtg na fila da pilha 
        if(!this.system.hasEntityAvailableInQueue("stacker waiting for stack "+stak.getNumericVariable(StackConstants.INDEX))){
        	stak.setDependence("stacker", this.steaker);
        	Event event = new EndStackerStackContainerEvent(stak, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_STACKING_CONTAINER_EVENT));
            this.system.agendFutureEvent(event);
        }
        //do contrário enfileira rtg 
        else{
        	this.system.addEntityInQueue("stacker waiting for stack "+stak.getNumericVariable(StackConstants.INDEX), steaker);
        }       

    }
}
