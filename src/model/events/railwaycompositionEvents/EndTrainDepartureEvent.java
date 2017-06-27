package model.events.railwaycompositionEvents;

import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 TrainDepartureEvent


 */

public class EndTrainDepartureEvent extends Event{
	Entity train;
	
    public EndTrainDepartureEvent(Entity train, Systema system){
    	super(system);
    	this.train = train;

    }

    public void execute(){
        system.setClock(this.getOccurrenceTime());
        
        //realiza alguma coisa aqui
        
        if(this.system.hasEntityAvailableInQueue(""))

        
        this.train = null;
    }
}