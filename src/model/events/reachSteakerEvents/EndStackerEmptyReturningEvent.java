package model.events.reachSteakerEvents;

import java.util.Random;

import model.constants.RainlwayConstants;
import model.constants.StackConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**

 */
public class EndStackerEmptyReturningEvent extends Event {
	Entity stacker;
    public EndStackerEmptyReturningEvent(Entity stacker, Systema system){
    	super(system);
    	this.stacker = stacker;
    }


    public void execute(){
        system.setClock(this.getDurationTime());
        system.report("Reach Stacker retornou vazio para o pátio");
        
        //existe vagão vazio o terminal
        
        Entity train = this.stacker.getDependence("train");        
        
        if(train.getNumericVariable(RainlwayConstants.NUMBER_OF_WAGONS)>0){
        	this.system.addEntityInQueue("train loading", train);
        	// sortear pilha destino
        	//sortear a duração
        	
        	//seleciona pilha para desempilhar
            int stackQuantity;
            int stackindex; 
            int i;
            
            i = 20;			//procura por 20 vezes
            stackQuantity = this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue();	//numero de pilhas no sistema
            while(i>0){
            	i--;        	
            	stackindex = new Random().nextInt(stackQuantity);            
                Entity stack = this.system.getEntityFromSet(SystemConstants.CONTAINER_STAKS, stackindex);
                            
                if(stack.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)>0){
                	//retira container da pilha
                	//agenda o evento de movimentar para a pilha com intenção de descarregar
                	
                	this.stacker.setDependence("stack", stack);
                	stack.setDependence("stacker", this.stacker);
                	double eventTimeDuration = this.system.getClock()+stack.getNumericVariable(StackConstants.INDEX)*10;
                	
                	Event event = new EndStackerMovingToStackEvent(stack, system);
                	event.setOccurrenceTime(eventTimeDuration);
                	this.system.agendFutureEvent(event);
                	break;
                }
                else{
                	if(i==0){
                		this.system.report("Não foram enconcatada nenhuma pilha, reach stacker ocioso");
                		//enfleira stacker
                		this.system.addEntityInQueue("stacker", stacker);
                		break;
                	}
                	this.system.report("A pilha estava vazia, stacker procurará outra pilha para desempilhar");
                }
            }
        }
        else{
        	system.report("Não existem vagões disponíveis, reach stacker ocioso");
        	this.system.addEntityInQueue("stacker", stacker);
        }
    }
}
