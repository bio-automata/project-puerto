package model.events.reachSteakerEvents;

import model.events.Event;
import model.system.Systema;

/**

 */
public class EndSteakerEmptyReturningEvent extends Event {
    public EndSteakerEmptyReturningEvent(Systema system){
    	super(system);

    }


    public void execute(){
        system.setClock(this.getDurationTime());
    }
}
