package controller;

import java.io.BufferedReader;
import java.io.FileReader;

import model.constants.EventConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.events.EndSimulationEvent;
import model.events.Event;
import model.events.railwaycompositionEvents.EndTrainArrivingEvent;
import model.events.shipEvents.ShipArrivingEvent;
import model.random.time.ExponentialTimeDistribution;
import model.random.time.TriangularTimeDistribution;
import model.system.Systema;

public class SystemBuildingEngine {
	private static void generateInitialScenario(Systema system){
		double time, duration;
		Event event = new EndSimulationEvent(system);
    	system.agendFutureEvent(event);
    	
    	//configura as variáveis do sistema
    	system.setVariable(SystemConstants.CRANE_CONTAINER_PRODUCTION, 0.0);
    	system.setVariable(SystemConstants.RTG_CONTAINER_PRODUCTION, 0.0);
    	
    	
    	//time = duration;
    	time = 0;
    	while(time<system.getVariable("UTSM")){
    		
    		time = time+system.getEventDuration(EventConstants.SHIP_ARRIVING_EVENT);
    		//System.out.println(time); 
    		
    		event = new ShipArrivingEvent(system);
    		event.setOccurrenceTime(time);
        	system.agendFutureEvent(event);
    	}
    	
    	
    	time = 0;
    	while(time<system.getVariable("UTSM")){
    		
    		time = time+system.getEventDuration(EventConstants.TRAIN_ARRIVING_EVENT);
    		//System.out.println(time); 
    		
    		event = new EndTrainArrivingEvent(system);
    		event.setOccurrenceTime(time);
        	system.agendFutureEvent(event);
    	}
	}
	
	
    private static void importScenario(String filepath, Systema system){
        //System.out.printf("\nConteúdo do arquivo texto:\n");
        try {
        	int i, qtd;
            BufferedReader arq = new BufferedReader(new FileReader(filepath));
            String lineFragment[];
            String entity;
            String atribute;

            // lê a primeira linha
            String line = arq.readLine();
            // a variável "linha" recebe o valor "null" quando o processo
            // de repetição atingir o final do arquivo texto
            while (line != null) {
            	//System.out.println(line);
            	
                //ignora as linhas que tem tamanho 0 (linhas vazias)
                if ((line.length() != 0) && (!line.contains("#"))) {
                    //System.out.println(linha);
                    //System.out.println(String.valueOf(linha.charAt(0)));
                	lineFragment = line.split(" ");
                    entity = lineFragment[0];                    
                    atribute = lineFragment[1];
                    
                    //System.out.println(entity+atribute);
                    if(atribute.equals("TSM")||atribute.equals("MAX")||atribute.equals("MIN")){
                    	
                    	//Reserva a quantidade de uma determinada entidade
                    	Double value = Double.parseDouble(lineFragment[2]);
                    	system.setVariable(entity+atribute, value);
                    }
                    else if(atribute.equals("QTD")){
                    	
                    	//Reserva a quantidade de uma determinada entidade
                    	qtd = Integer.parseInt(lineFragment[2]);
                    	
                    	for(i=0;i<qtd;i++){
                    		system.addEntityInQueue(EntityName.getname(entity), new Entity());
                    	}
                    }
                   //é uma distribuição exponencial
                    else if(lineFragment.length==3){
                    	//System.out.println("exponential");
                    	Double lambda = Double.parseDouble(lineFragment[2]);
                    	system.addTimeDistribution(entity.toUpperCase()+atribute.toUpperCase(), new ExponentialTimeDistribution(lambda, system));
                    }
                    //é uma distribuição triangular
                    else{
                    	//System.out.println("triangular");
                    	Double a = Double.parseDouble(lineFragment[2]);
                        Double b = Double.parseDouble(lineFragment[3]);
                        Double c = Double.parseDouble(lineFragment[4]);
                    	
                    	system.addTimeDistribution(entity.toUpperCase()+atribute.toUpperCase(), new TriangularTimeDistribution(a, b, c, system));
                    }
                }
                // le a proxima linha
                line = arq.readLine();
            }

            // fecha o arquivo
            arq.close();
            //System.exit(0);
        }
        catch (Exception e) {
        	System.out.println("Falha ao importar cenário");
        	System.exit(0);
        }
    }
    
    public static Systema buildSystem(String filepath){
    	Systema system = Systema.getInstance();
    	
    	System.out.println("Importando cenário");
    	importScenario(filepath, system);
    	
    	System.out.println("Gerando situação inicial");
    	generateInitialScenario(system);
    	
    	
    	
    	return system;
    }
    
}
