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
	private Systema system;
	

	public void importConfigurationFile(){

	}

	public void simulate(){
		//inicia a simulação, seta o estado do sistema como "simulando" = true
		system.startSimulation();

		//enquanto o sistema estiver em simulação
		while(system.simulating()){
			//retira da lista de eventos futuros o evento imediato e o executa
			this.system.getNextImediateEvent().execute();
		}
	}


	public void showResults(){
		
	}
}
