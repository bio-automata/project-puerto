package model.events.rtgEvents;

import java.util.Random;

import model.constants.EventConstants;
import model.constants.StackConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.events.Event;
import model.events.cartEvents.EndCartMovingEmptyCartEvent;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndRTGUnloadCartEvent extends Event{
	Entity rtg;
    public EndRTGUnloadCartEvent(Entity rtg, Systema system){
    	super(system);
    	this.rtg = rtg;
    }

    public void execute(){
        system.incrementClock(this.getOccurrenceTime());
        system.report("RTG descarregou container da carreta");
        
        
        //descarrega carreta
        //agenda retorno da carreta 
        Event event = new EndCartMovingEmptyCartEvent(rtg, system);
        event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_UNLOAD_CART_EVENT));
        this.system.agendFutureEvent(event);
        
        
        Entity stak;
        //se não há pilhas no pátio, cria uma nova pilha
        if(!this.system.thereIsSet(SystemConstants.CONTAINER_STAKS)){
        	System.out.println("cria a primeira pilha");
        	//cria uma nova pilha e já empilha o container
        	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, 1.0);
        	
        	stak = new Entity();
        	stak.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 1);
        	stak.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
        	
        	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stak);
        }
        
      //se há pilha
        else{
        	System.out.print("numero de containers ");
        	//sorteia uma pilha
        	System.out.println(this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue());
        	
        	int stakQuantity = this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue();	//numero de pilhas no sistema
            int stakindex = new Random().nextInt(stakQuantity);
            
            stak = this.system.getEntityFromSet(SystemConstants.CONTAINER_STAKS, stakindex);
            
            
            System.out.println("tenta empilhar");
            if(stak.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)>stak.getNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS)){
            	System.out.println("cria nova pilha");
            	//cria uma nova pilha
            	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS)+1);
            	            	
            	stak = new Entity();
            	stak.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, 1);
            	stak.setNumericVariable(StackConstants.MAX_NUMBER_OF_CONTAINERS, 5);
            	
            	this.system.addEntityInEntitySet(SystemConstants.CONTAINER_STAKS, stak);
            	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS)+1);
            }
            else{
            	//stak = this.system.getEntityFromSet(SystemConstants.CONTAINER_STAKS, stakindex);
                stak.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, stak.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)+1.0);
            }
        }
    }
}
