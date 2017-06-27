package model.events.craneEvents;

import model.constants.EventConstants;
import model.entities.Crane;
import model.entities.Entity;
import model.entities.vehicles.Cart;
import model.events.Event;
import model.events.cartEvents.EndCartMovingLoadCartEvent;
import model.system.Systema;


/**

 */
public class EndCraneLoadingCartEvent extends Event{
    private Entity crane;
    private Entity cart;

    public EndCraneLoadingCartEvent(Entity crane, Systema system){
    	super(system);
        this.crane = crane;
        this.cart = crane.getDependence("cart");
    }

	@Override
	public void execute() {
		// TODO Auto-generated method stub
    	//atualisa o relógio do sistema
		this.system.report("Grua carrregou uma carreta");
        system.setClock(this.getOccurrenceTime());
        
        Event event = new EndCraneEmptyReturningEvent(crane, this.system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.CRANE_EMPTY_RETURNING_EVENT));
        this.system.agendFutureEvent(event);

        event = new EndCartMovingLoadCartEvent(this.cart, system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.CART_MOVING_LOAD_EVENT));
        this.system.agendFutureEvent(event);
	}
}
