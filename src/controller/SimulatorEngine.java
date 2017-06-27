package controller;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.constants.SystemConstants;
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
	

	public static void initialize(String inputAddres){
		System.out.println("Construindo sistema");
		system = SystemBuildingEngine.buildSystem(inputAddres);
		System.out.println("Inicializando simulação");
		system.startSimulation();
		System.out.println("simulando");		
	}

	public static void simulate(String inputAddress, String outputAddress){
		initialize(inputAddress);
		//inicia a simulação, seta o estado do sistema como "simulando" = true
		//enquanto o sistema estiver em simulação
		while(system.simulating()){
			//retira da lista de eventos futuros o evento imediato e o executa
			system.getNextImediateEvent().execute();
		}
		
		endSimulation(outputAddress);
	}
	
    
    public static void endSimulation(String outputAddress){
		try {
			BufferedWriter file;
			file = new BufferedWriter(new FileWriter(outputAddress,true));
			
			file.write(""+system.getClock());
			file.write(" "+system.getVariable(SystemConstants.CRANE_CONTAINER_PRODUCTION));
			file.write(" "+system.getVariable(SystemConstants.RTG_CONTAINER_PRODUCTION));
			file.write(" "+system.getVariable(SystemConstants.CRANE_CONTAINER_PRODUCTION)/system.getClock()*24*3600);
			file.write(" "+system.getVariable(SystemConstants.RTG_CONTAINER_PRODUCTION)/system.getClock()*24*3600+"\n");
			
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro ao gerar relátório de saída");
		}
    }
}
