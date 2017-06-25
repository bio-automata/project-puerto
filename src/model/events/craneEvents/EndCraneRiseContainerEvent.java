package model.events.craneEvents;

import model.constants.EventConstants;
import model.constants.ShipConstants;
import model.entities.Entity;
import model.entities.ships.Ship;
import model.events.Event;
import model.system.Systema;

/**

 */
public class EndCraneRiseContainerEvent extends Event{
	
    private Entity ship;
    private Entity crane;

    public EndCraneRiseContainerEvent(Entity ship, Systema system){
    	super(system);
        this.ship = ship;
        this.crane = ship.getDependence("crane");
    }

    
    @Override
	public void execute() {
    	//atualiza o tempo do sistema
        system.setClock(this.getOccurrenceTime());
        this.system.report("Grua içou um container");
        ship.incrementVariable(ShipConstants.NUMBER_OF_CONTAINERS, -1);
        
        Event event = new EndCraneMovingContainerEvent(this.crane, this.system);
        event.setOccurrenceTime(this.getOccurrenceTime()+system.getRandomTimeGenerator().getTime(EventConstants.CRANE_MOVING_CONTAINER_EVENT));
        system.getFutureEventList().addEvent(event);
	}
}
