package model.events.craneEvents;

import model.constants.EventConstants;
import model.constants.ShipConstants;
import model.entities.Crane;
import model.entities.Entity;
import model.entities.ships.Ship;
import model.events.Event;
import model.events.equipEvents.EndEquipUndockingEvent;
import model.system.Systema;

/**

 */
public class EndCraneEmptyReturningEvent extends Event{
    private Entity crane;
    private Entity ship;

    public EndCraneEmptyReturningEvent(Entity crane, Systema system){
    	super(system);
        this.crane = crane;
        this.ship = crane.getDependence("ship");
    }


	@Override
	public void execute() {
		// TODO Auto-generated method stub
        system.setClock(this.getOccurrenceTime());
        this.system.report("Grua retornou vazia para içar outro container");

        if(ship.getNumericVariable(ShipConstants.NUMBER_OF_CONTAINERS)>0){//se existe container no navio
        	Event event = new EndCraneRiseContainerEvent(ship, system);
    		event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.CRANE_RISING_CONTAINER_EVENT));
    		this.system.agendFutureEvent(event);
        }
        else{
            //if()
        	this.system.addEntityInQueue("crane", this.crane);
        	
        	if(this.system.hasEntityAvailableInQueue("equip")){
        		Entity equip = this.system.getEntityFromQueue("equip");
        		this.ship.setDependence("equip", equip);
        		
        		Event event = new EndEquipUndockingEvent(this.ship, system);
        		event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.EQUIP_UNDOKING_EVENT));
        		this.system.agendFutureEvent(event);
        	}
        	else{
        		this.system.addEntityInQueue("ship waiting undock", ship);
        		this.system.report("Navio aguardando equipe");
        	}
        }
	}
}
