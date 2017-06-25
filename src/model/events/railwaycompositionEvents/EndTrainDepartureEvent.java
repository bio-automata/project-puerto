package model.events.railwaycompositionEvents;

import model.events.Event;
import model.system.Systema;

/**
 TrainDepartureEvent


 */

public class EndTrainDepartureEvent extends Event{
    public EndTrainDepartureEvent(Systema system){
    	super(system);

    }

    public void execute(){
        system.setClock(this.getOccurrenceTime());


    }
}