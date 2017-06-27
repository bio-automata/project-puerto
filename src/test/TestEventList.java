package test;


import java.util.Random;

import controller.SimulatorEngine;
import model.entities.Entity;
import model.events.shipEvents.ShipArrivingEvent;
import model.random.time.ExponentialTimeDistribution;
import model.random.time.TimeDistribution;
import model.system.Systema;

public class TestEventList {
	public static void main(String args[]){
		FutureEventList f = new FutureEventList();
		double d = 0;
		for(int i = 0 ; i< 10000; i++){
			d = d+new Random().nextFloat();
			
			f.addEvent(d);
			if(new Random().nextFloat()<0.1){
				System.out.println(f.getEvent());
			}
		}
	}
}
