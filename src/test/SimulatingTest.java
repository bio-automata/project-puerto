package test;


import controller.SimulatorEngine;
import model.entities.Entity;
import model.events.shipEvents.ShipArrivingEvent;
import model.random.time.ExponentialTimeDistribution;
import model.random.time.TimeDistribution;
import model.system.Systema;

public class SimulatingTest {
	public static void main(String args[]){
		//SimulatorEngine.initialize(System.getProperty("user.dir")+"cenario.txt");
		SimulatorEngine.initialize("cenario.txt");
	}
}
