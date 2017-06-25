package model.events.cartEvents;

import model.constants.EventConstants;
import model.entities.Entity;
import model.entities.vehicles.Cart;
import model.events.Event;
import model.events.equipEvents.EndEquipUndockingEvent;
import model.events.reachSteakerEvents.EndSteakerUnloadCartEvent;
import model.events.rtgEvents.EndRTGUnloadCartEvent;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndCartMovingLoadCartEvent extends Event{
    private Entity cart;
    
    public EndCartMovingLoadCartEvent(Entity cart, Systema system){
    	super(system);
        this.cart = cart;
    }

    @Override
    public void execute(){
        system.setClock(this.getOccurrenceTime());
        system.report("Carreta transportou container até o pátio");
        
        if (system.hasEntityAvailableInQueue("rtg")){
        	system.report("Grua Retornando vazia");
        	
        	Entity vehicle = system.getEntityFromQueue("rtg");
        	
        	Event event = new EndRTGUnloadCartEvent(this.system);
        	event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_UNLOAD_CART_EVENT));
        	this.system.agendFutureEvent(event);

        	
        }
        else if (system.hasEntityAvailableInQueue("steaker")){
        	Entity vehicle = system.getEntityFromQueue("steaker");
        	
        	Event event = new EndSteakerUnloadCartEvent(this.system);
        	event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STEAKER_EMPTY_RETURNING_EVENT));
        	this.system.agendFutureEvent(event);
        }
        else{
        	
            system.getEntityQueueSet().addEntity("cart waiting unload", cart);
            system.report("Carreta aguardando descarregar container");
        }

    }
}
