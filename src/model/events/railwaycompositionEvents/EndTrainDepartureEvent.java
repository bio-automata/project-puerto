package model.events.railwaycompositionEvents;

import model.entities.Entity;
import model.events.Event;
import model.system.Systema;

/**
 TrainDepartureEvent


 */

public class EndTrainDepartureEvent extends Event{
	Entity train;
	
    public EndTrainDepartureEvent(Entity train, Systema system){
    	super(system);
    	this.train = train;

    }

    public void execute(){
        system.setClock(this.getOccurrenceTime());
        this.system.report("Composição ferroviária despachada");
        //realiza alguma coisa aqui
        
        
        Entity railway = train.getDependence("railway");       
        
        //se há composição ferroviária à espera de carregamento
        if(this.system.hasEntityAvailableInQueue("train waiting railway")){
        	this.system.report("Em breve já começará carregamento de outra");
        	train = this.system.getEntityFromQueue("train waiting railway");
        	train.setDependence("railway", railway);
        }
        //senão terminal está ocionso
        else{
        	this.system.report("Não há fila de espera, Terminal de carga ocioso");
        	this.train = null;
        	this.system.addEntityInQueue("railway", railway);
        }
    }
}