package model.events.craneEvents;

import model.entities.Crane;
import model.entities.vehicles.Cart;
import model.events.Event;
import model.system.Systema;

/**

 */
public class EndLoadingCartEvent extends Event{
    private Crane crane;
    private Cart cart;

    public EndLoadingCartEvent(Crane crane, Cart cart, Systema system){
    	super(system);
        this.crane = crane;
        this.cart = cart;
        
    }

	@Override
	public void execute() {
		// TODO Auto-generated method stub
    	//atualisa o relógio do sistema
        system.setClock(this.getOccurrenceTime());

        /*
        Event event = new EndEmptyReturningEvent(crane);
        event.setOccurrenceTime(this.getOccurrenceTime()+model.system.getRandomTimeGenerator().getTime("EmptyReturning"));
        model.system.getFutureEventList().addEvent(event);

        event = new EndMovingLoadCartEvent(cart);
        event.setOccurrenceTime(this.getOccurrenceTime()+model.system.getRandomTimeGenerator().getTime("MovingLoadCart"));
        model.system.getFutureEventList().addEvent(event);
        */
	}
}
