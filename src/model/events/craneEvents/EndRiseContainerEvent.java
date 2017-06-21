package model.events.craneEvents;

import model.entities.Entity;
import model.entities.ships.Ship;
import model.events.Event;
import model.system.Systema;

/**

 */
public class EndRiseContainerEvent extends Event{
	
    private Entity ship;
    private Entity crane;

    public EndRiseContainerEvent(Entity ship, Systema system){
    	super(system);
        this.ship = ship;
        this.crane = ship.getDependence("crane");
    }

    
    @Override
	public void execute() {
    	//atualiza o tempo do sistema
        system.setClock(this.getOccurrenceTime());

        ship.incrementVariable("number of containers", -1);
        system.report("levantou um container");
        
        /*
        Event event = new EndMovingContainerEvent(this.system);
        event.setOccurrenceTime(this.getOccurrenceTime()+system.getRandomTimeGenerator().getTime("mov. container"));
        system.getFutureEventList().addEvent(event);
		*/
	}
}
