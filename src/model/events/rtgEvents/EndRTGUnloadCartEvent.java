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
public class EndRTGUnloadCartEvent extends Event{
	Entity rtg;
    public EndRTGUnloadCartEvent(Entity rtg, Systema system){
    	super(system);
    	this.rtg = rtg;
    }

    public void execute(){
        system.incrementClock(this.getOccurrenceTime());
        system.report("RTG descarregou container da carreta");
        Entity cart = this.rtg.getDependence("cart");
        

        //descarrega carreta
        //agenda retorno da carreta 
        Event event = new EndCartMovingEmptyCartEvent(cart, system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.CART_MOVING_EMPTY_EVENT));
        this.system.agendFutureEvent(event);

        
        //sorteia 
        event = new EndRTGMovingContainerToStackEvent(this.rtg, system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_MOVING_CONTAINER_TO_STACK_EVENT));
        this.system.agendFutureEvent(event);
        
        
    }
}
