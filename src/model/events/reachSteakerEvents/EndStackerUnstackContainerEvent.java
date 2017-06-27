package model.events.reachSteakerEvents;

import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndStackerUnstackContainerEvent extends Event{
    public EndStackerUnstackContainerEvent(Systema system){
    	super(system);
    }

    public void execute(){
    	this.system.setClock(this.getOccurrenceTime());

    }
}
