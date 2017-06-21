package model.events.equipEvents;


import model.system.Systema;
import model.entities.Entity;
import model.events.Event;
import model.events.craneEvents.EndRiseContainerEvent;

/**
 
 */

public class EndDockingEvent extends Event{
	private Entity ship;
	private Entity equip;
	
    public EndDockingEvent(Entity ship, Systema system){
    	super(system);
    	this.ship = ship;
    	this.equip = ship.getDependence("equip");
    }

    @Override
    public void execute(){
        //atualiza o tempo do sistema
        system.setClock(this.getOccurrenceTime());
        
        //1a parte
        if (system.getQueue("crane").available()){
        	//agenda descarregamento e seta dependencias do navio
        	Entity crane = this.system.getEntityFromQueue("crane");
        	this.ship.setDependence("crane", crane);
        	
        	//cria evento de içar container
        	
            Event event = new EndRiseContainerEvent(this.ship, this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration("rise container"));
            system.getFutureEventList().addEvent(event);            
        	
        	system.report("Navio "+this.ship.getTextVariable("name")+" inicia descarregamento na grua "+crane.getTextVariable("index"));
        }
        else{
        	//aguarda descarregamento
        	system.report("Navio "+this.ship.getTextVariable("name")+" enfileirado aguardando descarregamento");
        }
        
        
        //2a parte
        
        //se navio para desatracar
        if (system.getQueue("ship waiting undock").available()){
            //retira navio
            this.ship  = system.getEntityFromQueue("ship waiting undock");
            this.ship.setDependence("equip", this.equip);
            
            Event event = new EndUndockingEvent(this.ship, this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration("undocking"));
            system.getFutureEventList().addEvent(event);
            
            system.report("Equipe desatracando");
        }

        //se navio para atracar e cais disponível
        else if(system.getQueue("ship waiting dock").available()&&system.getQueue("quay").available()){
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
            Event event = new EndDockingEvent(ship, this.system);
            event.setOccurrenceTime(this.system.getClock()+system.getEventDuration("docking"));
            system.getFutureEventList().addEvent(event);
        }
        else{
            //entra em fila de espera
        	
        	this.system.report("Não há navios em espera, equipe está ociosa");
            system.getEntityQueueSet().addEntity("equip", this.equip);
        }
    }
}
