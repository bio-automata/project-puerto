package model.events.rtgEvents;

import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndRTGMovingContainerToStackEvent extends Event{
    public EndRTGMovingContainerToStackEvent(Systema system){
    	super(system);
    }

    public void execute(){
        system.incrementClock(this.getOccurrenceTime());
        system.report("RTG movimentou até uma das pilhas do pátio");
    }
}
