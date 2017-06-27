package vision;

import controller.SimulatorEngine;

public class mainface {
	public static void main(String args[]){
		//SimulatorEngine.initialize(System.getProperty("user.dir")+"cenario.txt");
		SimulatorEngine.simulate(args[0],args[1]);
	}
}
