package model.events.reachSteakerEvents;

import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndSteakerLoadingWagonEvent extends Event{
    public EndSteakerLoadingWagonEvent(Systema system){
    	super(system);

    }

    public void execute(){
        system.setClock(this.getOccurrenceTime());
        
        //this.system
        
    }
}
