package model.events.craneEvents;

import model.entities.Crane;
import model.entities.vehicles.Cart;
import model.events.Event;
import model.system.Systema;

/**

 */
public class EndMovingContainerEvent extends Event{
    private Crane crane;
    private Cart cart;


    public EndMovingContainerEvent(Crane crane, Cart cart, Systema system){
    	super(system);
        this.crane = crane;
        this.cart = cart;
    }

    @Override
    public void execute(){
        //atualiza o tempo do sistema
        system.setClock(this.getOccurrenceTime());

        if (system.getEntityQueueSet().getEntityQueue("cart").available()){
            Cart cart = (Cart) system.getEntityQueueSet().getEntity("cart");

            Event event = new EndLoadingCartEvent(this.crane, this.cart, this.system);
            event.setOccurrenceTime(this.getOccurrenceTime()+system.getRandomTimeGenerator().getTime("loading cart"));
            system.getFutureEventList().addEvent(event);
        }
        else{
            system.getEntityQueueSet().addEntity("crane waiting cart", crane);
        }
    }
    
}
