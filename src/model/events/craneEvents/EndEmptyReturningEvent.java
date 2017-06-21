package model.events.craneEvents;

import model.entities.Crane;
import model.entities.Entity;
import model.entities.ships.Ship;
import model.events.Event;
import model.system.Systema;

/**

 */
public class EndEmptyReturningEvent extends Event{
    private Entity crane;
    private Entity ship;

    public EndEmptyReturningEvent(Entity crane, Entity ship, Systema system){
    	super(system);
        this.crane = crane;
    }


	@Override
	public void execute() {
		// TODO Auto-generated method stub
        system.setClock(this.getOccurrenceTime());

        if(ship.getNumericVariable("number of containers")>0){//se existe container no navio

        }
        else{
            //if()
        }
	}
}
