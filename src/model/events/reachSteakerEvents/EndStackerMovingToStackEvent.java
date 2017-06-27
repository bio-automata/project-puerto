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
    public EndStackerMovingToStackEvent(Entity stack, Systema system){
    	super(system);
    	this.stack = stack;
    }

    public void execute(){
        system.setClock(this.getDurationTime());
        system.report("  --Stacker se move até uma das pilhas do pátio para desempilhar");
        
        		
        //se não há fila dependendo da pilha 
        if((!this.system.hasEntityAvailableInQueue("rtg waiting for stack "+stack.getNumericVariable(StackConstants.INDEX))) && (!this.system.hasEntityAvailableInQueue("stacker waiting for stack "+stack.getNumericVariable(StackConstants.INDEX))) && (!this.system.hasEntityAvailableInQueue("stacker waiting for unstack "+stack.getNumericVariable(StackConstants.INDEX)))){
        	stack.setDependence("stacker", this.stack);
        	Event event = new EndStackerUnstackContainerEvent(stack, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_STACKING_CONTAINER_EVENT));
            this.system.agendFutureEvent(event);
        }
        //do contrário enfileira rtg 
        else{
        	this.system.addEntityInQueue("stacker waiting for unstack "+stack.getNumericVariable(StackConstants.INDEX), stack);
        }
    }
}
