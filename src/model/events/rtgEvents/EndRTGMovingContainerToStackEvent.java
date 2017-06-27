package model.events.rtgEvents;

import java.util.Random;

import model.constants.EventConstants;
import model.constants.StackConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndRTGMovingContainerToStackEvent extends Event{
	private Entity rtg;
    public EndRTGMovingContainerToStackEvent(Entity rtg, Systema system){
    	super(system);
    	this.rtg = rtg;
    }

    public void execute(){
    	this.system.setClock(this.getOccurrenceTime());
        system.report("RTG movimentou-se até o pátio à procura de uma pilha");
        
        
        //rtg seleciona uma pilha e agenda evento para movimentar até ela 
        Entity stak;
        //se não há pilhas no pátio, cria uma nova pilha
        if(!this.system.thereIsSet(SystemConstants.CONTAINER_STAKS)){
        	system.report("Não há pilhas no pátio, rtg criará a primeira pilha");
        	
        	//cria uma nova pilha e já empilha o container
        	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, 1.0);
        	
        	stak = new Entity();
        	stak.setNumericVariable(StackConstants.INDEX, 0);
        	stak.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 0);
        	stak.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
        	
        	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stak);
        }
        
        //se há pilha
        else{
        	//sorteia uma pilha
        	this.system.report("Numero de pilhas no pátio >>");
        	this.system.report(this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue()+"");
        	
        	int stakQuantity = this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue();	//numero de pilhas no sistema
            int stakindex = new Random().nextInt(stakQuantity);
            
            stak = this.system.getEntityFromSet(SystemConstants.CONTAINER_STAKS, stakindex);
            this.system.report(stak.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)+"");
            
            if(stak.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)>=stak.getNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS)){
            	this.system.report("A pilha estava cheia, rtg criará nova pilha");
            	//cria uma nova pilha
            	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS)+1);
            	
            	stak = new Entity();
            	stak.setNumericVariable(StackConstants.INDEX, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS));
            	stak.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 0);
            	stak.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
            	
            	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stak);
            }
        }
            
        //se não há rtg na fila da pilha 
        if(!this.system.hasEntityAvailableInQueue("rtg waiting for stack "+stak.getNumericVariable(StackConstants.INDEX))){
        	double eventTimeDuration = stak.getNumericVariable(StackConstants.INDEX)*10;
        	stak.setDependence("rtg", this.rtg);
        	Event event = new EndRTGStackContainerEvent(stak, this.system);
            //event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_STACKING_CONTAINER_EVENT));
        	event.setOccurrenceTime(eventTimeDuration);
            this.system.agendFutureEvent(event);
        }
        //do contrário enfileira rtg 
        else{
        	this.system.addEntityInQueue("rtg waiting for stack "+stak.getNumericVariable(StackConstants.INDEX), rtg);
        }       
    }
}
