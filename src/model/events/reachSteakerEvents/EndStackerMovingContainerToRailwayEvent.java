package model.events.reachSteakerEvents;

import java.util.Random;

import model.constants.RainlwayConstants;
import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndStackerMovingContainerToRailwayEvent extends Event{
	private Entity stacker;
	
    public EndStackerMovingContainerToRailwayEvent(Entity stacker, Systema system){
    	super(system);
    	this.stacker = stacker;
    }

    public void execute(){
        system.setClock(this.getDurationTime());
        system.report("Reach stacker se moveu até uma composição ferroviária");
        Entity train = stacker.getDependence("train");
        
        //sorteia o vagão
        int wagon = new Random().nextInt(train.getNumericVariable(RainlwayConstants.NUMBER_OF_WAGONS).intValue())+1;
        double eventTimeDuration = wagon*10;
        
        //agenda evento de mover até o vagão
        Event event = new EndStackerMovingContainerToWagonEvent(this.stacker, this.system);
        event.setOccurrenceTime(eventTimeDuration);
        this.system.agendFutureEvent(event);
    }
}
