package model.events.reachSteakerEvents;

import model.constants.EventConstants;
import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndStackerLoadingWagonEvent extends Event{
	Entity stacker;
    public EndStackerLoadingWagonEvent(Entity stacker, Systema system){
    	super(system);
    	this.stacker = stacker;
    }

    public void execute(){
        system.setClock(this.getOccurrenceTime());
        system.report("Vagão foi carregado");
        
        Entity train = this.stacker.getDependence("train");
        train.setNumericVariable("wagon quantity", train.getNumericVariable("wagon quantity")-1);
        
        Event event = new EndStackerEmptyReturningEvent(train, system);
        event.setOccurrenceTime(this.getDurationTime()+this.system.getEventDuration(EventConstants.STACKER_EMPTY_RETURNING_EVENT));
        this.system.agendFutureEvent(event);
    }
}
