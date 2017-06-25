package model.events.rtgEvents;

import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndRTGUnloadCartEvent extends Event{
    public EndRTGUnloadCartEvent(Systema system){
    	super(system);
    }

    public void execute(){
        system.incrementClock(this.getOccurrenceTime());
        system.report("RTG descarregou container da carreta");
        //decrementa contaier do navio



        //
    }
}
