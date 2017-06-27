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
public class EndStackerMovingToStackEvent extends Event{
	Entity stack;
    public EndStackerMovingToStackEvent(Entity steak, Systema system){
    	super(system);
    	this.stack = steak;
    }

    public void execute(){
        system.setClock(this.getDurationTime());

        //rtg seleciona uma pilha e agenda evento para movimentar até ela 
        Entity stack;
        //se não há pilhas no pátio, cria uma nova pilha
        if(!this.system.thereIsSet(SystemConstants.CONTAINER_STAKS)){
        	system.report("Não há pilhas no pátio, stacker criará a primeira pilha");
        	
        	//cria uma nova pilha e já empilha o container
        	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, 1.0);
        	
        	stack = new Entity();
        	stack.setNumericVariable(StackConstants.INDEX, 0);
        	stack.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 0);
        	stack.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
        	
        	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stack);
        }
        
        //se há pilha
        else{
        	//sorteia uma pilha
        	System.out.print("Numero de pilhas no pátio >>");
        	System.out.println(this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue());
        	
        	int stackQuantity = this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue();	//numero de pilhas no sistema
            int stackindex = new Random().nextInt(stackQuantity);
            
            stack = this.system.getEntityFromSet(SystemConstants.CONTAINER_STAKS, stackindex);
            System.out.println(stack.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS));
            
            if(stack.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)>=stack.getNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS)){
            	this.system.report("A pilha estava chiea, stacker criará nova pilha");
            	//cria uma nova pilha
            	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS)+1);
            	
            	stack = new Entity();
            	stack.setNumericVariable(StackConstants.INDEX, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS));
            	stack.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 0);
            	stack.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
            	
            	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stack);
            }
        }
            
        //se não há rtg na fila da pilha 
        if(!this.system.hasEntityAvailableInQueue("stacker waiting for stack "+stack.getNumericVariable(StackConstants.INDEX))){
        	stack.setDependence("stacker", this.stack);
        	Event event = new EndStackerStackContainerEvent(stack, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_STACKING_CONTAINER_EVENT));
            this.system.agendFutureEvent(event);
        }
        //do contrário enfileira rtg 
        else{
        	this.system.addEntityInQueue("stacker waiting for stack "+stack.getNumericVariable(StackConstants.INDEX), stack);
        }
    }
}
