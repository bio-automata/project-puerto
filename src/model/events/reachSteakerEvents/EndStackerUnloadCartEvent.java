package model.events.reachSteakerEvents;

import model.constants.EventConstants;
import model.entities.Entity;
import model.events.Event;
import model.events.cartEvents.EndCartMovingEmptyCartEvent;
import model.events.rtgEvents.EndRTGMovingContainerToStackEvent;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndStackerUnloadCartEvent extends Event {
	Entity steaker;
	
    public EndStackerUnloadCartEvent(Entity steaker, Systema system){
    	super(system);
    	this.steaker = steaker;
    }

    public void execute() {
    	this.system.setClock(this.getOccurrenceTime());
        system.report("Steaker descarrega container da carreta");
        Entity cart = this.steaker.getDependence("cart");
        
        //descarrega carreta
        //agenda retorno da carreta 
        Event event = new EndCartMovingEmptyCartEvent(cart, system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.CART_MOVING_EMPTY_EVENT));
        this.system.agendFutureEvent(event);

        
        //sorteia 
        event = new EndStackerMovingContainerToStackEvent(this.steaker, system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_MOVING_CONTAINER_TO_STACK_EVENT));
        this.system.agendFutureEvent(event);
    }
}