package model.events.equipEvents;

import model.constants.EventConstants;
import model.entities.Entity;
import model.entities.Equip;
import model.entities.Quay;
import model.entities.ships.Ship;
import model.events.Event;
import model.system.Systema;

/**
 TrainArrivingEvent

 */

public class EndEquipUndockingEvent extends Event{
	private Entity ship;
	private Entity equip; 
	private Entity quay;
	
    public EndEquipUndockingEvent(Entity ship, Systema system){
    	super(system);
    	this.ship = ship;
    	this.equip = ship.getDependence("equip");
    	this.quay = ship.getDependence("quay");
    }

    @Override
    public void execute(){
        //verifica cais e equipe disponíveis
        system.setClock(this.getOccurrenceTime());
        system.report("Equipe desatracou um navio");
        
        this.ship = null;		//navio parte
        system.addEntityInQueue("quay", this.quay);		//libera cais        
        
      //se navio para desatracar
        if (system.hasEntityAvailableInQueue("ship waiting undock")){
            //retira navio da cabeça da fila
            this.ship = system.getEntityFromQueue("ship waiting undock");
            this.ship.setDependence("equip", this.equip);
            
            Event event = new EndEquipUndockingEvent(ship,this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration(EventConstants.EQUIP_UNDOKING_EVENT));
            system.getFutureEventList().addEvent(event);
            
            
        }
        //se navio para atracar e cais dsiponível
        else if(system.hasEntityAvailableInQueue("ship waiting dock")&&system.hasEntityAvailableInQueue("quay")){
            //retira navio e reserva cais seta navio
            this.ship  = system.getEntityFromQueue("ship waiting dock");
            this.quay = system.getEntityFromQueue("quay");

            this.ship.setDependence("equip", this.equip);
            this.ship.setDependence("quay", this.quay);
            
            /* agendando na FEL um evento de fim de atracamento

               cria o novo evento
               setando seus paramêtros
               e estabelece o tempo de ocorrencia
               com base no tempo atual
               + o sorteio da duração do atracamento
            */
            
            Event event = new EndEquipDockingEvent(this.ship, this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration(EventConstants.EQUIP_DOKING_EVENT));
            system.getFutureEventList().addEvent(event);
        }
        else{
            //entra em fila de espera
            //system.getEntityQueueSet().addEntity("equip", equip);
            system.getEntityQueueSet().addEntity("equip", this.equip);
            this.system.report("Não há navios em espera, equipe está ociosa");
        }
    }
}
