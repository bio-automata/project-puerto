package model.events.rtgEvents;

import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndRTGStackContainerEvent extends Event{
    public EndRTGStackContainerEvent(Systema system){
    	super(system);
    }

    public void execute(){
        system.incrementClock(this.getOccurrenceTime());
        system.report("RTG empilhou container na pilha");

        //decrementa contaier do navio



        //
    }
}
