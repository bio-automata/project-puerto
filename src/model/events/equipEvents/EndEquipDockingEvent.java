package model.events.equipEvents;


import model.system.Systema;
import model.constants.EventConstants;
import model.entities.Entity;
import model.events.Event;
import model.events.craneEvents.EndCraneRiseContainerEvent;

/**
 
 */

public class EndEquipDockingEvent extends Event{
	private Entity ship;
	private Entity equip;
	
    public EndEquipDockingEvent(Entity ship, Systema system){
    	super(system);
    	this.ship = ship;
    	this.equip = ship.getDependence("equip");
    }

    @Override
    public void execute(){
        //atualiza o tempo do sistema
        system.setClock(this.getOccurrenceTime());
        this.system.report("Equipe atracou um navio");
        
        //1a parte
        if (system.hasEntityAvailableInQueue("crane")){
        	//agenda descarregamento e seta dependencias do navio
        	Entity crane = this.system.getEntityFromQueue("crane");
        	this.ship.setDependence("crane", crane);
        	crane.setDependence("ship", this.ship);
        	
        	//cria evento de içar container
        	
            Event event = new EndCraneRiseContainerEvent(crane, this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration(EventConstants.CRANE_RISING_CONTAINER_EVENT));
            system.getFutureEventList().addEvent(event);            
        	
        	system.report("Navio "+this.ship.getTextVariable("name")+" inicia descarregamento na grua ");
        }
        else{
        	//aguarda descarregamento
        	system.report("Navio "+this.ship.getTextVariable("name")+" enfileirado aguardando descarregamento");
        }
        
        
        //2a parte
        
        //se navio para desatracar
        if (system.hasEntityAvailableInQueue("ship waiting undock")){
            //retira navio
            this.ship  = system.getEntityFromQueue("ship waiting undock");
            this.ship.setDependence("equip", this.equip);
            
            Event event = new EndEquipUndockingEvent(this.ship, this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration(EventConstants.EQUIP_UNDOKING_EVENT));
            system.getFutureEventList().addEvent(event);
            
            system.report("Equipe desatracando");
        }

        //se navio para atracar e cais disponível
        else if(system.hasEntityAvailableInQueue("ship waiting dock")&&system.hasEntityAvailableInQueue("quay")){
            //retira navio e reserva cais
        	this.ship = system.getEntityFromQueue("ship waiting dock");
            Entity quay = system.getEntityFromQueue("quay");

            this.ship.setDependence("equip", this.equip);
            this.ship.setDependence("quay", quay);
            
            /* agendando na FEL um evento de fim de atracamento

               cria o novo evento
               setando seus paramêtros
               e estabelece o tempo de ocorrencia
               com base no tempo atual
               + o sorteio da duração do atracamento
            */

            system.report("Equipe atracando novo navio");
            Event event = new EndEquipDockingEvent(ship, this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration(EventConstants.EQUIP_DOKING_EVENT));
            system.getFutureEventList().addEvent(event);
        }
        else{
            //entra em fila de espera
        	
        	this.system.report("Não há navios em espera, equipe está ociosa");
            system.addEntityInQueue("equip", this.equip);
        }
    }
}
