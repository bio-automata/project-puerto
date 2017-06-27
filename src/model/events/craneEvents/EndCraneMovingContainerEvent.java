package model.events.craneEvents;

import model.constants.EventConstants;
import model.entities.Crane;
import model.entities.Entity;
import model.entities.vehicles.Cart;
import model.events.Event;
import model.system.Systema;

/**

 */
public class EndCraneMovingContainerEvent extends Event{
    private Entity crane;

    public EndCraneMovingContainerEvent(Entity crane, Systema system){
    	super(system);
        this.crane = crane;
    }

    @Override
    public void execute(){
        //atualiza o tempo do sistema
        system.setClock(this.getOccurrenceTime());
        this.system.report("Grua movimentou um container para transporte");
        
        if (system.hasEntityAvailableInQueue("cart")){
            Entity cart = system.getEntityFromQueue("cart");
            cart.setDependence("crane", this.crane);
            this.crane.setDependence("cart", cart);

            Event event = new EndCraneLoadingCartEvent(this.crane, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.CRANE_LOADING_CART_EVENT));
            system.getFutureEventList().addEvent(event);
        }
        else{
            system.addEntityInQueue("crane waiting cart", crane);
            this.system.report("Não há transporte, grua aguardando carreta");
        }
    }
    
}
