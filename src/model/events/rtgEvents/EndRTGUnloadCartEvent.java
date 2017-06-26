package model.events.rtgEvents;

import java.util.Random;

import model.constants.StackConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndRTGUnloadCartEvent extends Event{
    public EndRTGUnloadCartEvent(Systema system){
    	super(system);
    }

    public void execute(){
        system.incrementClock(this.getOccurrenceTime());
        system.report("RTG descarregou container da carreta");
        
        //agenda retorno da carreta 
        
        
        
        //se não há pilhas no pátio, cria uma nova pilha 
        
        
        if(this.system.thereIsSet("container stak")){
        	//cria uma nova pilha
        	
        	//this.system.setEntityInEntitySet("container stak", );
        	this.system.setVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS, this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS)+1);
        }
        else{
        	int stakQuantity = this.system.getVariable(SystemConstants.NUMBER_OF_CONTAINERSTAKS).intValue();
            int stakindex = new Random().nextInt(stakQuantity);
            
            Entity stak = this.system.getEntityFromSet("container stak", stakindex);
            
            if(stak.getNumericVariable("")>StackConstants.MAX_NUMBER_OF_CONTAINERS){
            	//cria uma nova pilha
            	
            	
            }
        }
        
        //sorteia pilha
        
        
        
        



        //
    }
}
