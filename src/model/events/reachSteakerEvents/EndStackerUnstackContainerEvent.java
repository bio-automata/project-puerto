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
public class EndStackerUnstackContainerEvent extends Event{
	Entity stack;
	
    public EndStackerUnstackContainerEvent(Entity stack, Systema system){
    	super(system);
    	this.stack = stack;
    }

    public void execute(){
    	this.system.setClock(this.getOccurrenceTime());
    	this.system.report("Reach Stacker desempilhou container na pilha");

        Entity stacker = stack.getDependence("stacker");
        this.stack.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, stack.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)-1);
        
        Event event = new EndStackerMovingContainerToRailwayEvent(stacker, system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_MOVING_CONTAINER_TO_RAILWAY_EVENT));
        this.system.agendFutureEvent(event);

        
        //se existe rtg/steaker aguardadno na fila da pilha, já aguarda novo empilhamento
        
        if(this.system.hasEntityAvailableInQueue("rtg waiting for stack "+this.stack.getNumericVariable(StackConstants.INDEX))){
        	//retira veiculo da fila e atuliza ponteiro da pilha
        	Entity rtg = this.system.getEntityFromQueue("rtg waiting for stack "+this.stack.getNumericVariable(StackConstants.INDEX));
        	stack.setDependence("rtg", rtg);
        	
        	event = new EndRTGStackContainerEvent(stack, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_STACKING_CONTAINER_EVENT));
            this.system.agendFutureEvent(event);
        }
        else if(this.system.hasEntityAvailableInQueue("stacker waiting for stack "+this.stack.getNumericVariable(StackConstants.INDEX))){
        	//retira veiculo da fila e atuliza ponteiro da pilha
        	stacker = this.system.getEntityFromQueue("stacker waiting for stack "+this.stack.getNumericVariable(StackConstants.INDEX));
        	stack.setDependence("stacker", stacker);
        	
        	event = new EndStackerStackContainerEvent(stack, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_STACKING_CONTAINER_EVENT));
            this.system.agendFutureEvent(event);
        }
        else if(this.system.hasEntityAvailableInQueue("stacker waiting for unstack "+this.stack.getNumericVariable(StackConstants.INDEX))){
        	//retira veiculo da fila e atuliza ponteiro da pilha
        	stacker = this.system.getEntityFromQueue("stacker waiting for stack "+this.stack.getNumericVariable(StackConstants.INDEX));
        	stack.setDependence("stacker", stacker);
        	
        	event = new EndStackerUnstackContainerEvent(stack, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_UNSTACKING_CONTAINER_EVENT));
            this.system.agendFutureEvent(event);
        }
    }
}
