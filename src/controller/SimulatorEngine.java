package controller;


import model.system.Systema;

/**

 This class manipulates the System
 
 	the life cicle of this class consists in
 		-> start the simulation
 		-> take the immediate event in the FEL and execute this event
 		-> the event automatically changes the system state in execution time
 		-> the simulation runs until the finish event notice
 		   is achieved or the FEL is empty

 */
public class SimulatorEngine {
	private static Systema system;
	

	public static void initialize(String filepath){
		System.out.println("Construindo sistema");
		system = SystemBuildingEngine.buildSystem(filepath);
		System.out.println("Inicializando simulação");
		system.startSimulation();
		System.out.println("simulando");
		simulate();
	}

	public static void simulate(){
		//inicia a simulação, seta o estado do sistema como "simulando" = true
		//enquanto o sistema estiver em simulação
		while(system.simulating()){
			//retira da lista de eventos futuros o evento imediato e o executa
			system.getNextImediateEvent().execute();
		}
	}
}
