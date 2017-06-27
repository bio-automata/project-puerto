package model.events.cartEvents;

import model.constants.EventConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.entities.vehicles.Cart;
import model.events.Event;
import model.events.equipEvents.EndEquipUndockingEvent;
import model.events.reachSteakerEvents.EndStackerUnloadCartEvent;
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
        //system.setClock(this.getOccurrenceTime());
    	this.system.setClock(this.getOccurrenceTime());
        system.report("Carreta transportou container até o pátio");
        
        this.system.report("Produtividade dos RTGs: "+this.system.getVariable(SystemConstants.RTG_CONTAINER_PRODUCTION));
        
        system.report("Numero RTGs livres:"+this.system.getEntityQueueSet().getEntityQueue("rtg").size());
        /*try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        //
        if (system.hasEntityAvailableInQueue("rtg")){
        	Entity rtg = system.getEntityFromQueue("rtg");
        	rtg.setDependence("cart", cart);
        	this.cart.setDependence("rtg", rtg);
        	        	
        	Event event = new EndRTGUnloadCartEvent(rtg, this.system);
        	event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_UNLOAD_CART_EVENT));
        	this.system.agendFutureEvent(event);        	
        }
        //
        else if (system.hasEntityAvailableInQueue("stacker")){
        	Entity steaker = system.getEntityFromQueue("stacker");
        	steaker.setDependence("cart", cart);
        	
        	Event event = new EndStackerUnloadCartEvent(steaker, this.system);
        	event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_EMPTY_RETURNING_EVENT));
        	this.system.agendFutureEvent(event);
        }
        //
        else{
            system.addEntityInQueue("cart waiting unload", cart);
            system.report("Carreta aguardando para descarregar container");
        }
    }
}
