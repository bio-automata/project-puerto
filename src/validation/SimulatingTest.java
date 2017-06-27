package validation;


import controller.SimulatorEngine;

public class SimulatingTest {
	public static void main(String args[]){
		//SimulatorEngine.initialize(System.getProperty("user.dir")+"cenario.txt");
		//SimulatorEngine.simulate("cenario"+1+".txt","saidax.txt");
		//System.exit(0);

		int i, j, k, n;
		
		n = 100;
		k = 4;
		for(i=0;i<n;i++){
			for(j=0;j<k;j++){
				SimulatorEngine.simulate("cenario"+i+".txt","saida"+i+".txt");
			}
		}
		
	}
}
