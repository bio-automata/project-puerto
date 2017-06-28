package validation;


import controller.SimulatorEngine;

public class SimulatingTest {
	public static void main(String args[]){
		//SimulatorEngine.initialize(System.getProperty("user.dir")+"cenario.txt");
		//SimulatorEngine.simulate("cenario"+1+".txt","saidax.txt");
		//System.exit(0);

		int i, j, k, n;
		
		
		for(i=2;i<4;i++){
			System.out.println("\nRodando cenário: "+i);
			for(j=0;j<100;j++){
				System.out.println("\nIteração: "+j+" do cenário "+i);
				SimulatorEngine.simulate("cenario"+i+".txt","saida_15_dias_cenario_"+i+".txt");
			}
		}
		
	}
}
