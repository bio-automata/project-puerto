package model.events.reachSteakerEvents;

import java.util.Random;

import model.constants.EventConstants;
import model.constants.StackConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.events.Event;
import model.events.rtgEvents.EndRTGStackContainerEvent;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndStackerStackContainerEvent extends Event{
	Entity stack;
	
    public EndStackerStackContainerEvent(Entity stack, Systema system){
    	super(system);
    	this.stack = stack; 
    }

    public void execute(){
        system.setClock(this.getDurationTime());
        this.system.report("Reach Stacker empilhou container na pilha");

        Entity stacker = stack.getDependence("stacker");
        this.stack.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, stack.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)+1);
        
        
        //existe vagão disponíveel na composição aguardando
        if(this.system.hasEntityAvailableInQueue("train unloading")){
        	//sorteia duração para se movimentar até pilha de destino
        	
        	
        	//qqagenda descarregamento da pilha
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
        	    	stack = new Entity();
        	    	stack.setNumericVariable(StackConstants.INDEX, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS));
        	    	stack.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 0);
        	    	stack.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
        	    	
        	    	
        	    	double eventTimeDuration = stack.getNumericVariable(StackConstants.INDEX)*10;
        	    	//retira container da pilha
        	    	//agenda o evento de movimentar para a pilha com intenção de descarregar
        	    	Event event = new EndStackerMovingToStackEvent(stack, system);
        	    	event.setOccurrenceTime(eventTimeDuration);
        	    	this.system.agendFutureEvent(event);
        	    	
        	    	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stack);
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
        	system.report("Não há vagões, reach Stacker foi liberado");
            this.system.addEntityInQueue("stacker", stacker);
        }
        
        //se existe rtg/steaker aguardadno na fila da pilha, já aguarda novo empilhamento
        if(this.system.hasEntityAvailableInQueue("stacker waiting for stack "+this.stack.getNumericVariable(StackConstants.INDEX))){
        	//retira veiculo da fila e atuliza ponteiro da pilha
        	stacker = this.system.getEntityFromQueue("stacker waiting for stack "+this.stack.getNumericVariable(StackConstants.INDEX));
        	stack.setDependence("stacker", stacker);
        	
        	Event event = new EndStackerStackContainerEvent(stack, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.STACKER_STACKING_CONTAINER_EVENT));
            this.system.agendFutureEvent(event);
        }
        
        //existe vagão no terminal
    }
}
