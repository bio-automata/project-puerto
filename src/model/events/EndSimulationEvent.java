package model.events;

import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndSimulationEvent extends Event{
    public EndSimulationEvent(Systema system){
    	super(system);
    	this.setOccurrenceTime(system.getVariable("UTSM"));
    }

    public void execute(){
        System.out.println("Simulação terminou!!!");
        System.exit(0);
    }
}
