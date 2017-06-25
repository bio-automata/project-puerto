package model.events.railwaycompositionEvents;


import model.events.Event;
import model.system.Systema;

/**
 TrainArrivingEvent

 */

public class EndTrainArrivingEvent extends Event{
    public EndTrainArrivingEvent(Systema system){
    	super(system);

    }

    public void execute(){
        system.setClock(this.getOccurrenceTime());
    }
}