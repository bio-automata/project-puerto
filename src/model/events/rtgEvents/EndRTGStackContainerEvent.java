package model.events.rtgEvents;


import model.constants.EventConstants;
import model.constants.StackConstants;
import model.constants.SystemConstants;
import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 * Created by dicus on 11/06/17.
 */
public class EndRTGStackContainerEvent extends Event{
	Entity stack;
	
    public EndRTGStackContainerEvent(Entity stack, Systema system){
    	super(system);
    	this.stack = stack;
    }

    public void execute(){
    	this.system.setClock(this.getOccurrenceTime());
        this.system.report("RTG empilhou container na pilha");

        Entity rtg = stack.getDependence("rtg");
        this.stack.setNumericVariable(StackConstants.NUMBER_OF_CONTAINERS, stack.getNumericVariable(StackConstants.NUMBER_OF_CONTAINERS)+1);
        this.system.setVariable(SystemConstants.RTG_CONTAINER_PRODUCTION, this.system.getVariable(SystemConstants.RTG_CONTAINER_PRODUCTION)+1);

        this.system.report("Produtividade dos RTGs: "+this.system.getVariable(SystemConstants.RTG_CONTAINER_PRODUCTION));
        
        system.report("RTG foi liberado");
        this.system.addEntityInQueue("rtg", rtg);
     
        system.report("Numero RTGs livres:"+this.system.getEntityQueueSet().getEntityQueue("rtg").size());
        
        if(this.system.hasEntityAvailableInQueue("cart waiting unload")){
        	Entity cart = this.system.getEntityFromQueue("cart waiting unload");       
            rtg.setDependence("cart", cart);
        	
        	Event event = new EndRTGUnloadCartEvent(rtg, this.system);
        	event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_UNLOAD_CART_EVENT));
        	this.system.agendFutureEvent(event);        	
        }
        
        
        //se existe rtg/steaker aguardadno na fila da pilha, já aguarda novo empilhamento
        if(this.system.hasEntityAvailableInQueue("rtg waiting for stack "+this.stack.getNumericVariable(StackConstants.INDEX))){
        	rtg = this.system.getEntityFromQueue("rtg waiting for stack "+this.stack.getNumericVariable(StackConstants.INDEX));
        	
        	stack.setDependence("rtg", rtg);
        	Event event = new EndRTGStackContainerEvent(stack, this.system);
            event.setOccurrenceTime(this.system.getClock()+this.system.getEventDuration(EventConstants.RTG_STACKING_CONTAINER_EVENT));
            this.system.agendFutureEvent(event);
        }
    }
}
