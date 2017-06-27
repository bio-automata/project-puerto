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
	Entity stacker;
    public EndStackerMovingContainerToStackEvent(Entity steaker, Systema system){
    	super(system);
    	this.stacker = steaker;
    }

    public void execute(){
        system.setClock(this.getOccurrenceTime());
        system.report("Stacker movimentou-se até o pátio à procura de uma pilha");
        
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
        	this.system.report("Numero de pilhas no pátio >>"+this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue());
        	
        	
        	int stackQuantity = this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue();	//numero de pilhas no sistema
            int stackindex = new Random().nextInt(stackQuantity);
            
            stack = this.system.getEntityFromSet(SystemConstants.CONTAINER_STAKS, stackindex);
            this.system.report("Numero de containers na pilha "+stackindex+" >> "+stack.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS).intValue());
            
            if(stack.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)>=stack.getNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS)){
            	this.system.report("A pilha estava chiea, stacker criará nova pilha");
            	//cria uma nova pilha
            	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS)+1);
            	
            	stack = new Entity();
            	stack.setNumericVariable(StackConstants.INDEX, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS)+1);
            	stack.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 0);
            	stack.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
            	
            	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stack);
            }
        }
        
        //se não há rtg na fila da pilha
        if((!this.system.hasEntityAvailableInQueue("rtg waiting for stack "+stack.getNumericVariable(StackConstants.INDEX))) && (!this.system.hasEntityAvailableInQueue("stacker waiting for stack "+stack.getNumericVariable(StackConstants.INDEX))) && (!this.system.hasEntityAvailableInQueue("stacker waiting for unstack "+stack.getNumericVariable(StackConstants.INDEX)))){
        	this.system.report("Não há fila na pilha, Reach Stacker pode empilhar");
        	double eventTimeDuration = this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_MOVING_CONTAINER_TO_STACK_EVENT)+stack.getNumericVariable(StackConstants.INDEX)*10;
        	stack.setDependence("stacker", this.stacker);
        	Event event = new EndStackerStackContainerEvent(stack, this.system);
            //event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_STACKING_CONTAINER_EVENT));
        	
        	event.setOccurrenceTime(eventTimeDuration);
            this.system.agendFutureEvent(event);
        }
        //do contrário enfileira rtg 
        else
        	this.system.report("Fila em espera, Reach Stacker aguardando para empilhar");{
        	this.system.addEntityInQueue("stacker waiting for stack "+stack.getNumericVariable(StackConstants.INDEX), stacker);
        }
    }
}