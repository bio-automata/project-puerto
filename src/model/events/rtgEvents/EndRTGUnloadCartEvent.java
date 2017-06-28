package model.events.rtgEvents;


import model.constants.EventConstants;
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
    	this.system.setClock(this.getOccurrenceTime());
        system.report("RTG descarregou container da carreta");
        
        //descarrega carreta
        //agenda retorno da carreta
        Entity cart = this.rtg.getDependence("cart");       
        Event event = new EndCartMovingEmptyCartEvent(cart, system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.CART_MOVING_EMPTY_EVENT));
        this.system.agendFutureEvent(event);

       
        //sorteia
        event = new EndRTGMovingContainerToStackEvent(this.rtg, system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_MOVING_CONTAINER_TO_STACK_EVENT));
        this.system.agendFutureEvent(event);
    }
}
