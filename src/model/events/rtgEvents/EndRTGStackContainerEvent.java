package model.events.rtgEvents;

import java.util.Random;

import model.constants.EventConstants;
import model.constants.StackConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.events.Event;
import model.events.cartEvents.EndCartMovingEmptyCartEvent;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndRTGStackContainerEvent extends Event{
	Entity stak;
	
    public EndRTGStackContainerEvent(Entity stak, Systema system){
    	super(system);
    	this.stak = stak;
    }

    public void execute(){
    	this.system.setClock(this.getOccurrenceTime());
        this.system.report("RTG empilhou container na pilha");

        Entity rtg = stak.getDependence("rtg");
        this.stak.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, stak.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)+1);
        this.system.setVariable(SystemConstants.RTG_CONTAINER_PRODUCTION, this.system.getVariable(SystemConstants.RTG_CONTAINER_PRODUCTION)+1);
        
        system.report("RTG foi liberado");
        this.system.addEntityInQueue("rtg", rtg);
        
        //se existe rtg/steaker aguardadno na fila da pilha, já aguarda novo empilhamento
        if(this.system.hasEntityAvailableInQueue("rtg waiting for stack "+this.stak.getNumericVariable(StackConstants.INDEX))){
        	rtg = this.system.getEntityFromQueue("rtg waiting for stack "+this.stak.getNumericVariable(StackConstants.INDEX));
        	
        	stak.setDependence("rtg", rtg);
        	Event event = new EndRTGStackContainerEvent(stak, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_STACKING_CONTAINER_EVENT));
            this.system.agendFutureEvent(event);
        }
    }
}
