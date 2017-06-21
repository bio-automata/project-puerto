package model.events;

import model.system.Systema;

/**

 Event

 é uma classe abstrata para a representação de
 uma corrência de um determinado evento
 lembrando:

 um evento manipulará as filas de entidades do sistema
 um evento manipulará as filas de evento do sistema


 um evento possui:

 tempo de ocorrência
 tempo de duração (progressão temporal)
 poderá ou não desencadear outros eventos


 através do método execute() o evento manipula:

 a lista de eventos do sistema
 o conjunto de entidades do sistema
 as variáveis internas do sistema
 as variáveis internas de uma entidade


 o método execute() seguirá a seguinte ordem:

 alterar o estado das filas do sistema
 alterar as variáveis do sistema
 alterar o estado de estidades
 inserir eventos desencadeados na lista de eventos do sistema


 todo classer derivado de Event deverá
 implementar este método:

 public void execute();  //dentro deste método acontecerá a mágica
 */

public abstract class Event {
	protected Systema system;
    private double occurrenceTime;
    private double durationTime;

    public Event(Systema system){
    	this.system = system; 
        this.occurrenceTime = -1;
        this.durationTime = -1;
    }

    public double getOccurrenceTime(){
        return this.occurrenceTime;
    }

    public void setOccurrenceTime(double occurrenceTime){
        this.occurrenceTime= occurrenceTime;
    }

    public double getDurationTime(){
        return this.durationTime;
    }

    public abstract void execute();
}