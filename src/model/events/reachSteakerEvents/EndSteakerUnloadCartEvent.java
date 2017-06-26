package model.events.reachSteakerEvents;

import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndSteakerUnloadCartEvent extends Event {
    public EndSteakerUnloadCartEvent(Entity steaker, Systema system){
    	super(system);
    }

    public void execute() {
        system.incrementClock(this.getOccurrenceTime());
        system.report("Steaker descarrega container da carreta");
        
        //decrementa contaier do navio


        //
    }
}