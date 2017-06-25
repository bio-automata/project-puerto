package test;


import model.entities.Entity;
import model.events.shipEvents.ShipArrivingEvent;
import model.random.time.ExponentialTimeDistribution;
import model.random.time.TimeDistribution;
import model.system.Systema;

public class DistributionsTest {
	public static void main(String args[]){
		Systema system = Systema.getInstance();
		
		
		system.addEntityInQueue("equip", new Entity());
		system.addEntityInQueue("quay", new Entity());
		system.addEntityInQueue("crane", new Entity());
		system.agendFutureEvent(new ShipArrivingEvent(system));
		
		
		TimeDistribution d = new ExponentialTimeDistribution(10.0,system);
		
		
		//system.addTimeDistribution("vazio", null);
		
		system.getRandomTimeGenerator().addTimeDistribution("docking", d);
		
		

		
		
		
		
		//system.getRandomTimeGenerator().addTimeDistribution("docking", new ExponentialTimeDistribution(10.0,system));
		//system.getRandomTimeGenerator().addTimeDistribution("docking", new ExponentialTimeDistribution(10.0,system));
		
		//system.startSimulation();

		//enquanto o sistema estiver em simulação
		int i =0;
		while(i<3){
			//retira da lista de eventos futuros o evento imediato e o executa
			system.getNextImediateEvent().execute();
			i++;
		}

		
		System.out.println("finish...");
		
		
	}
}
