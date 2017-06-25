package model.events.shipEvents;

import model.constants.EventConstants;
import model.constants.ShipConstants;
import model.entities.Entity;
import model.entities.ships.ContainerShipFactory;
import model.entities.ships.Ship;
import model.events.Event;
import model.events.equipEvents.EndEquipDockingEvent;
import model.random.Random;
import model.system.Systema;

/**
 
*/

public class ShipArrivingEvent extends Event {
    //exponencial
    public ShipArrivingEvent(Systema system){
    	super(system);
    }

    public void execute(){
    	system.setClock(this.getOccurrenceTime());

    	/*
    	 	------chegada de navio---------
       		
       		sorteia o tipo do navio
    		sorteia a quantidade de containers entre 70 e 100 percent
    	*/
    	
        Entity ship = ContainerShipFactory.create((int)new Random().uniform(7));
        ship.setNumericVariable(ShipConstants.NUMBER_OF_CONTAINERS , ship.getNumericVariable("maximum capacity")*(70+new Random().uniform(30)/100));
     
        
        //reporta o que está acontecendo no sistema
        this.system.report("Chegada de um "+ship.getTextVariable("name")+" com "+ship.getNumericVariable(ShipConstants.NUMBER_OF_CONTAINERS));
        
        
    	//verifica cais e equipe disponíveis
    	if(system.hasEntityAvailableInQueue("quay")&&system.hasEntityAvailableInQueue("equip")){
    		//reserva cais e equipe e seta as dependencias do navio
    		Entity equip = system.getEntityFromQueue("equip");
            Entity quay = system.getEntityFromQueue("quay");

            ship.setDependence("equip", equip);
            ship.setDependence("quay", quay);
            
            
            /* agenda na FEL um evento de fim de atracamento

               cria o novo evento
               seta seus paramêtros
               e estabelece o tempo de ocorrencia
               com base no tempo atual
               + o sorteio da duração do atracamento
            */
            
            Event event = new EndEquipDockingEvent(ship, this.system);
            event.setOccurrenceTime(system.getClock()+system.getEventDuration(EventConstants.EQUIP_DOKING_EVENT));
            system.getFutureEventList().addEvent(event);
    	}
    	else{
    		//enfileira navio
    		this.system.report("Navio enfileirado esperando atracagem");
    		system.addEntityInQueue("ship waiting dock", ship);
    	}
    }
}