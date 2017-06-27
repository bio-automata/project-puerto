package model.events.reachSteakerEvents;

import model.events.Event;
import model.system.Systema;

/**

 */
public class EndStackerEmptyReturningEvent extends Event {
    public EndStackerEmptyReturningEvent(Systema system){
    	super(system);

    }


    public void execute(){
        system.setClock(this.getDurationTime());
    }
}
