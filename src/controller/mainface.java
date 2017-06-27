package controller;


import controller.SimulatorEngine;

public class mainface {
	public static void main(String args[]){
		//SimulatorEngine.initialize(System.getProperty("user.dir")+"cenario.txt");
		SimulatorEngine.simulate("cenario"+1+".txt","saida"+0+"xx.txt");
		System.exit(0);

		int i, j, k, n;
		
		n = 2;
		k = 3;
		for(i=0;i<n;i++){
			for(j=0;j<k;j++){
				SimulatorEngine.simulate("cenario"+i+".txt","saida"+i+".txt");
			}
		}
		
	}
}
