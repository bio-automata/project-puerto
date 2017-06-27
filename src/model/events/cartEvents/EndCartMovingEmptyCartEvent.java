package model.events.cartEvents;

import model.constants.EventConstants;
import model.entities.Entity;
import model.events.Event;
import model.events.craneEvents.EndCraneLoadingCartEvent;
import model.events.craneEvents.EndCraneRiseContainerEvent;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndCartMovingEmptyCartEvent extends Event{
	private Entity cart;
    public EndCartMovingEmptyCartEvent(Entity cart, Systema system){
    	super(system);
    	this.cart = cart;

    }

    public void execute(){
    	this.system.setClock(this.getOccurrenceTime());
    	system.report("Carreta retornou vazia");
    	
        if(system.hasEntityAvailableInQueue("crane waiting cart")){
        	system.report("Existe grua em espera, carreta irá carregar na grua");
        	Entity crane = this.system.getEntityFromQueue("crane waiting cart");
        	crane.setDependence("cart", this.cart);
        	this.cart.setDependence("crane", crane);
        	
        	Event event = new EndCraneLoadingCartEvent(crane, system);
        	event.setOccurrenceTime(this.getOccurrenceTime()+this.system.getEventDuration(EventConstants.CART_MOVING_LOAD_EVENT));
            this.system.agendFutureEvent(event);
        }
        else{
        	this.system.addEntityInQueue("cart", cart);
        	system.report("Carreta ociosa");
        }
    }
}
