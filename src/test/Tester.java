package test;

import model.entities.vehicles.Vehicle;
import model.events.Event;
import model.events.Evente;
import model.system.Systema;

public class Tester {
	public static void main(String args[]){
		Systema system = new Systema();
		Event e1 = new Evente(system);
		Event e2 = new Evente(system);
		Event e3 = new Evente(system);
		Event e4 = new Evente(system);
		Event e5 = new Evente(system);
		
		e1.setOccurrenceTime(0.15);
		e2.setOccurrenceTime(0.25);
		e3.setOccurrenceTime(0.08);
		e4.setOccurrenceTime(0.06);
		e5.setOccurrenceTime(0.12);
		Vehicle va;
		Vehicle v = new Vehicle();
		
		
		
		v.setNumericVariable("peso", 35.0);
		v.setNumericVariable("ano", 1935.0);
		
		system.setVariable("numero de carros", 1.0);
		system.addEntityInEntitySet("carros", v);
		system.addEntityInQueue("carros", v);
		system.agendFutureEvent(e1);
		system.agendFutureEvent(e2);
		system.agendFutureEvent(e3);
		system.agendFutureEvent(e4);
		system.agendFutureEvent(e5);
		
		va = (Vehicle)system.getEntityFromQueue("carros");
		
		//system.getEntitySet().getEntity("carros", 0);
		System.out.println(system.getVariable("numero de carros"));
		System.out.println(system.getEntitySet().getEntity("carros", 0).getNumericVariable("peso"));
		System.out.println(va.getNumericVariable("peso"));
		System.out.println(va.getNumericVariable("ano"));
		
		
		System.out.println(system.getNextImediateEvent().getOccurrenceTime());
		System.out.println(system.getNextImediateEvent().getOccurrenceTime());
		System.out.println(system.getNextImediateEvent().getOccurrenceTime());
		System.out.println(system.getNextImediateEvent().getOccurrenceTime());
		System.out.println(system.getNextImediateEvent().getOccurrenceTime());
		
		System.out.println("tesste");
	}
}
