package model.events.reachSteakerEvents;

import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndStackerStackContainerEvent extends Event{
    public EndStackerStackContainerEvent(Entity stak, Systema system){
    	super(system);
    	
    }

    public void execute(){
        system.setClock(this.getDurationTime());
    }
}
