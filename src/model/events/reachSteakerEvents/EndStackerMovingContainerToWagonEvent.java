package model.events.reachSteakerEvents;

import model.constants.EventConstants;
import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndStackerMovingContainerToWagonEvent extends Event{
	Entity stacker;
	
    public EndStackerMovingContainerToWagonEvent(Entity stacker, Systema system){
    	super(system);
    	this.stacker = stacker;
    }

    public void execute(){
        system.setClock(this.getDurationTime());
        
        
        Event event = new EndStackerLoadingWagonEvent(this.stacker, this.system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_LOADING_WAGON_EVENT));
        this.system.agendFutureEvent(event);
    }
}
