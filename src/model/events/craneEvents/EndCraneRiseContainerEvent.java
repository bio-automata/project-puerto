package model.events.craneEvents;

import model.constants.EventConstants;
import model.constants.ShipConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.entities.ships.Ship;
import model.events.Event;
import model.system.Systema;

/**

 */
public class EndCraneRiseContainerEvent extends Event{
	
    private Entity ship;
    private Entity crane;

    public EndCraneRiseContainerEvent(Entity crane, Systema system){
    	super(system);
    	this.crane = crane;
        this.ship = crane.getDependence("ship");
    }

    
    @Override
	public void execute() {
    	//atualiza o tempo do sistema
        system.setClock(this.getOccurrenceTime());
        this.system.report("Grua içou um container");
        this.system.setVariable(SystemConstants.CRANE_CONTAINER_PRODUCTION, this.system.getVariable(SystemConstants.CRANE_CONTAINER_PRODUCTION)+1);
        this.ship.incrementVariable(ShipConstants.NUMBER_OF_CONTAINERS, -1);
        
        if(ship.getNumericVariable(ShipConstants.NUMBER_OF_CONTAINERS)>0){//se existe container no navio
        	if (system.hasEntityAvailableInQueue("crane")){
            	//agenda descarregamento e seta dependencias do navio
        		Entity crane = this.system.getEntityFromQueue("crane");
            	//this.ship.setDependence("crane", crane);
            	crane.setDependence("ship", this.ship);
            	
            	//cria evento de içar container
            	
                Event event = new EndCraneRiseContainerEvent(crane, this.system);
                event.setOccurrenceTime(this.system.getClock()+system.getEventDuration(EventConstants.CRANE_RISING_CONTAINER_EVENT));
                system.getFutureEventList().addEvent(event);            
            	
            	system.report("Navio "+this.ship.getTextVariable("name")+" inicia descarregamento na grua ");
            }
        }
        
        Event event = new EndCraneMovingContainerEvent(this.crane, this.system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.CRANE_MOVING_CONTAINER_EVENT));
        system.getFutureEventList().addEvent(event);
	}
}
