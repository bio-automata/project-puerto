package model.events.equipEvents;

import model.entities.Entity;
import model.entities.Equip;
import model.entities.Quay;
import model.entities.ships.Ship;
import model.events.Event;
import model.system.Systema;

/**
 TrainArrivingEvent

 */

public class EndUndockingEvent extends Event{
	private Entity ship;
	private Entity equip; 
	private Entity quay;
	
    public EndUndockingEvent(Entity ship, Systema system){
    	super(system);
    	this.ship = ship;
    	this.equip = ship.getDependence("equip");
    	this.quay = ship.getDependence("quay");
    }

    @Override
    public void execute(){
        /** ainda preceisa ser implementado
            o que está aqui foi coopiadao de outra calsse
         */
    	
        //verifica cais e equipe disponíveis
        system.setClock(this.getOccurrenceTime());
        
        this.ship = null;		//navio parte
        system.addEntityInQueue("quay", this.quay);		//libera cais

      //se navio para desatracar
        if (system.getQueue("ship waiting undock").available()){
            //retira navio da cabeça da fila
            this.ship = system.getEntityFromQueue("ship waiting undock");
            this.ship.setDependence("equip", this.equip);
            
            Event event = new EndUndockingEvent(ship,this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration("undocking"));
            system.getFutureEventList().addEvent(event);
            
            system.report("");
        }
        //se navio para atracar e cais dsiponível
        else if(system.getQueue("ship waiting dock").available()&&system.getQueue("quay").available()){
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
            
            Event event = new EndDockingEvent(this.ship, this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration("docking"));
            system.getFutureEventList().addEvent(event);
            
            system.report("");
        }
        else{
            //entra em fila de espera
            //system.getEntityQueueSet().addEntity("equip", equip);
        	this.system.report("Não há navios em espera, equipe está ociosa");
            system.getEntityQueueSet().addEntity("equip", this.equip);
        }

    }
}
